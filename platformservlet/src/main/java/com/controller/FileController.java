package com.controller;

import java.io.File;
import java.nio.file.Paths;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.FileCommon;
import com.utils.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.File_projectRepository;
import com.service.ProjectService;

@RestController
public class FileController {
    @Resource
    private FileCommon fileCommon;


    @GetMapping(value = "/file/{project_id:.+}/{step_id:.+}/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_user_img(@PathVariable String project_id, @PathVariable String step_id, @PathVariable String filename) {
        try {
            File file = fileCommon.getFile(Integer.parseInt(project_id), Integer.parseInt(step_id), filename);
            if (!file.exists()) {
                throw new Exception();
            }
            return ResponseEntity.ok(FileTools.getBytes(file));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
