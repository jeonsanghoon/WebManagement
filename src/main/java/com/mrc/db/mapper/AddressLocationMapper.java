package com.mrc.db.mapper;

import com.mrc.db.dto.address.AddressLocationCond;
import com.mrc.db.dto.address.LocationDong;
import com.mrc.db.dto.address.AddressLocation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AddressLocationMapper {
	List<AddressLocation> getList(AddressLocationCond cond);
	void save(AddressLocation data);
	void delete(AddressLocation data);
	void update(AddressLocation data);
	void updateLatLongiList(List<AddressLocation> list);
	List<LocationDong> getCurrentDong(AddressLocationCond cond);
}
