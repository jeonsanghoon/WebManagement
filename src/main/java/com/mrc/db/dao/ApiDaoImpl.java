/**
 * 
 */
package com.mrc.db.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author jsh
 *
 */
@Repository
public class ApiDaoImpl extends BaseDaoImpl {
	protected static final String NAMESPACE = "com.mrc.db.dao.";

	@Autowired
	private SqlSession sqlSession;

	public String selectName(){
		//return "";
		
		
		//T_MEMBER member = new T_MEMBER();
		
		
		return sqlSession.selectOne(NAMESPACE + "selectName");
	}
}

