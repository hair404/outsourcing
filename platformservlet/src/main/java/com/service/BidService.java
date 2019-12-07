package com.service;

import com.dao.BidRepository;
import com.dao.ProjectRepository;
import com.model.Bid;
import com.model.Project;
import com.type.ActionType;
import com.type.BidState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    @Resource
    private BidRepository bidRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private NotificationService notificationService;

    public String bid(int projectId, int studioId, int quote) {

        Optional<Project> op = projectRepository.findById(projectId);
        if (!op.isPresent()) {
            return "NotFound";
        }

        Project project = op.get();

        if (project.getState() != 1) {
            return "fail";
        }

        Optional<Bid> optionalBid = bidRepository.getByProjectIdAndStudioId(projectId, studioId);

        if (optionalBid.isPresent()) {
            return "fail";
        }

        Bid bid = new Bid();
        bid.setCompanyId(project.getCompanyID());
        bid.setProjectId(project.getId());
        bid.setState(BidState.WAIT);
        bid.setQuote(quote);
        bid.setStudioId(studioId);
        bidRepository.save(bid);
        return "success";
    }

    public String pick(int projectId, int studioId) {
        Optional<Bid> optionalBid = bidRepository.getByProjectIdAndStudioId(projectId, studioId);
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalBid.isPresent()) {
            return "NotFoundBid";
        }
        if (!optionalProject.isPresent()) {
            return "NotFoundProject";
        }

        Bid bid = optionalBid.get();
        Project project = optionalProject.get();

        bid.setState(BidState.WIN);
        bidRepository.save(bid);
        List<Bid> bids = bidRepository.findAllByProjectId(project.getId());
        bids.forEach(it -> {
            if (it.getStudioId() != studioId) {
                it.setState(BidState.REJECT);
            }
        });
        bidRepository.saveAll(bids);

        project.setStudioID(studioId);
        project.setPrice((float) bid.getQuote());
        project.setState(2);
        projectRepository.save(project);

        notificationService.notify(studioId, "您中标了", "您投标的一个项目成功中标，点击查看详情", ActionType.JUMP_PROJECT, ActionType.generateJumpProjectParams(project.getSolr_id()));

        return "success";
    }
}
