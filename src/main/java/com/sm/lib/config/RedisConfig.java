package com.sm.lib.config;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
public class RedisConfig {
    private String singleRequestScript;

    public RedisConfig() {
        loadScripts();
    }

    private void loadScripts() {
        try {
            this.singleRequestScript = Files.readString(Path.of("src/main/resources/script/redis/single-request.lua"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load RedisService script", e);
        }
    }

    public String getSingleRequestScript() {
        return singleRequestScript;
    }

}
