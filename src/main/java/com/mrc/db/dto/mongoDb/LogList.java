package com.mrc.db.dto.mongoDb;

import java.io.Serializable;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LogList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Log> list;
	private long rowCount;
}
