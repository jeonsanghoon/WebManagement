package com.mrc.db.dao;

import com.mrc.db.dto.member.LoginInfo;

import javax.annotation.PostConstruct;

/**
 * Created by mrc0700@gmail.com on 2020-11-24
 * Discription :
 */
public class BaseDaoImpl {
    protected LoginInfo loginInfo;
    @PostConstruct
    private void initialize() {
        loginInfo = new LoginInfo();
    }
}
