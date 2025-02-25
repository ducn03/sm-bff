package com.sm.lib.service.file;

import jakarta.inject.Singleton;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
public class FileProperties {
    private final String USER_DIR = "user.dir";

    public Path getLocation(String path){
        return Paths.get(System.getProperty(USER_DIR), path);
    }

    public File getFile(String path, String fileName){
        return Paths.get(System.getProperty(USER_DIR), path, fileName).toFile();
    }

    public String getPath(String path, String fileName){
        return Paths.get(System.getProperty(USER_DIR), path, fileName).toString();
    }

}
