package it.albemiglio.authapi.modules;

import it.albemiglio.authapi.exceptions.AuthModuleInitializationException;

public interface AuthModule {
    boolean authenticate(String username, String password);
    void initialize() throws AuthModuleInitializationException;
}

