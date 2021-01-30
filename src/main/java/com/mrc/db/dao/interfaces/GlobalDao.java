package com.mrc.db.dao.interfaces;

/**
 * Created by mrc0700@gmail.com on 2020-12-15
 * Discription :
 */

import com.mrc.db.dao.ApiDaoImpl;
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
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jsh
 *
 */
@Component
public class GlobalDao {

    @Getter
    private static MemberDao<MemberCond, Member, MemberList> memberDao;
    @Getter
    private static MemberCompanyDao<MemberCond, MemberCompany> memberCompanyDao;
    @Getter
    private static MemberChannelDao<MemberWithdraw> memberChannelDao;
    @Getter
    private static CommonDao<commonCond, Common, SelectItem> commonDao;
    @Getter
    private static MenuDao<MenuCond, Menu> menuDao;
    @Getter
    private static FileDao<FileCond, File> fileDao;
    @Getter
    private static ApiDaoImpl apiDao;
    @Getter
    private static BannerDao<BannerCond, Banner> bannerDao;
    @Getter
    private static AddressLocationDao<AddressLocationCond, AddressLocation> addressLocationDao;
    @Getter
    private static AddressLocationBaseDao<AddressLocationBaseCond,AddressLocationBase> addressLocationBaseDao;
    @Getter
    private static MapSquareDao<MapSquareCond, MapSquare> mapSquareDao;
    @Getter
    private static KeywordDao<KeywordCond, Keyword> keywordDao;
    @Getter
    private static BoardDao<BoardCond, Board, BoardRead> boardDao;
    @Getter
    private static MongodbDao<LogCond, Log> mongodbDao;
    @Getter
    private static PersistentLoginsDao<PersistentLogins> persistentLoginsDao;
    @Getter
    private static MediaShopDao<LocalboxCond, Localbox> mediaShopDao;
    @Getter
    private static AddressAreaDao<AddressAreaCond,AddressArea> addressAreaDao;
    @Getter
    private static MemberBookmarkDao<MemberBookmark> memberBookmarkDao;
    @Getter
    private static SignageDao<SignageCond, Signage> signageDao;

    /**
     * Dao 인터페이스 초기화
     * @param member
     * @param memberCompany
     * @param memberChannel
     * @param common
     * @param menudao
     * @param filedao
     * @param api
     * @param banner
     * @param addressLocation
     * @param addressLocationBase
     * @param mapSquare
     * @param keyword
     * @param board
     * @param mongodb
     * @param persistentlogins
     * @param mediaShop
     * @param addressArea
     * @param memberBookmark
     * @param signage
     */
    @Autowired
    private GlobalDao(MemberDao<MemberCond, Member, MemberList> member
            , MemberCompanyDao<MemberCond, MemberCompany> memberCompany
            , MemberChannelDao<MemberWithdraw> memberChannel
            , CommonDao<commonCond, Common, SelectItem> common
            , MenuDao<MenuCond, Menu> menudao
            , FileDao<FileCond, File> filedao
            , ApiDaoImpl api
            , BannerDao<BannerCond, Banner> banner
            , AddressLocationDao<AddressLocationCond, AddressLocation> addressLocation
            , AddressLocationBaseDao<AddressLocationBaseCond,AddressLocationBase> addressLocationBase
            , MapSquareDao<MapSquareCond, MapSquare> mapSquare
            , KeywordDao<KeywordCond, Keyword> keyword
            , BoardDao<BoardCond, Board, BoardRead> board
            , MongodbDao<LogCond, Log> mongodb
            , PersistentLoginsDao<PersistentLogins> persistentlogins
            , MediaShopDao<LocalboxCond, Localbox> mediaShop
            , AddressAreaDao<AddressAreaCond,AddressArea> addressArea
            , MemberBookmarkDao<MemberBookmark> memberBookmark
            , SignageDao<SignageCond, Signage> signage
    ) {
        memberDao = member;
        memberCompanyDao = memberCompany;
        memberChannelDao = memberChannel;
        commonDao = common;
        menuDao = menudao;
        fileDao = filedao;
        apiDao = api;
        bannerDao = banner;
        addressLocationDao = addressLocation;
        addressLocationBaseDao = addressLocationBase;
        mapSquareDao = mapSquare;
        keywordDao = keyword;
        boardDao = board;
        mongodbDao = mongodb;
        persistentLoginsDao = persistentlogins;
        mediaShopDao = mediaShop;
        addressAreaDao = addressArea;
        memberBookmarkDao = memberBookmark;
        signageDao = signage;
    }
}
