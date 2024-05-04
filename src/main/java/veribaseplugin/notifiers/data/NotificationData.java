package veribaseplugin.notifiers.data;

import veribaseplugin.message.Field;

import java.util.Collections;
import java.util.List;

public abstract class NotificationData {
    public List<Field> getFields() {
        return Collections.emptyList();
    }
}
