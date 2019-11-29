package com.common;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileCommon {

    @Value("${application.file.save.folder}")
    private String savePath;

    public File saveFile(MultipartFile file, int projectId) throws IOException {
        File saveFolder;
        if (savePath == null || savePath.isEmpty()) {
            saveFolder = new File("file").getAbsoluteFile();
        } else {
            saveFolder = new File(savePath);
        }
        File projectFolder = new File(saveFolder, projectId + "");
        if (!projectFolder.exists()) {
            projectFolder.mkdirs();
        }
        File dest = new File(saveFolder, projectId + "/" + file.getOriginalFilename());
        file.transferTo(dest);
        return dest;
    }

}
