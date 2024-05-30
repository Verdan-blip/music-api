package org.muztache.api.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CloudinaryService {

    public enum UploadType {
        VIDEO, AUDIO, COVER, SMALL_COVER, AVATAR
    }

    private final Cloudinary cloudinary;

    public String upload(File file, UploadType uploadType) throws IOException {
        final Map<String, String> params = new HashMap<>();

        switch (uploadType) {
            case VIDEO -> {
                params.put("folder", "videos");
                params.put("resource_type", "video");
            }
            case COVER -> {
                params.put("folder", "small_covers");
            }
            case SMALL_COVER -> {
                params.put("folder", "covers");
            }
            case AUDIO -> {
                params.put("folder", "audio");
                params.put("resource_type", "video");
            }
            case AVATAR -> {
                params.put("folder", "avatars");
            }
        }

        Map result = cloudinary.uploader().upload(file, params);
        return (String) result.get("url");
    }

}
