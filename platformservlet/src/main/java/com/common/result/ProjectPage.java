package com.common.result;

import com.model.Project;
import lombok.Getter;
import org.apache.solr.common.SolrDocument;

import java.util.List;

@Getter
public class ProjectPage {
    private long total;
    private List<Project> projectList;

    public ProjectPage(long total, List<Project> projectList) {
        this.total = total;
        this.projectList = projectList;
    }
}
