package de.harrisblog.nms.data;

import de.harrisblog.nms.versions.spigot1_19_4.NmsUtil1_19_4;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public abstract class CustomEffect {
    private String name;

    private String usage;

    private String description;

    private EffectType type;

    public CustomEffect(String name, String usage, String description, String type) {
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.type = NmsUtil1_19_4.getEffectTypeFromString(type);
    }

    public abstract void run(Event paramEvent, String[] paramArrayOfString, @Nullable Player p);

    public abstract void cancel(Event paramEvent, String[] paramArrayOfString);

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EffectType getType() {
        return this.type;
    }

    public void setType(EffectType type) {
        this.type = type;
    }
}
