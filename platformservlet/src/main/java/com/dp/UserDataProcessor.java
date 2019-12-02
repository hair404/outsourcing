package com.dp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.result.SolrDocumentPage;
import com.dp.dto.ServerUserCenter;
import com.dp.dto.ServerUserPage;
import com.model.User;
import com.service.SearchService;
import com.service.UserService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDataProcessor {

    @Resource
    private UserService userService;

    @Resource
    private SearchService searchService;

    public Object getCenter(int userId) {
        Optional<User> op = userService.getUser(userId);
        if (!op.isPresent()) {
            return "fail";
        }
        User user = op.get();
        List<Integer> tags = new ArrayList<>();
        userService.getTags(userId).forEach(it -> tags.add(it.getTag()));
        return new ServerUserCenter(
                user.getImg(),
                user.getUsername(),
                user.getName(),
                tags,
                user.getAvatar(),
                user.getEmail(),
                user.getTel(),
                user.getInfo(),
                user.getIsValid()
        );

    }

    public ServerUserPage search(String keyword, int page, int size, int ctg) throws IOException, SolrServerException {
        SolrDocumentPage solrDocumentPage = searchService.searchStudio(keyword, page, size, ctg);
        List<JSONObject> serverUsers = new ArrayList<>();
        solrDocumentPage.getData().forEach(it -> {
            Optional<User> op = userService.getUser((Integer) it.getFieldValue("account_id"));
            if (op.isPresent()) {
                User user = op.get();
                JSONObject json = (JSONObject) JSON.toJSON(user);
                List<Integer> tags = new ArrayList<>();
                userService.getTags(user.getId()).forEach(tag -> tags.add(tag.getTag()));
                json.put("tag",tags);
                serverUsers.add(json);
            }
        });
        return new ServerUserPage(solrDocumentPage.getTotal(), serverUsers);
    }
}
