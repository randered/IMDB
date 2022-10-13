package com.imdb.util.file;

import com.cloudinary.Cloudinary;
import com.imdb.base.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest extends BaseTest {

    @Autowired
    private Cloudinary cloudinary;

    @Test
    public void testUploadImage() throws IOException {
        final File uploadedFile = new File("src/test/resources/thor.jpg");
        final Map result = cloudinary.uploader().upload(uploadedFile, getUploadConfig());
        assertNotNull(result.get("public_id"));
    }

    private Map<String, String> getUploadConfig() {
        final Map<String, String> options = new HashMap<>();
        options.put("use_filename", "true");
        options.put("unique_filename", "false");
        options.put("folder", "test");
        return options;
    }
}