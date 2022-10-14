package com.imdb.util.file;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FileUtil {

    private final Cloudinary cloudinary;

    public void uploadImage(final MultipartFile image) {
        try {
            File uploadedFile = multipartToFile(image);
            cloudinary.uploader().upload(uploadedFile, getUploadConfig());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static File multipartToFile(final MultipartFile image) throws IllegalStateException, IOException {
        final File convFile = new File(Objects.requireNonNull(image.getOriginalFilename()));
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
