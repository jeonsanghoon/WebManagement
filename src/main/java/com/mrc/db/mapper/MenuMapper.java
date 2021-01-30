package com.mrc.db.mapper;

import com.mrc.db.dto.menu.MenuCond;
import com.mrc.db.dto.menu.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



@Mapper
public interface MenuMapper {
	void save(Menu data);
	List<Menu> getList(MenuCond cond);
	void delete(Menu data);
	void update(Menu data);
	
}

