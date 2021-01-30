package com.mrc.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mrc.db.dto.address.AddressLocation1;

@Mapper
public interface AddressLocation1Mapper {
	Integer save(AddressLocation1 data);
	List<AddressLocation1> getList(AddressLocation1 cond);
}
