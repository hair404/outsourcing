package com.common;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileCommon {

    @Value("${application.file.save.folder}")
    private String savePath;

    public File saveFile(MultipartFile file, int projectId, int stepId) throws IOException {
        File saveFolder;
        if (savePath == null || savePath.isEmpty()) {
            saveFolder = new File("file").getAbsoluteFile();
        } else {
            saveFolder = new File(savePath);
        }
        File stepProject = new File(saveFolder, projectId + "/" + stepId);
        if (!stepProject.exists()) {
            stepProject.mkdirs();
        }
        File dest = new File(stepProject, Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(dest);
        return dest;
    }

    public File getFile(int projectId, int stepId, String filename) {
        File saveFolder;
        if ((savePath == null) || savePath.isEmpty()) {
            saveFolder = new File("file").getAbsoluteFile();
        } else {
            saveFolder = new File(savePath);
        }
        File projectFolder = new File(saveFolder, projectId + "");
        return new File(projectFolder, stepId + "/" + filename);
    }

}
