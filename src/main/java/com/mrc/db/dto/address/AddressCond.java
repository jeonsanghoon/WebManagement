package com.mrc.db.dto.address;

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
public class AddressCond implements Serializable {
	private Double latitude;
	private Double longitude;
	private String sidoName;
	private String sigunguName;
	private String dong2Name;
	private String address;
}
   