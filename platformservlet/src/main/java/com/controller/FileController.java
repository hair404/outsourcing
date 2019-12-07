package com.controller;

import java.io.File;

import javax.annotation.Resource;

import com.common.FileCommon;
import com.utils.FileTools;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
