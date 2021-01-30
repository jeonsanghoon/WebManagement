package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.AddressAreaDao;
import com.mrc.db.dto.address.AddressAreaCond;
import com.mrc.db.dto.address.AddressCond;
import com.mrc.db.dto.address.LocationDong;
import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.common.KakaomapCond;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.framework.Global;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressAreaDaoImpl extends BaseDaoImpl implements AddressAreaDao<AddressAreaCond, AddressArea> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<AddressArea> getList(AddressAreaCond cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getAddressAreaMapper().getList(cond);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public AddressArea getData(AddressAreaCond cond) {
		// TODO Auto-generated method stub
		 List<AddressArea> list = GlobalMapper.getAddressAreaMapper().getList(cond);
		 if(list.size() == 1) return list.get(0);
		return null;
	}
	
	@Transactional
	@Override
	public ResultData saveList(List<AddressArea> list) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			for (AddressArea data : list) GlobalMapper.getAddressAreaMapper().save(data);
		}catch(Exception ex){
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
	
	@Transactional
	@Override
	public ResultData save(AddressArea data) {


		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getAddressAreaMapper().save(data);
		}catch(Exception ex){
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
	@Transactional
	@Override
	public ResultData update(AddressArea data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		
		try {
			GlobalMapper.getAddressAreaMapper().update(data);
		}catch(Exception ex)
		{
			data.setCode("");
			rtn.setResultType(ResultType.Error);
			rtn.setMessage(ex.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		// TODO Auto-generated method stub
		return rtn;
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<LocationDong> getCodeByAddress(AddressCond cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getAddressAreaMapper().getCodeByAddress(cond);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<LocationDong> getAddressAreaBylocation(KakaomapCond cond)
	{
		List<LocationDong> rtn = new ArrayList<LocationDong>();
		ResponseEntity<String> response = Global.getKakaoApi().call(URI.create("https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=" + cond.getLongi() + "&y=" + cond.getLat()));
		JSONParser jsonParser = new JSONParser(); 
		
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody().toString());
			JSONArray docuArray = (JSONArray) jsonObject.get("documents");
			
			if(docuArray.size() > 0)
			{
				JSONObject docuObject = (JSONObject) docuArray.get(docuArray.size()-1);
				AddressCond Cond2 = AddressCond.builder()
									.sidoName(docuObject.get("region_1depth_name").toString())
									.sigunguName(docuObject.get("region_2depth_name").toString())
									.dong2Name(docuObject.get("region_3depth_name").toString()).build();
				rtn = GlobalMapper.getAddressAreaMapper().getCodeByAddress(Cond2);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rtn;
	}
}
