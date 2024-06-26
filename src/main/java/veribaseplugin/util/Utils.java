package veribaseplugin.util;

import com.google.common.hash.HashCode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.runelite.client.discord.DiscordService;
import okhttp3.*;
import veribaseplugin.VeribasePlugin;
import veribaseplugin.VeribasePluginConfig;
import veribaseplugin.domain.AccountType;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.annotations.Component;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public class Utils {
    private static final Gson gson = new Gson();
    private static final OkHttpClient httpClient = new OkHttpClient();

    public static final String WIKI_IMG_BASE_URL = "https://oldschool.runescape.wiki/images/";

    public final Color GREEN = ColorUtil.fromHex("006c4c"); // dark shade of PINK in CIELCh_uv color space
    public final Color PINK = ColorUtil.fromHex("#f40098"); // analogous to RED in CIELCh_uv color space
    public final Color RED = ColorUtil.fromHex("#ca2a2d"); // red used in pajaW

    private final char ELLIPSIS = '\u2026'; // '…'

    /**
     * Custom padding for applying SHA-256 to a long.
     * SHA-256 adds '0' padding bits such that L+1+K+64 mod 512 == 0.
     * Long is 64 bits and this padding is 376 bits, so L = 440. Thus, minimal K = 7.
     * This padding differentiates our hash results, and only incurs a ~1% performance penalty.
     *
     * @see #veribaseHash(long)
     */
    private static final byte[] VERIBASE_HASH_PADDING = "JennaRaidingDreamSeedZeroPercentOptionsForAnts!"
        .getBytes(StandardCharsets.UTF_8); // DO NOT CHANGE

    /**
     * Truncates text at the last space to conform with the specified max length.
     *
     * @param text      The text to be truncated
     * @param maxLength The maximum allowed length of the text
     * @return the truncated text
     */
    public String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;

        int lastSpace = text.lastIndexOf(' ', maxLength - 1);
        if (lastSpace <= 0) {
            return text.substring(0, maxLength - 1) + ELLIPSIS;
        } else {
            return text.substring(0, lastSpace) + ELLIPSIS;
        }
    }

    public String sanitize(String str) {
        if (str == null || str.isEmpty()) return "";
        return Text.removeTags(str.replace("<br>", "\n")).replace('\u00A0', ' ').trim();
    }

    /**
     * Converts text into "upper case first" form, as is used by OSRS for item names.
     *
     * @param text the string to be transformed
     * @return the text with only the first character capitalized
     */
    public String ucFirst(@NotNull String text) {
        if (text.length() < 2) return text.toUpperCase();
        return Character.toUpperCase(text.charAt(0)) + text.substring(1).toLowerCase();
    }

    /**
     * Converts simple patterns (asterisk is the only special character) into regexps.
     *
     * @param pattern a simple pattern (asterisks are wildcards, and the rest is a string literal)
     * @return a compiled regular expression associated with the simple pattern
     * @see VeribasePluginConfig#lootItemAllowlist()
     * @see VeribasePluginConfig#lootItemDenylist()
     */
    public Pattern regexify(@NotNull String pattern) {
        final int len = pattern.length();
        final StringBuilder sb = new StringBuilder(len + 2 + 4);
        int startIndex = 0;

        if (!pattern.startsWith("*")) {
            sb.append('^');
        } else {
            startIndex++;
        }

        int i;
        while ((i = pattern.indexOf('*', startIndex)) >= 0) {
            String section = pattern.substring(startIndex, i);
            sb.append(Pattern.quote(section));
            sb.append(".*");
            startIndex = i + 1;
        }

        if (startIndex < len) {
            sb.append(Pattern.quote(pattern.substring(startIndex)));
            sb.append('$');
        }

        return Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    }

    /**
     * @param a some string
     * @param b another string
     * @return whether either string contains the other
     */
    public boolean containsEither(@NotNull String a, @NotNull String b) {
        if (a.length() >= b.length()) {
            return a.contains(b);
        } else {
            return b.contains(a);
        }
    }

    /**
     * @param client {@link Client}
     * @return the name of the local player
     */
    public String getPlayerName(Client client) {
        return client.getLocalPlayer().getName();
    }

    /**
     * Transforms the value from {@link Varbits#ACCOUNT_TYPE} to a convenient enum.
     *
     * @param client {@link Client}
     * @return {@link AccountType}
     * @apiNote This function should only be called from the client thread.
     */
    public AccountType getAccountType(@NotNull Client client) {
        return AccountType.get(client.getVarbitValue(Varbits.ACCOUNT_TYPE));
    }

    @Nullable
    public String getChatBadge(@NotNull AccountType type, boolean seasonal) {
        if (seasonal) {
            return WIKI_IMG_BASE_URL + "Leagues_chat_badge.png";
        }
        switch (type) {
            case IRONMAN:
                return WIKI_IMG_BASE_URL + "Ironman_chat_badge.png";
            case ULTIMATE_IRONMAN:
                return WIKI_IMG_BASE_URL + "Ultimate_ironman_chat_badge.png";
            case HARDCORE_IRONMAN:
                return WIKI_IMG_BASE_URL + "Hardcore_ironman_chat_badge.png";
            case GROUP_IRONMAN:
                return WIKI_IMG_BASE_URL + "Group_ironman_chat_badge.png";
            case HARDCORE_GROUP_IRONMAN:
                return WIKI_IMG_BASE_URL + "Hardcore_group_ironman_chat_badge.png";
            case UNRANKED_GROUP_IRONMAN:
                return WIKI_IMG_BASE_URL + "Unranked_group_ironman_chat_badge.png";
            default:
                return null;
        }
    }

    public static boolean hideWidget(boolean shouldHide, Client client, @Component int info) {
        if (!shouldHide)
            return false;

        Widget widget = client.getWidget(info);
        if (widget == null || widget.isHidden())
            return false;

        widget.setHidden(true);
        return true;
    }

    public static void unhideWidget(boolean shouldUnhide, Client client, ClientThread clientThread, @Component int info) {
        if (!shouldUnhide)
            return;

        clientThread.invoke(() -> {
            Widget widget = client.getWidget(info);
            if (widget != null)
                widget.setHidden(false);
        });
    }

    public BufferedImage rescale(BufferedImage input, double percent) {
        if (percent + Math.ulp(1.0) >= 1.0)
            return input;

        AffineTransform rescale = AffineTransform.getScaleInstance(percent, percent);
        AffineTransformOp operation = new AffineTransformOp(rescale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        BufferedImage output = new BufferedImage((int) (input.getWidth() * percent), (int) (input.getHeight() * percent), input.getType());
        operation.filter(input, output);
        return output;
    }

    public byte[] convertImageToByteArray(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean foundWriter = ImageIO.write(bufferedImage, format, byteArrayOutputStream);
        if (!foundWriter)
            throw new IllegalArgumentException(String.format("Specified format '%s' was not in supported formats: %s", format, Arrays.toString(ImageIO.getWriterFormatNames())));
        return byteArrayOutputStream.toByteArray();
    }

    public boolean hasImage(@NotNull MultipartBody body) {
        return body.parts().stream().anyMatch(part -> {
            MediaType type = part.body().contentType();
            return type != null && "image".equals(type.type());
        });
    }

    public CompletableFuture<String> readClipboard() {
        CompletableFuture<String> future = new CompletableFuture<>();
        SwingUtilities.invokeLater(() -> {
            try {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String data = (String) clipboard.getData(DataFlavor.stringFlavor);
                future.complete(data);
            } catch (Exception e) {
                log.warn("Failed to read from clipboard", e);
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    public CompletableFuture<Void> copyToClipboard(String text) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        SwingUtilities.invokeLater(() -> {
            try {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection content = new StringSelection(text);
                clipboard.setContents(content, content);
                future.complete(null);
            } catch (Exception e) {
                log.warn("Failed to copy to clipboard", e);
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    public <T> CompletableFuture<T> readJson(@NotNull OkHttpClient httpClient, @NotNull Gson gson, @NotNull String url, @NotNull TypeToken<T> type) {
        return readUrl(httpClient, url, reader -> gson.fromJson(reader, type.getType()));
    }

    public <T> CompletableFuture<T> readUrl(@NotNull OkHttpClient httpClient, @NotNull String url, @NotNull Function<Reader, T> transformer) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                assert response.body() != null;
                try (Reader reader = response.body().charStream()) {
                    future.complete(transformer.apply(reader));
                } catch (Exception e) {
                    future.completeExceptionally(e);
                } finally {
                    response.close();
                }
            }
        });
        return future;
    }

    /**
     * Computes and sends the veribase hash to a remote server via POST request.
     *
     * @param l the long to hash
     */
    public CompletableFuture<Void> sendVeribaseHash(@NotNull Client client, @NotNull long l, @NotNull String discordId, @NotNull VeribasePlugin plugin) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        String hash = veribaseHash(l);
        if (hash == null) {
            future.completeExceptionally(new IllegalStateException("Hash computation failed"));
            return future;
        }

        // Create JSON payload
        String jsonPayload = gson.toJson(new VeribasePayload(hash, discordId, getPlayerName(client), getAccountType(client).toString()));

        // Create request body
        RequestBody body = RequestBody.create(MediaType.get("application/json"), jsonPayload);

        // Build the POST request
        Request request = new Request.Builder()
            .url("https://vhgle12tn7.execute-api.us-east-1.amazonaws.com/prod/veribase-init")
            .post(body)
            .build();

        // Asynchronously send the POST request
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error("Failed to send veribase hash due to network error", e);
                future.completeExceptionally(e); // Complete exceptionally on network errors
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string(); // Get the response body as a string
                        VeribaseInitResponse jsonResponse = new Gson().fromJson(responseBody, VeribaseInitResponse.class); // Parse the response body to JSON
                        String status = jsonResponse.getStatus();

                        log.info("here is my status " + status);

                        if ("initialized".equals(status)) {
                            log.info("User successfully registered");
                            plugin.addChatSuccess("Character successfully registered with Veribase.");
                        } else if ("already_initialized".equals(status)) {
                            log.info("User already registered");
                            plugin.addChatSuccess("Character is already registered with Veribase.");
                        } else {
                            log.warn("Unknown status received: " + status);
                        }

                        future.complete(null); // Successfully completed the future
                    } else {
                        log.warn("Server responded with error: " + response.code() + " " + response.body().toString());
                        future.completeExceptionally(new IOException("HTTP Error: " + response.code() + " " + response.message()));
                    }
                } catch (Exception e) {
                    log.error("Failed processing the HTTP response", e);
                    future.completeExceptionally(e); // Complete exceptionally for any exception caught during processing
                } finally {
                    response.close(); // Ensure the response is closed in all cases
                }
            }
        });

        return future;
    }

    public CompletableFuture<Void> wipeVeribaseHash(@NotNull Client client, @NotNull long l, @NotNull String discordId, @NotNull VeribasePlugin plugin) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        String hash = veribaseHash(l);
        if (hash == null) {
            future.completeExceptionally(new IllegalStateException("Hash computation failed"));
            return future;
        }

        // Create JSON payload
        String jsonPayload = gson.toJson(new VeribasePayload(hash, discordId, getPlayerName(client), getAccountType(client).toString()));

        // Create request body
        RequestBody body = RequestBody.create(MediaType.get("application/json"), jsonPayload);

        // Build the POST request
        Request request = new Request.Builder()
            .url("https://vhgle12tn7.execute-api.us-east-1.amazonaws.com/prod/veribase-wipe")
            .post(body)
            .build();

        // Asynchronously send the POST request
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error("Failed to send veribase hash due to network error", e);
                future.completeExceptionally(e); // Complete exceptionally on network errors
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string(); // Get the response body as a string
                        VeribaseWipeResponse jsonResponse = new Gson().fromJson(responseBody, VeribaseWipeResponse.class); // Parse the response body to JSON

                        if (jsonResponse.hasSuccess()) {
                            log.info("successful deletion");
                            plugin.addChatSuccess(jsonResponse.getSuccessMessage());
                        }

                        if (jsonResponse.hasFailures()) {
                            log.info("error deletion");
                            plugin.addChatWarning(jsonResponse.getFailureMessage());
                        }

                        future.complete(null); // Successfully completed the future
                    } else {
                        log.warn("Server responded with error: " + response.code() + " " + response.body().toString());
                        future.completeExceptionally(new IOException("HTTP Error: " + response.code() + " " + response.message()));
                    }
                } catch (Exception e) {
                    log.error("Failed processing the HTTP response", e);
                    future.completeExceptionally(e); // Complete exceptionally for any exception caught during processing
                } finally {
                    response.close(); // Ensure the response is closed in all cases
                }
            }
        });

        return future;
    }

    /**
     * Helper class for JSON payload.
     */
    private static class VeribasePayload {
        private final String veribaseHash;
        private final String discordId;
        private final String playerName;
        private final String accountType;

        public VeribasePayload(String veribaseHash, String discordId, String playerName, String accountType) {
            this.veribaseHash = veribaseHash;
            this.playerName = playerName;
            this.accountType = accountType;
            this.discordId = discordId;
        }
    }

    private class VeribaseInitResponse {
        private String status;
        private String message;

        // Getter for the status field
        public String getStatus() {
            return status;
        }

        // Getter for the message field
        public String getMessage() {
            return message;
        }
    }

    private class VeribaseWipeResponse {
        private String status;
        private String success;
        private String failure;
        private boolean hasSuccess;
        private boolean hasFailures;

        // Getter for the status field
        public String getStatus() {
            return status;
        }

        // Getter for the message field
        public String getSuccessMessage() {
            return success;
        }
        public String getFailureMessage() {
            return failure;
        }
        public boolean hasSuccess() {
            return hasSuccess;
        }
        public boolean hasFailures() {
            return hasFailures;
        }
    }

    /**
     * @param l the long to hash
     * @return SHA-224 with custom padding
     * @see #VERIBASE_HASH_PADDING
     */
    public String veribaseHash(long l) {
        MessageDigest hash;
        try {
            hash = MessageDigest.getInstance("SHA-224");
        } catch (NoSuchAlgorithmException e) {
            log.warn("Account hash will not be included in notification metadata", e);
            return null;
        }
        byte[] input = ByteBuffer.allocate(8 + VERIBASE_HASH_PADDING.length)
            .putLong(l)
            .put(VERIBASE_HASH_PADDING)
            .array();
        // noinspection UnstableApiUsage - no longer @Beta as of v32.0.0
        return HashCode.fromBytes(hash.digest(input)).toString();
    }

}
