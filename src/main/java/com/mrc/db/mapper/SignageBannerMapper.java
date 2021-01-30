package com.mrc.db.mapper;


import com.mrc.db.dto.signage.SignageBanner;
import com.mrc.db.dto.signage.SignageBannerList;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Created by mrc0700@gmail.com on 2020-12-22
 * Discription :
 */
@Mapper
public interface SignageBannerMapper  {

    void saveList(List<SignageBanner> data);
    List<SignageBannerList> getList(SignageBanner cond);
    void deleteList(List<SignageBanner> list);
    void updateList(List<SignageBanner> list);
    void arrangeAfterSave(SignageBanner data);
}
