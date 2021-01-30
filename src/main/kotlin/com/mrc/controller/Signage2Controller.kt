package com.mrc.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class Signage2Controller {
    @GetMapping("/member/signageelist")
    fun signage2List():String{
      //
     //  Global2Dao().DaoSignage.getList(signCond)
      //  var signCond = DtoSignageCond(sign_code = 1)
        //var rtn = Global2Dao().daoSignage.getList(signCond)
      // var cond = DtoSignageCond(null,null)
       // var rtn = Global2Dao.DaoSignage.getList(ConditionalOperators.cond)


        return "test"
    }
}