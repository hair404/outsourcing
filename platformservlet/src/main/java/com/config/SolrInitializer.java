package com.config;

import com.service.SearchService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Order(1)
public class SolrInitializer implements ApplicationRunner {

    @Resource
    private SearchService searchService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        searchService.deleteDocument();
        searchService.initProject();
        searchService.initUser();
    }
}
