package com.mrc.db.mapper;

import java.util.List;

import com.mrc.db.dto.address.AddressAreaCond;
import org.apache.ibatis.annotations.Mapper;

import com.mrc.db.dto.address.AddressCond;
import com.mrc.db.dto.address.LocationDong;
import com.mrc.db.dto.address.AddressArea;

@Mapper
public interface AddressAreaMapper {

	List<AddressArea> getList(AddressAreaCond cond);
	void save(AddressArea data);
	void delete(AddressArea data);
	void update(AddressArea data);
	List<LocationDong> getCodeByAddress(AddressCond cond);
} 
