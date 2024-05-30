package org.muztache.api.util;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class MultipartFileUtils {

    public static File asFile(@NotNull MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        File file = File.createTempFile(
                FilenameUtils.getBaseName(originalFilename),
                FilenameUtils.getExtension(originalFilename)
        );
        multipartFile.transferTo(file);
        return file;
    }
}
