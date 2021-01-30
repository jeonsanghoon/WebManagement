package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.member.MemberCond;
import com.mrc.db.dto.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mrc0700@gmail.com on 2020-11-23
 * Discription :
 */



@SpringBootTest
@Transactional
public class MemberControllerTest {
    @Test
    void getMemberList()
    {
        MemberCond cond = MemberCond.builder().build();
        List<Member> list = GlobalDao.getMemberDao().getList(cond);
        //assertEquals()

    }


}
