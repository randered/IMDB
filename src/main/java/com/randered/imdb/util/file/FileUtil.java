package com.randered.imdb.util.file;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FileUtil {


    private final Cloudinary cloudinary;

    public String uploadFile(final MultipartFile image) {
        try {
            File uploadedFile = multipartToFile(image);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, getUploadConfig());
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadFile(final File file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file, getUploadConfig());
            uploadResult.get("url");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static File multipartToFile(final MultipartFile image) throws IllegalStateException, IOException {
        File convFile = new File(image.getOriginalFilename());
        image.transferTo(convFile);
        return convFile;
    }

    private Map<String, String> getUploadConfig() {
        Map<String, String> options = new HashMap<>();
        options.put("use_filename", "true");
        options.put("unique_filename", "false");
        options.put("folder", "imdb");
        return options;
    }
}
