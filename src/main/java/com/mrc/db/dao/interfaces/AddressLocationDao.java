package com.mrc.db.dao.interfaces;

import java.util.List;

import com.mrc.db.dto.address.*;
import com.mrc.db.dto.common.ResultData;

public interface AddressLocationDao<K,V> extends BaseDao<K,V> {
	List<LocationDong> getCurrentDong(K cond);
	ResultData addressLocation1Save(AddressLocation1 data);

	/**
	 * https://www.juso.go.kr/addrlink/addressBuildDevNew.do?menu=geodata
	 * 행정안전부 기초데이터(txt파일)를 t_address_location_base테이블에 업데이트 하고
	 * 위도 경도가 null 값을 리턴한다.
	 * -> x,y 좌표( GRS80 UTM-K)를 WGS84 좌표계로 변환해야함
	 * @param path
	 * @return
	 */
	List<AddressLocationBase> getAddressLocationBaseListAftertextFilaSave(String path) throws Exception;
}
