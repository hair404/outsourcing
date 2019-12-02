package com.controller;

import java.io.IOException;

import com.dp.UserDataProcessor;
import com.service.SearchService;
import com.type.SearchType;
import com.type.ProjectSortType;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SearchController {
    @Resource
    private SearchService searchService;

    @Resource
    private UserDataProcessor userDataProcessor;

    @PostMapping("search")
    public Object recommend(@RequestParam("type") Integer type, @RequestParam("ctg") Integer ctg,
                            @RequestParam(value = "subctg", required = false) Integer subctg,
                            @RequestParam("keyword") String keyword,
                            @RequestParam("size") Integer size,
                            @RequestParam("page") Integer page,
                            @RequestParam(value = "sort", required = false) Integer sort,
                            @RequestParam(value = "sortrule", required = false) String sortrule)
            throws IOException, SolrServerException {
        if (SearchType.fromId(type) == SearchType.PROJECT) {
            return searchService.searchProject(ProjectSortType.fromId(sort), keyword, page, size, ctg, subctg, sortrule.equalsIgnoreCase("asc"));
        } else if (SearchType.fromId(type) == SearchType.STUDIO) {
            return userDataProcessor.search(keyword, page, size, ctg);
        }
        return "fail";
    }
}




