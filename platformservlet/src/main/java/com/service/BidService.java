package com.service;

import com.dao.BidRepository;
import com.dao.ProjectRepository;
import com.model.Bid;
import com.model.Project;
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

    public void bid(Project project, int studioId, int quote) {
        Bid bid = new Bid();
        bid.setCompanyId(project.getCompanyID());
        bid.setProjectId(project.getId());
        bid.setState(BidState.WAIT);
        bid.setQuote(quote);
        bid.setStudioId(studioId);
        bidRepository.save(bid);
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
        return "success";
    }
}
