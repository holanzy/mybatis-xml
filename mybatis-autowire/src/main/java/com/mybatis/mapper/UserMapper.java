package com.mybatis.mapper;

import com.mybatis.model.User;

public interface UserMapper {
    User queryUser(String userName);
}
