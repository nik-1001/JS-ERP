package com.admin.jsservice.response;

import com.admin.jsservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewResponse {
    private List<User> registeredUser;


}