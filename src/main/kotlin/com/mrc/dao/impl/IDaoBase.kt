package com.mrc.dao.impl

interface IDaoBase<TCond, TData> {
    fun getList(cond: TCond): List<TData>?
}