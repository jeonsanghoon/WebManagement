package com.mrc.mapper

import com.mrc.dto.DtoSignage
import com.mrc.dto.DtoSignageCond
import org.apache.ibatis.annotations.Mapper

@Mapper
interface IMapperSignage {
    fun getSignageList(cond : DtoSignageCond? ):List<DtoSignage>?
}