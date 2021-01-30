package com.mrc.dao.impl


interface IDaoSignage<TCond, TData>{
    fun getList(cond: TCond): List<TData>?
}