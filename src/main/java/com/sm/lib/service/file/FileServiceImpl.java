package com.sm.lib.service.file;

import io.vertx.ext.web.FileUpload;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@ApplicationScoped
public class FileServiceImpl implements FileService {

    @Override
    public String read(String path) {
        try {
            Path filePath = Paths.get(path);
            return Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException ignored){
        }
        return null;
    }

    @Override
    public void write(String path, String content) {
        try {
            Path filePath = Paths.get(path);
            // Đảm bảo thư mục chứa file tồn tại
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            Files.writeString(filePath, content, StandardCharsets.UTF_8);
        } catch (IOException ignored){
        }
    }

    @Override
    public void delete(String path) {
        try {
            Path filePath = Paths.get(path);
            Files.deleteIfExists(filePath);
        } catch (IOException ignored){
        }
    }

    @Override
    public void saveAndReplace(String path, FileUpload fileUpload) {
        try {
            // Xóa file cũ nếu tồn tại
            Files.deleteIfExists(getPath(path));
            Files.move(Paths.get(fileUpload.uploadedFileName()), getPath(path), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored){
        }
    }

    private Path getPath(String path){
        return Paths.get(path);
    }
}
