package com.mrc.dto


data class DtoSignageCond (
    var sign_code: Int? = null,
    var sign_name: String? = null,
)
{
    constructor() : this(null, "")
}