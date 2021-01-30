package com.mrc.db.mapper;

import com.mrc.db.dto.signage.SignageCond;
import com.mrc.db.dto.signage.SignagePlace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SignagePlaceMapper {
	void save(SignagePlace data);
	List<SignagePlace> getList(SignageCond cond);
	void delete(SignagePlace data);
}
