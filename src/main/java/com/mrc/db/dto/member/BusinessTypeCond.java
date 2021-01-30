package com.mrc.db.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: kyh
 * @Date: 2020. 4. 22.
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessTypeCond implements Serializable {
	private Integer parentCode;
	private Integer level;
}
