package com.common;

import com.type.PictureType;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PictureCommon {

    public static File saveImage(MultipartFile file, PictureType type) throws IOException {
        File dest = new File(type.getSaveFolder(), UUID.randomUUID() + ".jpg");
        Thumbnails.of(file.getInputStream()).outputFormat("jpeg").scale(1.0f).toFile(dest);
        return dest;
    }


}
