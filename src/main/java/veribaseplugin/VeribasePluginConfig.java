package veribaseplugin;

import veribaseplugin.domain.AchievementDiary;
import veribaseplugin.domain.ChatNotificationType;
import veribaseplugin.domain.ClueTier;
import veribaseplugin.domain.CombatAchievementTier;
import veribaseplugin.domain.ExceptionalDeath;
import veribaseplugin.domain.FilterMode;
import veribaseplugin.domain.LeagueTaskDifficulty;
import veribaseplugin.domain.PlayerLookupService;
import veribaseplugin.notifiers.ChatNotifier;
import net.runelite.api.Experience;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

import java.util.EnumSet;
import java.util.Set;

@ConfigGroup(SettingsManager.CONFIG_GROUP)
public interface VeribasePluginConfig extends Config {

    String veribaseApiUrl = "https://vhgle12tn7.execute-api.us-east-1.amazonaws.com/prod/secured/?discordId=133789758760550400";
//
//    @ConfigSection(
//        name = "Webhook Overrides",
//        description = "Allows webhook data to be sent to a different URL, for the various notifiers",
//        position = -20,
//        closedByDefault = true
//    )
//    String webhookSection = "Webhook Overrides";
//
//    @ConfigSection(
//        name = "Collection Log",
//        description = "Settings for notifying about collection log",
//        position = 0,
//        closedByDefault = true
//    )
//    String collectionSection = "Collection Log";
//
//    @ConfigSection(
//        name = "Pet",
//        description = "Settings for notifying when obtaining a pet",
//        position = 10,
//        closedByDefault = true
//    )
//    String petSection = "Pet";
//
//    @ConfigSection(
//        name = "Levels",
//        description = "Settings for notifying when levelling a skill",
//        position = 20,
//        closedByDefault = true
//    )
//    String levelSection = "Levels";
//
//    @ConfigSection(
//        name = "Loot",
//        description = "Settings for notifying when loot is dropped",
//        position = 30,
//        closedByDefault = true
//    )
//    String lootSection = "Loot";
//
//    @ConfigSection(
//        name = "Death",
//        description = "Settings for notifying when you die",
//        position = 40,
//        closedByDefault = true
//    )
//    String deathSection = "Death";
//
//    @ConfigSection(
//        name = "Slayer",
//        description = "Settings for notifying when you complete a slayer task",
//        position = 50,
//        closedByDefault = true
//    )
//    String slayerSection = "Slayer";
//
//    @ConfigSection(
//        name = "Quests",
//        description = "Settings for notifying when you complete a quest",
//        position = 60,
//        closedByDefault = true
//    )
//    String questSection = "Quests";
//
//    @ConfigSection(
//        name = "Clue Scrolls",
//        description = "Settings for notifying when you complete a clue scroll",
//        position = 70,
//        closedByDefault = true
//    )
//    String clueSection = "Clue Scrolls";
//
//    @ConfigSection(
//        name = "Speedruns",
//        description = "Settings for notifying when you finish a speedrun",
//        position = 80,
//        closedByDefault = true
//    )
//    String speedrunSection = "Speedruns";
//
//    @ConfigSection(
//        name = "Kill Count",
//        description = "Settings for notifying when you kill a boss",
//        position = 90,
//        closedByDefault = true
//    )
//    String killCountSection = "Kill Count";
//
//    @ConfigSection(
//        name = "Combat Tasks",
//        description = "Settings for notifying when you complete a combat achievement",
//        position = 100,
//        closedByDefault = true
//    )
//    String combatTaskSection = "Combat Tasks";
//
//    @ConfigSection(
//        name = "Achievement Diary",
//        description = "Settings for notifying when you complete an Achievement Diary",
//        position = 110,
//        closedByDefault = true
//    )
//    String diarySection = "Achievement Diary";
//
//    @ConfigSection(
//        name = "BA Gambles",
//        description = "Settings for notifying when you gamble at Barbarian Assault",
//        position = 120,
//        closedByDefault = true
//    )
//    String gambleSection = "BA Gambles";
//
//    @ConfigSection(
//        name = "Player Kills",
//        description = "Settings for notifying when you kill another player",
//        position = 130,
//        closedByDefault = true
//    )
//    String pkSection = "Player Kills";
//
//    @ConfigSection(
//        name = "Group Storage",
//        description = "Settings for notifying when you deposit or withdraw items from group ironman shared storage",
//        position = 140,
//        closedByDefault = true
//    )
//    String groupStorageSection = "Group Storage";
//
//    @ConfigSection(
//        name = "Grand Exchange",
//        description = "Settings for notifying when you buy or sell items from the Grand Exchange",
//        position = 150,
//        closedByDefault = true
//    )
//    String grandExchangeSection = "Grand Exchange";
//
//    @ConfigSection(
//        name = "Player Trades",
//        description = "Settings for notifying when you trade with another player",
//        position = 160,
//        closedByDefault = true
//    )
//    String tradeSection = "Player Trades";
//
//    @ConfigSection(
//        name = "Custom Chat Messages",
//        description = "Settings for notifying when you receive specific messages from the game",
//        position = 170,
//        closedByDefault = true
//    )
//    String chatSection = "Custom Chat Messages";
//
//    /*
//    @ConfigSection(
//        name = "Leagues",
//        description = "Settings for notifying when you complete league tasks, unlock areas, and redeem relics",
//        position = 200,
//        closedByDefault = true
//    )
//     */
//    String leaguesSection = "Leagues";
//
//    @ConfigSection(
//        name = "Advanced",
//        description = "Do not modify without fully understanding these settings",
//        position = 1000,
//        closedByDefault = true
//    )
//    String advancedSection = "Advanced";

    @ConfigItem(
        keyName = VersionManager.VERSION_CONFIG_KEY,
        name = "Plugin Version",
        description = "The latest veribase version used by the player that has a notable changelog entry",
        hidden = true
    )
    default String pluginVersion() {
        return "";
    }

    @ConfigItem(
        keyName = "maxRetries",
        name = "Webhook Max Retries",
        description = "The maximum number of retry attempts for sending a webhook message. Negative implies no attempts",
        position = 1000,
        hidden = true
    )
    default int maxRetries() {
        return 2;
    }

    @ConfigItem(
        keyName = "baseRetryDelay",
        name = "Webhook Retry Base Delay",
        description = "The base number of milliseconds to wait before attempting a retry for a webhook message",
        position = 1001,
        hidden = true
    )
    @Units(Units.MILLISECONDS)
    default int baseRetryDelay() {
        return 2000;
    }

    @ConfigItem(
        keyName = "imageWriteTimeout",
        name = "Image Upload Timeout",
        description = "The maximum number of seconds that uploading a screenshot can take before timing out",
        position = 1002,
        hidden = true
    )
    @Units(Units.SECONDS)
    default int imageWriteTimeout() {
        return 30; // elevated from okhttp default of 10
    }

    @ConfigItem(
        keyName = "screenshotScale",
        name = "Screenshot Scale",
        description = "Resizes screenshots in each dimension by the specified percentage.<br/>" +
            "Useful to avoid Discord's max upload size of 8MB or reduce bandwidth",
        position = 1003,
        hidden = true
    )
    @Units(Units.PERCENT)
    @Range(min = 1, max = 100)
    default int screenshotScale() {
        return 100;
    }

    @ConfigItem(
        keyName = "discordRichEmbeds",
        name = "Use Rich Embeds",
        description = "Whether Discord's rich embed format should be used for webhooks",
        position = 1004,
        hidden = true
    )
    default boolean discordRichEmbeds() {
        return true;
    }

    @ConfigItem(
        keyName = "embedFooterText",
        name = "Embed Footer Text",
        description = "The text in the footer of rich embed webhook messages. If empty, no footer will be sent",
        position = 1005,
        hidden = true
    )
    default String embedFooterText() {
        return "TY Dink <3";
    }

    @ConfigItem(
        keyName = "embedFooterIcon",
        name = "Embed Footer Icon",
        description = "The URL for the footer icon image of rich embed webhooks. Requires footer text to not be empty",
        position = 1006,
        hidden = true
    )
    default String embedFooterIcon() {
        return "https://github.com/pajlads/DinkPlugin/raw/master/icon.png";
    }

    @ConfigItem(
        keyName = "ignoredNames", // historical name, preserved for backwards compatibility
        name = "Filtered RSNs",
        description = "Restrict what player names can trigger notifications (One name per line)<br/>" +
            "This acts as an allowlist or denylist based on the 'RSN Filter Mode' setting below.",
        position = 1007,
        hidden = true
    )
    default String filteredNames() {
        return "";
    }

    @ConfigItem(
        keyName = "nameFilterMode",
        name = "RSN Filter Mode",
        description = "Allow Mode: Only allow notifications for RSNs on the list above (discouraged).<br/>" +
            "Deny Mode: Prevent notifications from RSNs on the list above (default/recommended).",
        position = 1008,
        hidden = true
    )
    default FilterMode nameFilterMode() {
        return FilterMode.DENY;
    }

    @ConfigItem(
        keyName = "playerLookupService",
        name = "Player Lookup Service",
        description = "The service used to lookup a players account, to make their name clickable in Discord embeds",
        position = 1009,
        hidden = true
    )
    default PlayerLookupService playerLookupService() {
        return PlayerLookupService.OSRS_HISCORE;
    }

    @ConfigItem(
        keyName = "screenshotHideChat",
        name = "Hide Chat in Images",
        description = "Whether to hide the chat box and private messages when capturing screenshots.<br/>" +
            "Note: visually you may notice the chat box momentarily flicker as it is hidden for the screenshot.",
        position = 1010,
        hidden = true
    )
    default boolean screenshotHideChat() {
        return true;
    }

    @ConfigItem(
        keyName = "sendDiscordUser",
        name = "Send Discord Profile",
        description = "Whether to send your discord user information to the webhook server via metadata",
        position = 1011,
        hidden = true
    )
    default boolean sendDiscordUser() {
        return true;
    }

    @ConfigItem(
        keyName = "sendClanName",
        name = "Send Clan Name",
        description = "Whether to send your clan information to the webhook server via metadata",
        position = 1012,
        hidden = true
    )
    default boolean sendClanName() {
        return true;
    }

    @ConfigItem(
        keyName = "sendGroupIronClanName",
        name = "Send GIM Clan Name",
        description = "Whether to send your group ironman clan information to the webhook server via metadata",
        position = 1013,
        hidden = true
    )
    default boolean sendGroupIronClanName() {
        return true;
    }

    @ConfigItem(
        keyName = "threadNameTemplate",
        name = "Forum Thread Name",
        description = "Thread name template to use for Discord Forum Channels<br/>" +
            "Use %TYPE% to insert the notification type<br/>" +
            "Use %MESSAGE% to insert the notification message<br/>" +
            "Use %USERNAME% to insert the player name",
        position = 1013,
        hidden = true
    )
    default String threadNameTemplate() {
        return "[%TYPE%] %MESSAGE%";
    }

    @ConfigItem(
        keyName = "metadataWebhook",
        name = "Custom Metadata Handler",
        description = "Webhook URL for custom handlers to receive regular information about the player.<br/>" +
            "Not recommended for use with Discord webhooks, as it could cause spam.<br/>" +
            "You can target multiple webhooks by specifying their URLs on separate lines",
        position = 1014,
        hidden = true
    )
    default String metadataWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "ignoreSeasonalWorlds",
        name = "Ignore Seasonal Worlds",
        description = "Whether to suppress notifications that occur on seasonal worlds like Leagues.",
        position = 1015,
        hidden = true
    )
    default boolean ignoreSeasonal() {
        return false;
    }

    @ConfigItem(
        keyName = "discordWebhook", // do not rename; would break old configs
        name = "Primary Webhook URLs",
        description = "The default webhook URL to send notifications to, if no override is specified.<br/>" +
            "You can target multiple webhooks by specifying their URLs on separate lines",
        position = -20,
        hidden = true
    )
    default String primaryWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "collectionWebhook",
        name = "Collection Webhook Override",
        description = "If non-empty, collection messages are sent to this URL, instead of the primary URL",
        position = -19,
        hidden = true
    )
    default String collectionWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "petWebhook",
        name = "Pet Webhook Override",
        description = "If non-empty, pet messages are sent to this URL, instead of the primary URL",
        position = -18,
        hidden = true
    )
    default String petWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "levelWebhook",
        name = "Level Webhook Override",
        description = "If non-empty, level up messages are sent to this URL, instead of the primary URL",
        position = -17,
        hidden = true
    )
    default String levelWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "lootWebhook",
        name = "Loot Webhook Override",
        description = "If non-empty, loot messages are sent to this URL, instead of the primary URL",
        position = -16,
        hidden = true
    )
    default String lootWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "deathWebhook",
        name = "Death Webhook Override",
        description = "If non-empty, death messages are sent to this URL, instead of the primary URL",
        position = -15,
        hidden = true
    )
    default String deathWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "slayerWebhook",
        name = "Slayer Webhook Override",
        description = "If non-empty, slayer messages are sent to this URL, instead of the primary URL",
        position = -14,
        hidden = true
    )
    default String slayerWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "questWebhook",
        name = "Quest Webhook Override",
        description = "If non-empty, quest messages are sent to this URL, instead of the primary URL",
        position = -13,
        hidden = true
    )
    default String questWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "clueWebhook",
        name = "Clue Webhook Override",
        description = "If non-empty, clue messages are sent to this URL, instead of the primary URL",
        position = -12,
        hidden = true
    )
    default String clueWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "speedrunWebhook",
        name = "Speedrun Webhook Override",
        description = "If non-empty, speedrun messages are sent to this URL, instead of the primary URL",
        position = -11,
        hidden = true
    )
    default String speedrunWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "killCountWebhook",
        name = "Kill Count Webhook Override",
        description = "If non-empty, kill count messages are sent to this URL, instead of the primary URL",
        position = -10,
        hidden = true
    )
    default String killCountWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "combatTaskWebhook",
        name = "Combat Task Webhook Override",
        description = "If non-empty, combat task messages are sent to this URL, instead of the primary URL",
        position = -9,
        hidden = true
    )
    default String combatTaskWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "diaryWebhook",
        name = "Diary Webhook Override",
        description = "If non-empty, achievement diary messages are sent to this URL, instead of the primary URL",
        position = -8,
        hidden = true
    )
    default String diaryWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "gambleWebhook",
        name = "BA Gamble Webhook Override",
        description = "If non-empty, BA gamble messages are sent to this URL, instead of the primary URL",
        position = -7,
        hidden = true
    )
    default String gambleWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "pkWebhook",
        name = "Player Kill Webhook Override",
        description = "If non-empty, PK messages are sent to this URL, instead of the primary URL",
        position = -6,
        hidden = true
    )
    default String pkWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "groupStorageWebhook",
        name = "Group Storage Webhook Override",
        description = "If non-empty, Group Storage messages are sent to this URL, instead of the primary URL",
        position = -5,
        hidden = true
    )
    default String groupStorageWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "grandExchangeWebhook",
        name = "Grand Exchange Webhook Override",
        description = "If non-empty, Grand Exchange messages are sent to this URL, instead of the primary URL",
        position = -4,
        hidden = true
    )
    default String grandExchangeWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "tradeWebhook",
        name = "Trade Webhook Override",
        description = "If non-empty, Trading messages are sent to this URL, instead of the primary URL",
        position = -3,
        hidden = true
    )
    default String tradeWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "chatWebhook",
        name = "Chat Webhook Override",
        description = "If non-empty, filtered chat messages are sent to this URL, instead of the primary URL",
        position = -2,
        hidden = true
    )
    default String chatWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "leaguesWebhook",
        name = "Leagues Webhook Override",
        description = "If non-empty, Leagues messages are sent to this URL, instead of the primary URL.<br/>" +
            "Note: this only applies to the Leagues notifier, not every notifier in a seasonal world",
        position = -1,
        hidden = true
    )
    default String leaguesWebhook() {
        return veribaseApiUrl;
    }

    @ConfigItem(
        keyName = "collectionLogEnabled",
        name = "Enable collection log",
        description = "Enable notifications for collection log.<br/>" +
            "Requires 'Chat > Collection log - New addition notification' setting to be enabled",
        position = 1
    )
    default boolean notifyCollectionLog() {
        return true;
    }

    @ConfigItem(
        keyName = "collectionSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 2,
        hidden = true
    )
    default boolean collectionSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "collectionNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %ITEM% to insert the item name<br/>" +
            "Use %COMPLETED% to insert the number of completed entries<br/>" +
            "Use %TOTAL_POSSIBLE% to insert the number of possible entries",
        position = 3,
        hidden = true
    )
    default String collectionNotifyMessage() {
        return "%USERNAME% has added %ITEM% to their collection";
    }

    @ConfigItem(
        keyName = "petEnabled",
        name = "Enable pets",
        description = "Enable notifications for obtaining pets",
        position = 10
    )
    default boolean notifyPet() {
        return true;
    }

    @ConfigItem(
        keyName = "petSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 11,
        hidden = true
    )
    default boolean petSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "petNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %GAME_MESSAGE% to insert the game message associated with this type of pet drop",
        position = 12,
        hidden = true
    )
    default String petNotifyMessage() {
        return "%USERNAME% %GAME_MESSAGE%";
    }

    @ConfigItem(
        keyName = "levelEnabled",
        name = "Enable level",
        description = "Enable notifications for gaining levels",
        position = 20,
        hidden = true
    )
    default boolean notifyLevel() {
        return true;
    }

    @ConfigItem(
        keyName = "levelSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 21,
        hidden = true
    )
    default boolean levelSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "levelNotifyVirtual",
        name = "Notify on Virtual Levels",
        description = "Whether level notifications should be fired beyond level 99.<br/>" +
            "Will also notify upon reaching 200M XP",
        position = 22
    )
    default boolean levelNotifyVirtual() {
        return true;
    }

    @ConfigItem(
        keyName = "levelNotifyCombat",
        name = "Notify for Combat Levels",
        description = "Whether notifications should occur for combat level increases",
        position = 23
    )
    default boolean levelNotifyCombat() {
        return true;
    }

    @ConfigItem(
        keyName = "levelInterval",
        name = "Notify Interval",
        description = "Level interval between when a notification should be sent",
        position = 24,
        hidden = true
    )
    default int levelInterval() {
        return 5;
    }

    @ConfigItem(
        keyName = "levelMinValue",
        name = "Minimum Skill Level",
        description = "The minimum skill level required to send a notification.<br/>" +
            "Useful for filtering out low-level notifications",
        position = 25,
        hidden = true
    )
    default int levelMinValue() {
        return 1;
    }

    @ConfigItem(
        keyName = "levelIntervalOverride",
        name = "Interval Override Level",
        description = "All level ups starting from this override level send a notification, disregarding the configured Notify Interval.<br/>" +
            "Disabled when set to 0",
        position = 26,
        hidden = true
    )
    default int levelIntervalOverride() {
        return 90;
    }

    @ConfigItem(
        keyName = "xpInterval",
        name = "Post-99 XP Interval",
        description = "XP interval at which to fire notifications (in millions).<br/>" +
            "Does not apply to skills that are below level 99.<br/>" +
            "Does <i>not</i> depend on the 'Notify on Virtual Levels' setting.<br/>" +
            "If enabled, fires for 200M XP, even if not divisible by the interval.<br/>" +
            "Disabled if set to 0",
        position = 27,
        hidden = true
    )
    @Units("M xp")
    @Range(max = Experience.MAX_SKILL_XP / 1_000_000) // [0, 200]
    default int xpInterval() {
        return 5;
    }

    @ConfigItem(
        keyName = "levelNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %SKILL% to insert the levelled skill(s)<br/>" +
            "Use %TOTAL_LEVEL% to insert the updated total level<br/>" +
            "Use %TOTAL_XP% to insert the updated overall experience",
        position = 29,
        hidden = true
    )
    default String levelNotifyMessage() {
        return "%USERNAME% has levelled %SKILL%";
    }

    @ConfigItem(
        keyName = "lootEnabled",
        name = "Enable loot",
        description = "Enable notifications for gaining loot",
        position = 30
    )
    default boolean notifyLoot() {
        return true;
    }

    @ConfigItem(
        keyName = "lootSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 31,
        hidden = true
    )
    default boolean lootSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "lootIcons",
        name = "Show loot icons",
        description = "Show icons for the loot obtained as additional embeds",
        position = 32,
        hidden = true
    )
    default boolean lootIcons() {
        return false;
    }

    @ConfigItem(
        keyName = "minLootValue",
        name = "Min Loot value",
        description = "The minimum value of an item for a notification to be sent.<br/>" +
            "For PK chests, the <i>total</i> value of the items is compared with this threshold",
        position = 33,
        hidden = true
    )
    default int minLootValue() {
        return 250000;
    }

    @ConfigItem(
        keyName = "lootImageMinValue",
        name = "Screenshot Min Value",
        description = "The minimum combined loot value to send a screenshot.<br/>" +
            "Must have 'Send Image' enabled",
        position = 34,
        hidden = true
    )
    default int lootImageMinValue() {
        return 250000;
    }

    @ConfigItem(
        keyName = "lootIncludePlayer",
        name = "Include PK Loot",
        description = "Allow notifications for loot from player kills",
        position = 35,
        hidden = true
    )
    default boolean includePlayerLoot() {
        return false;
    }

    @ConfigItem(
        keyName = "lootRedirectPlayerKill",
        name = "Send PK Loot to PK URL",
        description = "Whether to send PK loot to the PK override webhook URL, rather than the loot URL.<br/>" +
            "Must have 'Include PK Loot' (above) enabled.<br/>" +
            "Has no effect if the Player Kills notifier override URL is absent",
        position = 35,
        hidden = true
    )
    default boolean lootRedirectPlayerKill() {
        return false;
    }

    @ConfigItem(
        keyName = "lootIncludeClueScrolls",
        name = "Include Clue Loot",
        description = "Allow notifications for loot from Clue Scrolls",
        position = 36,
        hidden = true
    )
    default boolean lootIncludeClueScrolls() {
        return true;
    }

    @ConfigItem(
        keyName = "lootItemAllowlist",
        name = "Item Allowlist",
        description = "Always fire notifications for these items, despite value settings.<br/>" +
            "Place one item name per line (case-insensitive; asterisks are wildcards)",
        position = 37,
        hidden = true
    )
    default String lootItemAllowlist() {
        return "";
    }

    @ConfigItem(
        keyName = "lootItemDenylist",
        name = "Item Denylist",
        description = "Never fire notifications for these items, despite value or rarity settings.<br/>" +
            "Place one item name per line (case-insensitive; asterisks are wildcards)",
        position = 37,
        hidden = true
    )
    default String lootItemDenylist() {
        return "";
    }

    @ConfigItem(
        keyName = "lootRarityThreshold",
        name = "Rarity Override (1 in X)",
        description = "Fires notifications for sufficiently rare drops, despite the 'Min Loot value' threshold.<br/>" +
            "Corresponds to a 1 in X chance. For example, 100 notifies for items with 1% drop rate or rarer.<br/>" +
            "Has no effect when set to zero.<br/>" +
            "Currently only applies to NPC drops",
        position = 38,
        hidden = true
    )
    default int lootRarityThreshold() {
        return 0;
    }

    @ConfigItem(
        keyName = "lootNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %LOOT% to insert the loot<br/>" +
            "Use %SOURCE% to show the source of the loot",
        position = 39,
        hidden = true
    )
    default String lootNotifyMessage() {
        return "%USERNAME% has looted: \n\n%LOOT%\nFrom: %SOURCE%";
    }

    @ConfigItem(
        keyName = "deathEnabled",
        name = "Enable Death",
        description = "Enable notifications for when you die",
        position = 40
    )
    default boolean notifyDeath() {
        return true;
    }

    @ConfigItem(
        keyName = "deathSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 41,
        hidden = true
    )
    default boolean deathSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "deathEmbedProtected",
        name = "Embed Kept Items",
        description = "Whether embeds of the protected items should be sent to the webhook",
        position = 42,
        hidden = true
    )
    default boolean deathEmbedKeptItems() {
        return false;
    }

    @ConfigItem(
        keyName = "deathIgnoreSafe",
        name = "Ignore Safe Deaths",
        description = "Whether deaths in safe areas should be ignored.<br/>" +
            "Exceptions to this rule can be configured below",
        position = 43,
        hidden = true
    )
    default boolean deathIgnoreSafe() {
        return false;
    }

    @ConfigItem(
        keyName = "deathSafeExceptions",
        name = "Safe Exceptions",
        description = "Safe deaths that should trigger notifications even when 'Ignore Safe Deaths' is enabled.<br/>" +
            "Hold Control while clicking on the options to select multiple exceptions",
        position = 44,
        hidden = true
    )
    default Set<ExceptionalDeath> deathSafeExceptions() {
        return EnumSet.of(ExceptionalDeath.FIGHT_CAVE, ExceptionalDeath.INFERNO);
    }

    @ConfigItem(
        keyName = "deathIgnoredRegions",
        name = "Ignored Region IDs",
        description = "User-specified, comma-separated Region IDs where deaths should be ignored.<br/>" +
            "Use the '::veribaseregion' chat command or an online map to obtain the region IDs.<br/>" +
            "For example, Prifddinas spans 12894, 12895, 13150, 13151",
        position = 45,
        hidden = true
    )
    default String deathIgnoredRegions() {
        return "123, 456, 789";
    }

    @ConfigItem(
        keyName = "deathMinValue",
        name = "Min Lost Value",
        description = "The minimum value of the lost items for a notification to be sent.<br/>" +
            "This setting does not apply for safe deaths",
        position = 46,
        hidden = true
    )
    default int deathMinValue() {
        return 0;
    }

    @ConfigItem(
        keyName = "deathNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %VALUELOST% to insert the GE value of the stuff you lost",
        position = 47,
        hidden = true
    )
    default String deathNotifyMessage() {
        return "%USERNAME% has died...";
    }

    @ConfigItem(
        keyName = "deathNotifPvpEnabled",
        name = "Distinguish PvP deaths",
        description = "Should the plugin use a different message for dying in PvP?",
        position = 48,
        hidden = true
    )
    default boolean deathNotifPvpEnabled() {
        return true;
    }

    @ConfigItem(
        keyName = "deathNotifPvpMessage",
        name = "PvP notification message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %PKER% to insert the killer<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %VALUELOST% to insert the GE value of the stuff you lost",
        position = 49,
        hidden = true
    )
    default String deathNotifPvpMessage() {
        return "%USERNAME% has just been PKed by %PKER% for %VALUELOST% gp...";
    }

    @ConfigItem(
        keyName = "slayerEnabled",
        name = "Enable Slayer",
        description = "Enable notifications for when you complete a slayer task",
        position = 50,
        hidden = true
    )
    default boolean notifySlayer() {
        return true;
    }

    @ConfigItem(
        keyName = "slayerSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 51,
        hidden = true
    )
    default boolean slayerSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "slayerPointThreshold",
        name = "Min Slayer Points",
        description = "The minimum slayer task points to warrant a notification",
        position = 52,
        hidden = true
    )
    default int slayerPointThreshold() {
        return 0;
    }

    @ConfigItem(
        keyName = "slayerNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %TASK% to insert your task<br/>" +
            "Use %POINTS% to show how many points you obtained<br/>" +
            "Use %TASKCOUNT% to show how many tasks you have completed",
        position = 53,
        hidden = true
    )
    default String slayerNotifyMessage() {
        return "%USERNAME% has completed a slayer task: %TASK%, getting %POINTS% points and making that %TASKCOUNT% tasks completed";
    }

    @ConfigItem(
        keyName = "questEnabled",
        name = "Enable Quest",
        description = "Enable notifications for when you complete a quest",
        position = 60,
        hidden = true
    )
    default boolean notifyQuest() {
        return true;
    }

    @ConfigItem(
        keyName = "questSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 61,
        hidden = true
    )
    default boolean questSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "questNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %QUEST% to insert the quest that you completed",
        position = 62,
        hidden = true
    )
    default String questNotifyMessage() {
        return "%USERNAME% has completed a quest: %QUEST%";
    }

    @ConfigItem(
        keyName = "clueEnabled",
        name = "Enable Clue Scrolls",
        description = "Enable notifications for when you complete a clue scroll",
        position = 70
    )
    default boolean notifyClue() {
        return true;
    }

    @ConfigItem(
        keyName = "clueSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 71,
        hidden = true
    )
    default boolean clueSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "clueShowItems",
        name = "Show Item Icons",
        description = "Show item icons gained from the clue as embeds",
        position = 72,
        hidden = true
    )
    default boolean clueShowItems() {
        return false;
    }

    @ConfigItem(
        keyName = "clueMinTier",
        name = "Min Tier",
        description = "The minimum tier of the clue scroll for a notification to be sent",
        position = 73,
        hidden = true
    )
    default ClueTier clueMinTier() {
        return ClueTier.BEGINNER;
    }

    @ConfigItem(
        keyName = "clueMinValue",
        name = "Min Value",
        description = "The minimum value of the combined items for a notification to be sent",
        position = 74,
        hidden = true
    )
    default int clueMinValue() {
        return 0;
    }

    @ConfigItem(
        keyName = "clueImageMinValue",
        name = "Screenshot Min Value",
        description = "The minimum combined value of the items to send a screenshot.<br/>" +
            "Must have 'Send Image' enabled",
        position = 75,
        hidden = true
    )
    default int clueImageMinValue() {
        return 0;
    }

    @ConfigItem(
        keyName = "clueNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %CLUE% to insert the clue type<br/>" +
            "Use %LOOT% to show the loot obtained<br/>" +
            "Use %COUNT% to insert how many of those clue types you have completed",
        position = 76,
        hidden = true
    )
    default String clueNotifyMessage() {
        return "%USERNAME% has completed a %CLUE% clue, they have completed %COUNT%.\nThey obtained:\n\n%LOOT%";
    }

    @ConfigItem(
        keyName = "speedrunEnabled",
        name = "Enable speedruns",
        description = "Enable notifications for when you complete a speedrun",
        position = 80
    )
    default boolean notifySpeedrun() {
        return true;
    }

    @ConfigItem(
        keyName = "speedrunSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 81,
        hidden = true
    )
    default boolean speedrunSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "speedrunPBOnly",
        name = "Notify on Personal Best only",
        description = "Enable notifications only for your best runs",
        position = 82,
        hidden = true
    )
    default boolean speedrunPBOnly() {
        return true;
    }

    @ConfigItem(
        keyName = "speedrunPBMessage",
        name = "PB message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/> +" +
            "Use %QUEST% to insert the quest name<br/>" +
            "Use %TIME% to insert your new time",
        position = 83,
        hidden = true
    )
    default String speedrunPBMessage() {
        return "%USERNAME% has just beat their personal best in a speedrun of %QUEST% with a time of %TIME%";
    }

    @ConfigItem(
        keyName = "speedrunMessage",
        name = "Notification message",
        description = "The message to be sent through the webhook.<br/> +" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %QUEST% to insert the quest name<br/>" +
            "Use %TIME% to insert your new time<br/>" +
            "Use %BEST% to insert your PB",
        position = 84,
        hidden = true
    )
    default String speedrunMessage() {
        return "%USERNAME% has just finished a speedrun of %QUEST% with a time of %TIME% (their PB is %BEST%)";
    }

    @ConfigItem(
        keyName = "killCountEnabled",
        name = "Enable Kill Count",
        description = "Enable notifications for boss kill count milestones",
        position = 90
    )
    default boolean notifyKillCount() {
        return true;
    }

    @ConfigItem(
        keyName = "killCountSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 91,
        hidden = true
    )
    default boolean killCountSendImage() {
        return true;
    }

    @ConfigItem(
        keyName = "killCountInitial",
        name = "Initial Boss Kill",
        description = "Notify on the first ever kill of any boss",
        position = 92,
        hidden = true
    )
    default boolean killCountNotifyInitial() {
        return true;
    }

    @ConfigItem(
        keyName = "killCountPB",
        name = "Personal Best",
        description = "Notify on achieving a new personal best time",
        position = 93,
        hidden = true
    )
    default boolean killCountNotifyBestTime() {
        return true;
    }

    @ConfigItem(
        keyName = "killCountInterval",
        name = "Kill Count Interval",
        description = "Interval between when a notification should be sent",
        position = 94,
        hidden = true
    )
    default int killCountInterval() {
        return 50;
    }

    @ConfigItem(
        keyName = "killCountPenanceQueen",
        name = "Barbarian Assault",
        description = "Notify for any Penance Queen kills",
        position = 95,
        hidden = true
    )
    default boolean killCountPenanceQueen() {
        return true;
    }

    @ConfigItem(
        keyName = "killCountMessage",
        name = "Notification Message",
        description = "The message to be sent to the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %BOSS% to insert the NPC name<br/>" +
            "Use %COUNT% to insert the kill count",
        position = 96,
        hidden = true
    )
    default String killCountMessage() {
        return "%USERNAME% has defeated %BOSS% with a completion count of %COUNT%";
    }

    @ConfigItem(
        keyName = "killCountBestTimeMessage",
        name = "PB Notification Message",
        description = "The message to be sent to the webhook upon a personal best time.<br/>" +
            "Use %USERNAME% to insert your username,<br/>" +
            "Use %BOSS% to insert the NPC name<br/>" +
            "Use %COUNT% to insert the kill count<br/>" +
            "Use %TIME% to insert the completion time",
        position = 97,
        hidden = true
    )
    default String killCountBestTimeMessage() {
        return "%USERNAME% has defeated %BOSS% with a new personal best time of %TIME% and a completion count of %COUNT%";
    }

    @ConfigItem(
        keyName = "combatTaskEnabled",
        name = "Enable Combat Tasks",
        description = "Enable notifications for combat achievements",
        position = 100
    )
    default boolean notifyCombatTask() {
        return true;
    }

    @ConfigItem(
        keyName = "combatTaskSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 101,
        hidden = true
    )
    default boolean combatTaskSendImage() {
        return true;
    }

    @ConfigItem(
        keyName = "combatTaskMinTier",
        name = "Min Tier",
        description = "Minimum combat achievement tier to warrant a notification",
        position = 102,
        hidden = true
    )
    default CombatAchievementTier minCombatAchievementTier() {
        return CombatAchievementTier.EASY;
    }

    @ConfigItem(
        keyName = "combatTaskMessage",
        name = "Notification Message",
        description = "The message to be sent to the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %TIER% to insert the task tier<br/>" +
            "Use %TASK% to insert the task name<br/>" +
            "Use %POINTS% to insert the task points<br/>" +
            "Use %TOTAL_POINTS% to insert the total points earned across tasks",
        position = 103,
        hidden = true
    )
    default String combatTaskMessage() {
        return "%USERNAME% has completed %TIER% combat task: %TASK%";
    }

    @ConfigItem(
        keyName = "combatTaskUnlockMessage",
        name = "Reward Unlock Notification Message",
        description = "The message to be sent to the webhook upon unlocking the rewards for a tier completion.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %TIER% to insert the task tier<br/>" +
            "Use %TASK% to insert the task name<br/>" +
            "Use %POINTS% to insert the task points<br/>" +
            "Use %TOTAL_POINTS% to insert the total points earned across tasks<br/>" +
            "Use %COMPLETED% to insert the completed tier",
        position = 104,
        hidden = true
    )
    default String combatTaskUnlockMessage() {
        return "%USERNAME% has unlocked the rewards for the %COMPLETED% tier, by completing the combat task: %TASK%";
    }

    @ConfigItem(
        keyName = "diaryEnabled",
        name = "Enable Diary",
        description = "Enable notifications for achievement diary completions",
        position = 110
    )
    default boolean notifyAchievementDiary() {
        return true;
    }

    @ConfigItem(
        keyName = "diarySendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 111,
        hidden = true
    )
    default boolean diarySendImage() {
        return true;
    }

    @ConfigItem(
        keyName = "diaryMinDifficulty",
        name = "Min Difficulty",
        description = "Minimum achievement diary difficulty to warrant a notification",
        position = 112,
        hidden = true
    )
    default AchievementDiary.Difficulty minDiaryDifficulty() {
        return AchievementDiary.Difficulty.EASY;
    }

    @ConfigItem(
        keyName = "diaryMessage",
        name = "Notification Message",
        description = "The message to be sent to the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %DIFFICULTY% to insert the diary difficulty<br/>" +
            "Use %AREA% to insert the diary area<br/>" +
            "Use %TOTAL% to insert the total diaries completed<br/>" +
            "Use %TASKS_COMPLETE% to insert the tasks completed across all diaries<br/>" +
            "Use %TASKS_TOTAL% to insert the total tasks possible across all diaries<br/>" +
            "Use %AREA_TASKS_COMPLETE% to insert the tasks completed within the area<br/>" +
            "Use %AREA_TASKS_TOTAL% to insert the total tasks possible within the area",
        position = 113,
        hidden = true
    )
    default String diaryNotifyMessage() {
        return "%USERNAME% has completed the %DIFFICULTY% %AREA% Achievement Diary, for a total of %TOTAL% diaries completed";
    }

    @ConfigItem(
        keyName = "gambleEnabled",
        name = "Enable BA Gamble",
        description = "Enable notifications for Barbarian Assault high level gambles",
        position = 120,
        hidden = true
    )
    default boolean notifyGamble() {
        return false;
    }

    @ConfigItem(
        keyName = "gambleSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 121,
        hidden = true
    )
    default boolean gambleSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "gambleInterval",
        name = "Notify Interval",
        description = "Interval between when a notification should be sent",
        position = 122,
        hidden = true
    )
    @Range(min = 1)
    default int gambleInterval() {
        return 100;
    }

    @ConfigItem(
        keyName = "gambleRareLoot",
        name = "Always notify for rare loot",
        description = "Always send a notification upon receiving a dragon chainbody or med helm from a gamble",
        position = 123,
        hidden = true
    )
    default boolean gambleRareLoot() {
        return true;
    }

    @ConfigItem(
        keyName = "gambleNotifMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook every gamble interval.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %COUNT% to insert the gamble count<br/>" +
            "Use %LOOT% to insert the loot",
        position = 124,
        hidden = true
    )
    default String gambleNotifyMessage() {
        return "%USERNAME% has reached %COUNT% high gambles";
    }

    @ConfigItem(
        keyName = "gambleRareNotifMessage",
        name = "Rare Loot Notification Message",
        description = "The message to be sent through the webhook for rare loot.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %COUNT% to insert the gamble count<br/>" +
            "Use %LOOT% to insert the loot",
        position = 125,
        hidden = true
    )
    default String gambleRareNotifyMessage() {
        return "%USERNAME% has received rare loot at gamble count %COUNT%: \n\n%LOOT%";
    }

    @ConfigItem(
        keyName = "pkEnabled",
        name = "Enable Player Kills",
        description = "Enable notifications upon killing other players",
        position = 130,
        hidden = true
    )
    default boolean notifyPk() {
        return false;
    }

    @ConfigItem(
        keyName = "pkSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 131,
        hidden = true
    )
    default boolean pkSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "pkSkipSafe",
        name = "Ignore Safe Deaths",
        description = "Whether notifications should be skipped for kills in safe areas.<br/>" +
            "This includes Clan Wars, Duel Arena, Fight Pit, Last Man Standing, etc.",
        position = 132,
        hidden = true
    )
    default boolean pkSkipSafe() {
        return true;
    }

    @ConfigItem(
        keyName = "pkSkipFriendly",
        name = "Ignore Friendlies",
        description = "Whether notifications should be skipped upon killing friends, clan members, or team mates",
        position = 133,
        hidden = true
    )
    default boolean pkSkipFriendly() {
        return false;
    }

    @ConfigItem(
        keyName = "pkMinValue",
        name = "Min Value",
        description = "The minimum value of the victim's visible equipment to send a notification.<br/>" +
            "This does not include equipment you cannot see or items in their inventory.",
        position = 134,
        hidden = true
    )
    default int pkMinValue() {
        return 0;
    }

    @ConfigItem(
        keyName = "pkIncludeLocation",
        name = "Include Location",
        description = "Whether notifications should include the world and location of the killed player.",
        position = 135,
        hidden = true
    )
    default boolean pkIncludeLocation() {
        return true;
    }

    @ConfigItem(
        keyName = "pkNotifyMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %TARGET% to insert the victim's username",
        position = 136,
        hidden = true
    )
    default String pkNotifyMessage() {
        return "%USERNAME% has PK'd %TARGET%";
    }

    @ConfigItem(
        keyName = "groupStorageEnabled",
        name = "Enable Transactions",
        description = "Enable notifications upon group storage transactions",
        position = 140,
        hidden = true
    )
    default boolean notifyGroupStorage() {
        return false;
    }

    @ConfigItem(
        keyName = "groupStorageSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 141,
        hidden = true
    )
    default boolean groupStorageSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "groupStorageMinValue",
        name = "Min Value",
        description = "The minimum value of the deposits or withdrawals to send a notification",
        position = 142,
        hidden = true
    )
    default int groupStorageMinValue() {
        return 0;
    }

    @ConfigItem(
        keyName = "groupStorageIncludeClan",
        name = "Include Group Name",
        description = "Whether notifications should include the GIM clan name",
        position = 143,
        hidden = true
    )
    default boolean groupStorageIncludeClan() {
        return true;
    }

    @ConfigItem(
        keyName = "groupStorageIncludePrice",
        name = "Include Price",
        description = "Whether price should be included on individual items,<br/>" +
            "and a Net Value field generated for notifications",
        position = 144,
        hidden = true
    )
    default boolean groupStorageIncludePrice() {
        return true;
    }

    @ConfigItem(
        keyName = "groupStorageNotifyMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %DEPOSITED% to insert the list of deposited items<br/>" +
            "Use %WITHDRAWN% to insert the list of withdrawn items",
        position = 145,
        hidden = true
    )
    default String groupStorageNotifyMessage() {
        return "%USERNAME% has deposited:\n%DEPOSITED%\n\n%USERNAME% has withdrawn:\n%WITHDRAWN%";
    }

    @ConfigItem(
        keyName = "notifyGrandExchange",
        name = "Enable GE Transactions",
        description = "Enable notifications upon grand exchange transactions",
        position = 150,
        hidden = true
    )
    default boolean notifyGrandExchange() {
        return false;
    }

    @ConfigItem(
        keyName = "grandExchangeSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 151,
        hidden = true
    )
    default boolean grandExchangeSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "grandExchangeIncludeCancelled",
        name = "Include Cancelled",
        description = "Enable notifications upon cancelling offers.<br/>" +
            "Cancellation events require the trade's partial progress to exceed the configured minimum value.<br/>" +
            "If 'Min Value' is set to 0, then cancellation events will also fire for completely unfilled orders",
        position = 152,
        hidden = true
    )
    default boolean grandExchangeIncludeCancelled() {
        return false;
    }

    @ConfigItem(
        keyName = "grandExchangeMinValue",
        name = "Min Value",
        description = "The minimum value of the transacted items to send a notification",
        position = 153,
        hidden = true
    )
    default int grandExchangeMinValue() {
        return 100_000;
    }

    @Range(min = -1)
    @Units(Units.MINUTES)
    @ConfigItem(
        keyName = "grandExchangeProgressSpacingMinutes",
        name = "In Progress Spacing",
        description = "The number of minutes that must pass since the last notification to notify for an in-progress trade.<br/>" +
            "Set to -1 to never notify for in-progress trades",
        position = 154,
        hidden = true
    )
    default int grandExchangeProgressSpacingMinutes() {
        return -1; // corresponds to no notifications for partial progress
    }

    @ConfigItem(
        keyName = "grandExchangeNotifyMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %TYPE% to insert the type of transaction (bought or sold)<br/>" +
            "Use %ITEM% to insert the transacted item<br/>" +
            "Use %STATUS% to insert the trade status (e.g., Completed, In Progress, Cancelled)",
        position = 155,
        hidden = true
    )
    default String grandExchangeNotifyMessage() {
        return "%USERNAME% %TYPE% %ITEM% on the GE";
    }

    @ConfigItem(
        keyName = "notifyTrades",
        name = "Enable Trades",
        description = "Enable notifications upon completed player trades",
        position = 160,
        hidden = true
    )
    default boolean notifyTrades() {
        return false;
    }

    @ConfigItem(
        keyName = "tradeSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 161,
        hidden = true
    )
    default boolean tradeSendImage() {
        return true;
    }

    @ConfigItem(
        keyName = "tradeMinValue",
        name = "Min Value",
        description = "The minimum total value of the traded items to send a notification",
        position = 162,
        hidden = true
    )
    default int tradeMinValue() {
        return 0;
    }

    @ConfigItem(
        keyName = "tradeNotifyMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %COUNTERPARTY% to insert the name of the other player<br/>" +
            "Use %IN_VALUE% to insert the value of the items received from the counterparty<br/>" +
            "Use %OUT_VALUE% to insert the value of the items given to the counterparty",
        position = 163,
        hidden = true
    )
    default String tradeNotifyMessage() {
        return "%USERNAME% traded with %COUNTERPARTY%";
    }

    @ConfigItem(
        keyName = "notifyChat",
        name = "Enable Chat Notifications",
        description = "Enable notifications upon receiving specific chat messages",
        position = 170,
        hidden = true
    )
    default boolean notifyChat() {
        return false;
    }

    @ConfigItem(
        keyName = "chatSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 171,
        hidden = true
    )
    default boolean chatSendImage() {
        return false;
    }

    @ConfigItem(
        keyName = "chatMessageTypes",
        name = "Message Types",
        description = "The types of chat messages that can trigger notifications",
        position = 172,
        hidden = true
    )
    default Set<ChatNotificationType> chatMessageTypes() {
        return EnumSet.of(ChatNotificationType.GAME);
    }

    @ConfigItem(
        keyName = ChatNotifier.PATTERNS_CONFIG_KEY,
        name = "Message Filters",
        description = "The chat message patterns that should trigger notifications.<br/>" +
            "Place one pattern per line (case-insensitive; asterisks are wildcards)",
        position = 173,
        hidden = true
    )
    default String chatPatterns() {
        return "You've unlocked an emote: *\n" +
            "You've completed the * event*\n" + // for holiday events
            "You have accepted * into *.\n" + // for clan recruitment
            "You will be logged out in approximately 30 minutes.*\n" +
            "You will be logged out in approximately 10 minutes.*\n";
    }

    @ConfigItem(
        keyName = "chatNotifyMessage",
        name = "Notification Message",
        description = "The message to be sent through the webhook.<br/>" +
            "Use %USERNAME% to insert your username<br/>" +
            "Use %MESSAGE% to insert the chat message",
        position = 174,
        hidden = true
    )
    default String chatNotifyMessage() {
        return "%USERNAME% received a chat message:\n\n```\n%MESSAGE%\n```";
    }

    @ConfigItem(
        keyName = "notifyLeagues",
        name = "Enable Leagues",
        description = "Enable notifications upon various leagues events",
        position = 200,
        hidden = true
    )
    default boolean notifyLeagues() {
        return false;
    }

    @ConfigItem(
        keyName = "leaguesSendImage",
        name = "Send Image",
        description = "Send image with the notification",
        position = 201,
        hidden = true
    )
    default boolean leaguesSendImage() {
        return true;
    }

    @ConfigItem(
        keyName = "leaguesAreaUnlock",
        name = "Send Area Unlocks",
        description = "Send notifications upon area unlocks",
        position = 202,
        hidden = true
    )
    default boolean leaguesAreaUnlock() {
        return true;
    }

    @ConfigItem(
        keyName = "leaguesRelicUnlock",
        name = "Send Relic Unlocks",
        description = "Send notifications upon relic unlocks",
        position = 203,
        hidden = true
    )
    default boolean leaguesRelicUnlock() {
        return true;
    }

    @ConfigItem(
        keyName = "leaguesTaskCompletion",
        name = "Send Completed Tasks",
        description = "Send notifications upon completing a task",
        position = 204,
        hidden = true
    )
    default boolean leaguesTaskCompletion() {
        return true;
    }

    @ConfigItem(
        keyName = "leaguesTaskMinTier",
        name = "Task Min Difficulty",
        description = "The minimum tier of a task for a notification to be sent",
        position = 205,
        hidden = true
    )
    default LeagueTaskDifficulty leaguesTaskMinTier() {
        return LeagueTaskDifficulty.EASY;
    }

}
