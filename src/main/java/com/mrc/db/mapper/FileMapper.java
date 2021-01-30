package com.mrc.db.mapper;

import com.mrc.db.dto.file.FileCond;
import com.mrc.db.dto.file.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



@Mapper
public interface FileMapper {
	List<File> getList(FileCond cond);
	void save(File data);
	void delete(File data);
	void update(File data);
}
