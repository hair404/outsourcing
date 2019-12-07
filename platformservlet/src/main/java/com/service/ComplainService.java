package com.service;

import com.dao.AdminRepository;
import com.dao.ProjectRepository;
import com.dao.ComplainRepository;
import com.dao.UserRepository;
import com.model.Admin;
import com.model.Complain;
import com.model.Project;
import com.model.User;
import com.type.RefundType;
import com.type.UserType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ComplainService {

    @Resource
    private ComplainRepository complainRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private AdminRepository adminRepository;

    public String add(int fromId, int toId, String reason, int projectId, float rate) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Optional<User> optionalFrom = userRepository.findById(fromId);
        Optional<User> optionalTo = userRepository.findById(toId);

        if (!optionalProject.isPresent()) {
            return "NotFound";
        }
        if (!optionalFrom.isPresent()) {
            return "NotFound";
        }
        if (!optionalTo.isPresent()) {
            return "NotFound";
        }

        Project project = optionalProject.get();
        User from = optionalFrom.get();
        User to = optionalTo.get();

        Complain complain = new Complain();
        complain.setFromid(fromId);
        complain.setToid(toId);
        complain.setPrjid(projectId);
        complain.setReason(reason);
        complain.setMoney(rate);
        complain.setTo(to.getUsername());
        complain.setFrom(from.getUsername());
        if (project.getState() == 7) {
            complain.setType(RefundType.CANCELED.getId());
        } else {
            complain.setType(RefundType.RUNNING.getId());
        }
        complain.setName(project.getPrjname());
        complain.setReason(reason);
        int freeExpert = findFreeExpert();
        if (freeExpert == -1) {
            return "NoExpert";
        }
        complain.setExpert(freeExpert);
        complainRepository.save(complain);

        project.setState(8);
        projectRepository.save(project);

        return "success";
    }

    private int findFreeExpert() {
        List<Admin> experts = adminRepository.findAllByType(UserType.EXPERT.getId());
        if (experts.size() == 0) {
            return -1;
        }
        int minExpert = experts.get(0).getId();
        int min = complainRepository.countByExpert(minExpert);
        for (Admin it : experts) {
            int cnt = complainRepository.countByExpert(it.getId());
            if (min > cnt) {
                min = cnt;
                minExpert = it.getId();
            }
        }
        return minExpert;
    }

    public List<Complain> getComplainByExpert(int expert) {
        return complainRepository.findAllByExpert(expert);
    }
}
