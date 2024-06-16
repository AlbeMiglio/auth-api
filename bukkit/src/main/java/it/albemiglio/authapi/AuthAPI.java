package it.albemiglio.authapi;

import it.albemiglio.authapi.services.ConfigService;
import it.albemiglio.authapi.services.ModuleService;
import it.albemiglio.authapi.services.WebService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AuthAPI extends JavaPlugin {

    private ConfigService configService;
    private ModuleService moduleService;
    private WebService webService;

    @Override
    public void onEnable() {
        configService = new ConfigService(this);
        moduleService = new ModuleService(configService.readSecret());
        webService = new WebService(this);
        getLogger().info("AuthAPI enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("AuthAPI disabled");
    }
}
