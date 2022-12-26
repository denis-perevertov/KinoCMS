package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {

    @Autowired
    private static HttpServletRequest request;

    public static void saveFile(String uploadDir, String fileName, MultipartFile picture) throws IOException, URISyntaxException {

        File uploadDirectory = new File(uploadDir);

        if(!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        if(!picture.isEmpty()) {
            Files.copy(picture.getInputStream(), Paths.get(uploadDir + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        }


//        if(picture != null) {
//            File targetFile = new File(uploadDir + "/" + fileName);
//
//            if(targetFile.exists()) {
//                targetFile.delete();
//                picture.transferTo(targetFile);
//            } else picture.transferTo(targetFile);
//
//        }
//        URI uri = new URI("file://"+uploadDir);
//        Path uploadPath = Paths.get(uri);
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        Path filePath = uploadPath.resolve(fileName);
//
//        try (InputStream inputStream = picture.getInputStream()) {
//            if(Files.exists(filePath)) {
//                Files.delete(filePath);
//                Files.copy(inputStream, filePath);
//            } else {
//                Files.copy(inputStream, filePath);
//            }
//        } catch (IOException ioe) {
//        }

    }
}
