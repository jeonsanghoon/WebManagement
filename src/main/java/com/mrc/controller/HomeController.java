package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.address.AddressAreaCond;
import com.mrc.db.dto.address.AddressCond;
import com.mrc.db.dto.address.LocationDong;
import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.common.KoreanUnit;
import com.mrc.db.dto.common.MailData;
import com.mrc.db.dto.common.MailParam;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.framework.Global;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author jsh
 *
 */
@Controller
public class HomeController {
	// @RequestMapping(value={"", "/index", "/error"z, "/*"}, method =
	// RequestMethod.GET)


	@RequestMapping(value = { "/", "index", "/index", "/error", "/page/error", "/*" }, method = RequestMethod.GET)
	public String index()  {
		//List<t_address_location> list=	GlobalDao.AddressLocationDao.GetList(new address_location_cond());
		/*
		 * MailData mail =
		 * MailData.builder().fromMail("jsh0147@naver.com").toMail("mrc0700@gmail.com").
		 * title("테스트메일 입니다. 확인차 보냅니다.").w
		 * message("ssl로 변경 테스트 안녕하세요 메일테스트를 하고 있어요 호가인 부탁드립니다. 메일 확인후 연락 주세요.").build()
		 * ; Global.MailInfo.sendMail(mail);
		 */
		// List<T_AD_PLAY_LOG_MONGO> rtn = GlobalDao.MongodbDao.getAdLog();
		// Proj4
		// proj4.defs["EPSG:5179"] 〓 "+proj〓tmerc +lat_0〓38 +lon_0〓127.5 +k〓0.9996
		// +x_0〓1000000 +y_0〓2000000 +ellps〓GRS80 +units〓m +no_defs";//제공되는 좌표

		// proj4.defs["EPSG:5179"] 〓 "+proj〓tmerc +lat_0〓38 +lon_0〓127.5 +k〓0.9996
		// +x_0〓1000000 +y_0〓2000000 +ellps〓GRS80 +units〓m +no_defs";//제공되는 좌표
		//ProjCoordinate rtn = Global.MapInfo.utmkToWgs84(955790.401089, 1952841.038552);
		
		//ProjCoordinate rtn2 = Global.MapInfo.wgs84ToUtmk(127.000, 37.000);

		//메일 테스트
		//this.emailTest() 
		
		//
		//this.addressAddessKeywordSave();
		//this.keywordAddressSynonymAdd();
		//KoreanUnit keyword = Global.DataInfo.convertKoreanUnit("흥천면");
		//KoreanUnit keyword2 = Global.DataInfo.convertKoreanUnit2("흥천면");
	
		//List<location_dong> list =	GlobalDao.getAddressAreaDao().getAddressAreaBylocation(KakaomapCond.builder().lat(37.511882).longi(127.044367).build());
		
		//setAddressAreaLatLong(); 
		
		ArrayList<int[]> testList2 = new ArrayList<>(Arrays.asList(
                new int[][]{ {1,2}, {3,4} }
        ));
		return "index";
	}
	
	@SuppressWarnings("unused")
	private void setAddressAreaLatLong() throws UnsupportedEncodingException 
	{
		List<LocationDong> list = GlobalDao.getAddressAreaDao().getCodeByAddress(AddressCond.builder().build());
		
		for(LocationDong data : list) {
			List<AddressArea> rtnList = GlobalDao.getAddressAreaDao().getList(AddressAreaCond.builder().code(data.getCode()).build());
			if(rtnList.size()>0)
			{
				AddressArea param = rtnList.get(0);
				
				String address = data.getSidoName().concat(" ").concat(data.getSigunguName()).concat(" ").concat(data.getDong2Name());
				address = URLEncoder.encode(address, "UTF-8");
				ResponseEntity<String> response = Global.getKakaoApi().call(URI.create("https://dapi.kakao.com/v2/local/search/address.json?query=" + address)) ;
				JSONParser jsonParser = new JSONParser(); 
				
				try {
					JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody().toString());
					JSONArray docuArray = (JSONArray) jsonObject.get("documents");
					if(docuArray.size() > 0)
					{
						JSONObject docuObject = (JSONObject) docuArray.get(0);
						docuObject = (JSONObject)docuObject.get("address");
						param.setLatitude(Double.parseDouble(docuObject.get("y").toString()));
						param.setLongitude(Double.parseDouble(docuObject.get("x").toString()));
						
						GlobalDao.getAddressAreaDao().update(param);
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			//t_address_area param = t_address_area.builder().level(3).code(data.getCode()).keyword_code(data.).build();
			//GlobalDao.getAddressAreaDao().update(param)
		}
				
	}
	@SuppressWarnings("unused")
	private void emailTest()
	
	{/*
		
		MailData mail = MailData.builder()
				  			.fromMail("webservice@altsoft.kr")
				  			.fromMailName("온닫이고객센터")
				  			.toMail("mrc0700@gmail.com")
		  					.title("테스트메일 첫번째입니다. 확인차 보냅니다.")
		  					.templeateName("/templeate/mail-template2.html")
		  					.build();
		  
		  MailParam mailParam = MailParam.builder()
				  						.data("jeo***@altsoft.kr")
				  						.data1("2020-07-22(수) 11:54")
				  						.data2("일반로그인")
				  						.data3("Window 10")
				  						.data4("Chrome")
				  						.data5("106.246.255.130(대한민국)")
				  						.build();
		   
		  Global.MailInfo.sendMail(mail,mailParam);
			 */
		  
		
		MailData mail = MailData.builder()
				      .fromMail("jsh0147@naver.com")
					  .toMail("mrc0700@gmail.com,jeon.sh@altsoft.kr")
					  .toMailName("받는사람1,받는사람2")
					  .ccMail("mrc0700@kakao.com,jeon.sh@altsoft.kr")
					  .ccMailName("참조1,참조2")
					  .title("테스트메일 두번째 입니다. 확인차 보냅니다.")
					  .templeateName("/templeate/mail-template2.html")
					  .build();

		  MailParam mailParam = MailParam.builder()
				        .data("jeo***@altsoft.kr")
				        .data1("2020-07-22(수) 11:54")
				        .data2("일반로그인")
				        .data3("Window 10")
				        .data4("Chrome")
				        .data5("106.246.255.130(대한민국)")
				        .build();

		  Global.getMailInfo().sendMail(mail,mailParam);
	   
		
	}


	private void keywordAddressSynonymAdd()
	{
		AddressArea cond = AddressArea.builder().build();
		List<AddressArea> list = GlobalDao.getAddressAreaDao().getList(AddressAreaCond.builder().build());
			
		for(AddressArea data : list)
		{
			
			String keywordName  = data.getName();
			Integer len = keywordName.length();
		
			if(len <= 2) continue;
			
			if(data.getLevel()> 1)
			{
				keywordName = keywordName.substring(0, keywordName.length()-1);
				KoreanUnit keyword = Global.getDataInfo().convertKoreanUnit(keywordName);
				Keyword param = Keyword.builder()
						.keywordType(Short.parseShort("2"))
						.keywordName(keywordName).chosung(keyword.getChoSung())
						.jungsung(keyword.getJungSung()).jongsung(keyword.getJongSung())
						.keywordUnits(keyword.getKeywordUnit())
						.isSynonym(true)
						.baseCode(data.getKeywordCode())
						.insertCode(1).build();

						GlobalDao.getKeywordDao().save(param);
			}
			else {
				
				switch(data.getName())
				{
						
					case "경상남도":
						keywordName  = "경남";
					case "경상북도":
						keywordName  = "경북";
					case "전라남도":
						keywordName  = "전남";
					case "전라북도":
						keywordName  = "전북";
					case "충청남도":
						keywordName  = "충남";
					case "충청북도":
						keywordName  = "층븍";
				
					default :
						keywordName  = keywordName.substring(0, 2);
					break;
				}
				
				KoreanUnit keyword = Global.getDataInfo().convertKoreanUnit(keywordName);
				Keyword keywordparam = Keyword.builder()
						.keywordType(Short.parseShort("2"))
						.keywordName(keywordName).chosung(keyword.getChoSung())
						.jungsung(keyword.getJungSung()).jongsung(keyword.getJongSung())
						.keywordUnits(keyword.getKeywordUnit())
						.isSynonym(true)
						.baseCode(data.getKeywordCode())
						.insertCode(1).build()
						
						;

				GlobalDao.getKeywordDao().save(keywordparam);
				
				if(!(data.getName().contains("경상남도") || data.getName().contains("전라남도")|| data.getName().contains("충청남도")))
				{
					keywordName  = keywordName.substring(0, 2).concat(data.getName().contains("도") ? "도" : "시");
					keyword = Global.getDataInfo().convertKoreanUnit(keywordName);
					keywordparam = Keyword.builder()
							.keywordType(Short.parseShort("2"))
							.keywordName(keywordName).chosung(keyword.getChoSung())
							.jungsung(keyword.getJungSung()).jongsung(keyword.getJongSung())
							.keywordUnits(keyword.getKeywordUnit())
							.isSynonym(true)
							.baseCode(data.getKeywordCode())
							.insertCode(1).build()
							;

					GlobalDao.getKeywordDao().save(keywordparam);
				}
			}
		}
	}


	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public void hello(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("errorMsg", "Your username and password are invalid.");
		}
		if (logout != null) {
			model.addAttribute("msg", "You have been logged out successfully");
		}
	}
}
