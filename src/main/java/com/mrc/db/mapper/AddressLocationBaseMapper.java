package com.mrc.db.mapper;

import com.mrc.db.dto.address.AddressLocationBase;
import com.mrc.db.dto.address.AddressLocationBaseCond;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by mrc0700@gmail.com on 2021-01-13
 * Discription :
 */
@Mapper
public interface AddressLocationBaseMapper {
    List<AddressLocationBase> getList(AddressLocationBaseCond cond);
    void save(AddressLocationBase data);
    void saveList(List<AddressLocationBase> list);
    void delete(AddressLocationBase data);
    void update(AddressLocationBase data);
    void updateList(List<AddressLocationBase> data);


    /**
     * tmp_origin_address_txtfile 테이블 삭제 후  초기화
     */
    void deleteTmpOriginAddressTxtfile();

    /**
     * txt 파일 가져와서 tmp_origin_address_txtfile 테이블에 추가
     * @param path
     */
    void addTmpOrignAddressTxtfileByTextfile(String path);

    /**
     * t_address_location_base 테이블 동기화 후 위도 경도 없는 데이터 리턴
     * @return
     */
    void addAddressLocationBaseByTmpOrignAddressTxtfile();

    /**
     * t_Address_Location_Base 테이블을 기반으로
     * t_address_area, t_address_location1, t_address_location2
     *
     */
    void autoAddAddressAreaByAddressLocationBase();

}
