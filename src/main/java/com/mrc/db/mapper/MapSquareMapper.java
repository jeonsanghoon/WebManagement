package com.mrc.db.mapper;

import com.mrc.db.dto.address.MapSquareCond;
import com.mrc.db.dto.address.MapSquare;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapSquareMapper {
	List<MapSquare> getList(MapSquareCond cond);
	void save(MapSquare data);
	void delete(MapSquare data);
	void update(MapSquare data);
}
