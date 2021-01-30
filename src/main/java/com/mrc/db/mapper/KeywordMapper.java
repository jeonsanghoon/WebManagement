package com.mrc.db.mapper;

import com.mrc.db.dto.keyword.KeywordCond;
import com.mrc.db.dto.keyword.Keyword;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * @Author: kyh
 * @Date: 2020. 4. 2.
 */
@Mapper
public interface KeywordMapper {
	List<Keyword> getList(KeywordCond cond);
	List<Keyword> getAutoList(KeywordCond cond);
	void save(Keyword data);
}
