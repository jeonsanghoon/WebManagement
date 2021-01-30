package com.mrc.controller;

import com.mrc.db.dao.ApiDaoImpl;
import com.mrc.db.dao.interfaces.*;
import com.mrc.db.dto.address.*;
import com.mrc.db.dto.banner.Banner;
import com.mrc.db.dto.banner.BannerCond;
import com.mrc.db.dto.board.Board;
import com.mrc.db.dto.board.BoardCond;
import com.mrc.db.dto.board.BoardRead;
import com.mrc.db.dto.common.Common;
import com.mrc.db.dto.common.SelectItem;
import com.mrc.db.dto.common.commonCond;
import com.mrc.db.dto.file.File;
import com.mrc.db.dto.file.FileCond;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.db.dto.keyword.KeywordCond;
import com.mrc.db.dto.mediaShop.Localbox;
import com.mrc.db.dto.mediaShop.LocalboxCond;
import com.mrc.db.dto.member.*;
import com.mrc.db.dto.menu.Menu;
import com.mrc.db.dto.menu.MenuCond;
import com.mrc.db.dto.mongoDb.Log;
import com.mrc.db.dto.mongoDb.LogCond;
import com.mrc.db.dto.signage.Signage;
import com.mrc.db.dto.signage.SignageCond;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by mrc0700@gmail.com on 2020-11-24
 * Discription :
 */
public class BaseController {
    protected LoginInfo loginInfo;
    @PostConstruct
    private void initialize() {
        loginInfo = new LoginInfo();
    }
}
