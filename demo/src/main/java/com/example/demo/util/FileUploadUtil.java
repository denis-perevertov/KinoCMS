package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    @Value("${upload.path}")
    private static String uploadPath;

    public static void saveFile(String uploadDir, String fileName, MultipartFile picture) throws IOException {

        File uploadDirectory = new File(uploadDir);

        if(!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        if(!picture.isEmpty()) {
            //Files.copy(picture.getInputStream(), Paths.get(uploadDir + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
            picture.transferTo(new File(uploadDir + "/" + fileName));
        }

    }
}
