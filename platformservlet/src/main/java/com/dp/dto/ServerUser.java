package com.dp.dto;

import com.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServerUser extends User {

    private List<Integer> tag;
}
