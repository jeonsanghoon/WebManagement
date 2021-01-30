package com.mrc.db.mapper;

import com.mrc.db.dto.signage.SignageCond;
import com.mrc.db.dto.signage.Signage;
import com.mrc.db.dto.signage.SignageStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SignageMapper {
	void save(Signage data);
	List<Signage> getList(SignageCond cond);
	void delete(Signage data);
	void update(Signage data);
	void changeStatus(SignageStatus data);
}
