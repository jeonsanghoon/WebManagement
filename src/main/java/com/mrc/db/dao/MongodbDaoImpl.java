package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.MongodbDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.mongoDb.AdPlayLogMongo;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.mongoDb.*;
import com.mrc.framework.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MongodbDaoImpl extends BaseDaoImpl implements MongodbDao<LogCond, Log> {

	@Autowired
	private MongoTemplate mongoTemplate;
	

	public List<AdPlayLogMongo> getAdLog() {
		Query query = new Query().addCriteria(Criteria.where("DEVICE_NAME").is(""));
		List<AdPlayLogMongo> rtn = mongoTemplate.find(query, AdPlayLogMongo.class);

		return rtn;
	}
	
	
	private Query setLogCond(LogCond cond)
	{
		Query query = new Query();
		
		if (cond.getMemberCode() != null)
			query =query.addCriteria(Criteria.where("member_code").in(cond.getMemberCode()));
		if (cond.getMemberName() != null)
			query = query.addCriteria(Criteria.where("member_name").in(cond.getMemberName()));
		//if (!Global.DataInfo.isNullEmpty(cond.getFr_date()))
		//	query = query.addCriteria(Criteria.where("fr_date").is(cond.getFr_date()));
		//if (!Global.DataInfo.isNullEmpty(cond.getTo_date()))
		//	query = query.addCriteria(Criteria.where("reg_date").(cond.getTo_date()));
		if (!Global.getDataInfo().isNullEmpty(cond.getDeviceType()))
			query = query.addCriteria(Criteria.where("device_type").in(cond.getDeviceType()));
		
		if (!Global.getDataInfo().isNullEmpty(cond.getSearchText()))
		{
			String searchText = cond.getSearchText();
			query = query.addCriteria(new Criteria()
										.orOperator(Criteria.where("member_code").in(searchText))
										.orOperator(Criteria.where("member_name").in(searchText))
										.orOperator(Criteria.where("page_url").in(searchText))
										.orOperator(Criteria.where("page_name").in(searchText))
										.orOperator(Criteria.where("device_type").in(searchText))
										.orOperator(Criteria.where("reg_date").in(searchText))
					
					);
		}
		return query;
	}
	
	@Override
	public long getLogCount(LogCond cond) {
		// TODO Auto-generated method stub
		Query query = this.setLogCond(cond);
		return mongoTemplate.count(query, Log.class);
	}
	
	@Override
	public List<Log> getList(LogCond cond) {
		// TODO Auto-generated method stub
		//Criteria param = Criteria.where("1").is("1");
		Query query = this.setLogCond(cond);
		int nStart = (cond.getPage() -1)* cond.getPageSize() ;
		int nLimit = cond.getPageSize();
		
		Sort sort = Sort.by(Sort.Direction.DESC, cond.getSort());
		query.with(sort);
		query = query.skip(nStart).limit(nLimit);

		List<Log> list = mongoTemplate.find(query, Log.class);
		return list;
	}

	@Override
	public Log getData(LogCond cond) {
		// TODO Auto-generated method stub
		return this.getList(cond).get(0);
	}

	@Override
	public ResultData save(Log data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			if (loginInfo.getIsLogin() == true) {
				data.setMemberCode(loginInfo.getMember().getMemberCode());
				data.setMemberName(loginInfo.getMember().getMemberName());
			} else
				data.setMemberCode(1);
			data.setRegDate(new Date());
			mongoTemplate.insert(data);
		} catch (Exception ex) {
			rtn.setMessage(ex.getMessage());
		}
		return rtn;
	}

	@Override
	public ResultData saveList(List<Log> list) {
		// TODO Auto-generated method stub
		return null;
	}
	public ResultData delete(Log data) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResultData logAdPlaceSave(List<LogAdPlace> list) {
		// TODO Auto-generated method stub
		
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			long timeInMillis =System.currentTimeMillis();
			
			//if(!loginInfo.isAdmin())
			//{
				Boolean bInsert = false;
				for(int i=0; i<list.size(); i++)
				{
					if(list.get(i).getIdx() == null || list.get(i).getIdx() <= 0)
					{
						bInsert = true;
						list.get(i).setIdx(timeInMillis);
						list.get(i).setIdx_sub(i);
						if(loginInfo.getIsLogin())
						{
							list.get(i).setMemberCode(loginInfo.getMember().getMemberCode());
							list.get(i).setMemberName(loginInfo.getMember().getMemberName());
						}
						else {
							list.get(i).setMemberCode(-1);
						}
						list.get(i).setRegDate(new Date());
					}
				}
				if(bInsert)
					mongoTemplate.insertAll(list);
				else {
					Query query = new Query();
				 	query.addCriteria(Criteria.where("idx").is(list.get(0).getIdx()));
				 	Update update = new Update();
			        update.set("reg_date", new Date());
			        update.set("idx", timeInMillis);
			        mongoTemplate.updateMulti(query, update, LogAdPlace.class);
				}
			//}
		} catch (Exception ex) {
			rtn.setMessage(ex.getMessage());
		}
		return rtn;
	}


	@Override
	public List<LogAdPlace> getLogAdPlaceList(LogAdPlaceCond cond) {
		// TODO Auto-generated method stub
		
		if(!loginInfo.isAdmin() )
		{
			if( loginInfo.getIsLogin()) cond.setMemberCode(loginInfo.getMember().getMemberCode());
			else  cond.setMemberCode(-1);
		}else {
			cond.setMemberCode( cond.getMemberCode() == null ? loginInfo.getMember().getMemberCode() : cond.getMemberCode());
		}
		
		Query query = new Query();
		
		if (cond.getMemberCode() != null)
			query =query.addCriteria(Criteria.where("member_code").exists(true).in(cond.getMemberCode()));
	
		
		Sort sort = Sort.by(Sort.Direction.DESC, cond.getSort());
		query.with(sort);
		int nStart = (int)((cond.getPage() -1)* cond.getPageSize());
		query = query.skip(nStart).limit(cond.getPageSize());
		
		List<LogAdPlace> list = mongoTemplate.find(query, LogAdPlace.class);
		return list;
	}


	@Override
	public ResultData logKeywordSave(LogKeyword keyword) {
		// TODO Auto-generated method stub
		
	
		
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			List<LogKeyword> list = new ArrayList<LogKeyword>();
			list.add(keyword);
			mongoTemplate.insertAll(list);
			
		}catch(Exception ex) {
			rtn.setResultType(ResultType.Error);
			rtn.setMessage(ex.getLocalizedMessage());
		}
		return rtn;
	}
}
