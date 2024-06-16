package it.albemiglio.authapi.services;

import it.mycraft.powerlib.bukkit.config.BukkitConfigManager;
import it.mycraft.powerlib.common.configuration.ConfigManager;
import it.mycraft.powerlib.common.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigService {

    private final ConfigManager configManager;

    public ConfigService(JavaPlugin plugin) {
        this.configManager = new BukkitConfigManager(plugin);
        load();
    }

    public void load() {
        configManager.create("config.yml");
    }

    public Configuration getConfig() {
        return configManager.get("config.yml");
    }

    public String readSecret() {
        return getConfig().getString("secret-key");
    }

    public int readPort() {
        return getConfig().getInt("endpoint-port");
    }
}
