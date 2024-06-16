package it.albemiglio.authapi.services;

import it.albemiglio.authapi.exceptions.AuthModuleInitializationException;
import it.albemiglio.authapi.modules.AuthModule;
import lombok.Getter;
import org.reflections.Reflections;

import java.util.*;

public class ModuleService {

    @Getter
    private AuthModule activeModule;
    private String secretKey;

    public ModuleService(String secretKey) {
        this.secretKey = secretKey;
        Set<AuthModule> modules = (Set<AuthModule>) loadModules();

        for (AuthModule module : modules) {
            try {
                module.initialize();
                activeModule = module;
                break;
            } catch (AuthModuleInitializationException ignored) {}
        }

        if (activeModule == null) {
            throw new IllegalStateException("No active authentication modules found.");
        }
    }

    private Collection<AuthModule> loadModules() {
        Set<AuthModule> modules = new HashSet<>();
        Reflections reflections = new Reflections("it.albemiglio.authapi.modules");
        Set<Class<? extends AuthModule>> classes = reflections.getSubTypesOf(AuthModule.class);

        for (Class<? extends AuthModule> clazz : classes) {
            try {
                AuthModule module = clazz.getDeclaredConstructor().newInstance();
                modules.add(module);
            } catch (Exception ignored) {}
        }

        return modules;
    }

    public int authenticate(String username, String password, String secretKey) {
        if(!secretKey.equals(this.secretKey))
            return -1;
        return activeModule.authenticate(username, password) ? 1 : 0;
    }
}
