package com.dp.dto;

import com.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class ServerUserCenter {

    private String img;
    private String username;
    private String name;
    private List<Integer> tag;
    private String avatar;
    private String email;
    private String tel;
    private String info;
    private int isValid;

    public ServerUserCenter(String img, String username, String name, List<Integer> tag, String avatar, String email, String tel, String info, int isValid) {
        this.img = img;
        this.username = username;
        this.name = name;
        this.tag = tag;
        this.avatar = avatar;
        this.email = email;
        this.tel = tel;
        this.info = info;
        this.isValid = isValid;
    }

}
