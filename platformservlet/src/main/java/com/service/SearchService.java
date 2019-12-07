package com.service;

import com.common.result.SolrDocumentPage;
import com.dao.*;
import com.model.Advertisement;
import com.model.Project;
import com.model.User;
import com.type.AdType;
import com.type.ProjectSortType;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    @Value("${spring.data.solr.host}")
    private String solrURL;

    @Resource
    private SolrClient solrClient;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private AdvertisementRepository advertisementRepository;

    @Resource
    private TagRepository tagRepository;

    public SolrDocumentPage searchProject(ProjectSortType sortType, String keyword, int page, int size,
                                          int ctg, int subCtg, boolean asc) throws IOException, SolrServerException {
        HttpSolrClient solrServer = new HttpSolrClient.Builder(solrURL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        SolrQuery query = new SolrQuery();

        query.setQuery("*:*");
        query.setRows(size);
        query.setStart((page - 1) * size);
        query.addFilterQuery("entity:project");
        query.addFilterQuery("state:1");
        if (!keyword.isEmpty()) {
            query.addFilterQuery("prjname:" + keyword);
        }
        if (ctg != 0 && subCtg == 0) {
            query.addFilterQuery("tag:" + ctg);
        }
        if (ctg != 0 && subCtg != 0) {
            query.addFilterQuery("tag:" + ctg);
            query.addFilterQuery("subtag:" + subCtg);
        }
        switch (sortType) {
            case DEFAULT:
                List<SolrQuery.SortClause> sorts = new ArrayList<>();
                sorts.add(new SolrQuery.SortClause("weight", SolrQuery.ORDER.desc));
                sorts.add(new SolrQuery.SortClause("date", SolrQuery.ORDER.desc));
                query.setSorts(sorts);
            case PRICE:
                query.setSort("price", asc ? SolrQuery.ORDER.asc : SolrQuery.ORDER.desc);
                break;
            case PAY_IN_ADVANCED:
                query.setSort("payinadvance", asc ? SolrQuery.ORDER.asc : SolrQuery.ORDER.desc);
                break;
        }
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();

        return new SolrDocumentPage(results.getNumFound(), results);
    }

    public SolrDocumentPage searchStudio(String keyword, int page, int size,
                                         int ctg) throws IOException, SolrServerException {
        HttpSolrClient solrServer = new HttpSolrClient.Builder(solrURL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        SolrQuery query = new SolrQuery();

        query.setQuery("*:*");
        query.setRows(size);
        query.setStart((page - 1) * size);
        query.addFilterQuery("entity:studio");
        if (!keyword.isEmpty()) {
            query.addFilterQuery("username:" + keyword);
        }

        List<SolrQuery.SortClause> sorts = new ArrayList<>();
        sorts.add(new SolrQuery.SortClause("weight", SolrQuery.ORDER.desc));
        query.setSorts(sorts);

        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        List<SolrDocument> documents = new ArrayList<>();
        if (ctg == 0) {
            documents.addAll(results);
        } else {
            List<Integer> ids = new ArrayList<>();
            tagRepository.findAllByTag(ctg).forEach(it -> ids.add(it.getUserId()));
            results.forEach(it -> {
                if (ids.contains((int) it.getFieldValue("account_id"))) {
                    documents.add(it);
                }
            });
        }
        return new SolrDocumentPage(results.getNumFound(), documents);
    }

    public void deleteDocument() throws Exception {
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }

    public void initProject() throws IOException, SolrServerException {
        for (Project item : projectRepository.findAll()) {
            addDocument(item);
        }
        solrClient.commit();
    }

    public void insertProject(Project project) throws IOException, SolrServerException {
        addDocument(project);
        solrClient.commit();
    }

    private void addDocument(Project item) throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("solr_id", item.getSolr_id());
        document.addField("prjname", item.getPrjname());
        document.addField("tag", item.getTag());
        document.addField("subtag", item.getSubtag());
        document.addField("img", item.getImg());
        document.addField("companyId", item.getCompanyID());
        document.addField("studioId", item.getStudioID());
        document.addField("price", item.getPrice());
        document.addField("info", item.getInfo());
        document.addField("state", item.getState());
        document.addField("ifAd", item.getIfAd());
        document.addField("entity", item.getEntity());
        document.addField("payinadvance", item.getPayinadvance());
        document.addField("weight", weightProject(item));
        document.addField("date", item.getReleaseTime().getTime());
        solrClient.add(document);
    }

    public void initUser() throws IOException, SolrServerException {
        for (User item : userRepository.findAll()) {
            if (!item.getEntity().equals("studio")) {
                continue;
            }
            SolrInputDocument document = new SolrInputDocument();
            document.addField("solr_id", item.getSolr_id());
            document.addField("account_id", item.getAccount_id());
            document.addField("username", item.getUsername());
            document.addField("name", item.getName());
            document.addField("tel", item.getTel());
            document.addField("info", item.getInfo());
            document.addField("img", item.getImg());
            document.addField("type", item.getType().getId());
            document.addField("email", item.getEmail());
            document.addField("alipay", item.getAlipay());
            document.addField("avatar", item.getAvatar());
            document.addField("entity", item.getEntity());
            document.addField("credit", item.getCredit());
            document.addField("weight", weightStudio(item));
            solrClient.add(document);
        }
        solrClient.commit();
    }

    private int weightProject(Project project) {
        Optional<User> optionalUser = userRepository.findById(project.getCompanyID());
        if (!optionalUser.isPresent()) {
            return 0;
        }
        User user = optionalUser.get();
        Optional<Advertisement> optionalAdvertisement = advertisementRepository.findByTypeAndTypeId(AdType.PROJECT.getId(), project.getId());
        if (!optionalAdvertisement.isPresent()) {
            return user.isStudent() ? 1 : 0;
        }
        Advertisement advertisement = optionalAdvertisement.get();
        if (user.isStudent()) {
            return (int) advertisement.getPrice() * 2;
        } else {
            return (int) advertisement.getPrice();
        }
    }

    private int weightStudio(User user) {
        Optional<Advertisement> optionalAdvertisement = advertisementRepository.findByTypeAndTypeId(AdType.PROJECT.getId(), user.getId());
        if (!optionalAdvertisement.isPresent()) {
            return user.isStudent() ? 1 : 0;
        }
        Advertisement advertisement = optionalAdvertisement.get();
        if (user.isStudent()) {
            return (int) (advertisement.getPrice() * 2);
        } else {
            return (int) advertisement.getPrice();
        }
    }
}
