package com.mrc.db.dao.interfaces;

import com.mrc.db.dto.common.ResultData;

import java.util.List;

/**
 * 기본 Base Dao 인터페이스
 * @author jsh
 *
 * @param <K> 제너릭 조건
 * @param <V> 제너릭 결과
 */
public interface BaseDao<K,V> {
	/**
	 * 리스트 조회
	 * @param cond
	 * @return
	 */
	List<V> getList(K cond);
	/**
	 * 데이터조회
	 * @param cond
	 * @return
	 */
	V getData(K cond);
	/**
	 * 리스트저장
	 * @param list
	 * @return
	 */
	ResultData saveList(List<V> list);
	/**
	 * 데이터저장
	 * @param data
	 * @return
	 */
	ResultData save(V data);

}
