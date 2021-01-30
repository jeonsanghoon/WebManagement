package com.mrc.db.dto.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressAreaExcel implements Serializable{
	private Double level;/*엑셀 숫자는 무조건 Double로 리턴됨*/
	private String code;
	private String name;
}
