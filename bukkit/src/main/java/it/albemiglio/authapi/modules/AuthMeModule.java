package it.albemiglio.authapi.modules;

import it.albemiglio.authapi.exceptions.AuthModuleInitializationException;
import org.bukkit.Bukkit;

public class AuthMeModule implements AuthModule {

    public AuthMeModule() {
    }

    @Override
    public void initialize() throws AuthModuleInitializationException {
        if (!Bukkit.getPluginManager().isPluginEnabled("AuthMe")) {
            throw new AuthModuleInitializationException("AuthMe not found!");
        }
    }

    @Override
    public boolean authenticate(String username, String password) {
        return fr.xephi.authme.api.v3.AuthMeApi.getInstance().checkPassword(username, password);
    }
}
