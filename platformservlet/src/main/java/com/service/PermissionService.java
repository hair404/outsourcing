package com.service;

import com.dao.ProjectRepository;
import com.model.Project;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class PermissionService {

    @Resource
    private ProjectRepository projectRepository;

    public boolean checkCompanyProject(int companyId, int projectId) {
        Optional<Project> op = projectRepository.findById(projectId);
        return op.filter(project -> project.getCompanyID() == companyId).isPresent();
    }
}
