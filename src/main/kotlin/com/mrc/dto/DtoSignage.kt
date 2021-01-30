package com.mrc.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.sql.Timestamp


data class DtoSignage (
    var sign_code: Int? = null,
    var sign_name: String? = null,
    /**
     * 사이니지유형(t_common : S002 1:내부배너 2:외부배너)
     */
    var sign_type: Int? = null,
    var sign_type_name: String? = null,

    /**
     * 화면분할(D002 1:1Frame 6:6Frame)
     */
    var frame_type: Int? = null,
    var frame_type_name: String? = null,

    /**
     * 세로설치여부(s004 : 1:세로 0:가로)
     */
    var is_vertical: Boolean? = null,
    var is_vertical_name: String? = null,
    var member_code: Int? = null,
    var remark: String? = null,
    var hide :Boolean= false,
    var insert_code: Int? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var insert_date: Timestamp? = null,
    var update_code: Int? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var update_date: Timestamp? = null,
    var insert_name: String? = null,
    var update_name: String? = null,
){
    constructor() : this(null, "")
}