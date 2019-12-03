package com.service;

import com.dao.ProjectRepository;
import com.dao.RefundRepository;
import com.dao.UserRepository;
import com.model.Project;
import com.model.Refund;
import com.model.User;
import com.type.RefundType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class RefundService {

    @Resource
    private RefundRepository refundRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private UserRepository userRepository;

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

        Refund refund = new Refund();
        refund.setFromid(fromId);
        refund.setToid(toId);
        refund.setPrjid(projectId);
        refund.setReason(reason);
        refund.setMoney(rate);
        refund.setTo(to.getUsername());
        refund.setFrom(from.getUsername());
        if (project.getState() == 7) {
            refund.setType(RefundType.CANCELED.getId());
        } else {
            refund.setType(RefundType.RUNNING.getId());
        }
        refund.setName(project.getPrjname());
        refund.setReason(reason);
        refundRepository.save(refund);

        project.setState(8);
        projectRepository.save(project);

        return "success";
    }
}
