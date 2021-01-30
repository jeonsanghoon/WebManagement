package com.mrc.dao

import com.mrc.dao.impl.IDaoSignage
import com.mrc.dto.DtoSignage
import com.mrc.dto.DtoSignageCond



object DaoSignage : IDaoSignage<DtoSignageCond?, DtoSignage?> {
    override fun getList(cond: DtoSignageCond?): List<DtoSignage?>? {
        TODO("Not yet implemented")
        var str:String = "안녕"

        println(str)
    }
}