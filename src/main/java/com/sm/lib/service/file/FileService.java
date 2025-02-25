package com.sm.lib.service.file;

import io.vertx.ext.web.FileUpload;

import java.io.IOException;

public interface FileService {

    /**
     * Đọc nội dung từ file.
     *
     * @param path Đường dẫn tới file cần đọc.
     * @return Nội dung của file.
     * @throws IOException Nếu có lỗi xảy ra trong quá trình đọc file.
     */
    String read(String path) throws IOException;

    /**
     * Ghi đè nội dung mới vào file đã có.
     *
     * @param path Đường dẫn tới file cần ghi.
     * @param content Nội dung mới cần ghi vào file.
     * @throws IOException Nếu có lỗi xảy ra trong quá trình ghi file.
     */
    void write(String path, String content) throws IOException;

    /**
     * Xóa file.
     *
     * @param path Đường dẫn tới file cần xóa.
     * @throws IOException Nếu có lỗi xảy ra trong quá trình xóa file.
     */
    void delete(String path) throws IOException;

    /**
     * Lưu và ghi đè file
     * @param path Đường dẫn tới file cần lưu hoặc ghi đè
     */
    void saveAndReplace(String path, FileUpload fileUpload) throws IOException;
}
