package com.mrc.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
object Global2Mapper  {
    @Autowired
    lateinit var Signage: IMapperSignage
}