package com.common.result;

import com.model.Project;
import lombok.Getter;
import org.apache.solr.common.SolrDocument;

import java.util.List;

@Getter
public class SolrDocumentPage {
    private long total;
    private List<SolrDocument> data;

    public SolrDocumentPage(long total, List<SolrDocument> data) {
        this.total = total;
        this.data = data;
    }
}
