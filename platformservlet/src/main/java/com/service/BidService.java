package com.service;

import com.dao.BidRepository;
import com.model.Bid;
import com.model.Project;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BidService {

    @Resource
    private BidRepository bidRepository;

    public void bid(Project project, int studioId, int quote) {
        Bid bid = new Bid();
        bid.setCompanyId(project.getCompanyID());
        bid.setProjectId(project.getId());
        bid.setState(0);
        bid.setQuote(quote);
        bid.setStudioId(studioId);
        bidRepository.save(bid);
    }
}
