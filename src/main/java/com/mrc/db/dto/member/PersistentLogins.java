package com.mrc.db.dto.member;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersistentLogins implements Serializable {
	private String series;
	private String userName;
	private String token;
	private Timestamp lastUsed;
	private String saveType;
}
