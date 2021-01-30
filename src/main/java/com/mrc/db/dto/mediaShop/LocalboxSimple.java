package com.mrc.db.dto.mediaShop;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kyh
 *
 * 2020.07.29
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalboxSimple implements Serializable {
	private Integer localboxCode;
	private String address;
	private String profileUrl;
	private String desc;
}
