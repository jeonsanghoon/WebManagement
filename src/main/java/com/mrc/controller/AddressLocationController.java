package com.mrc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.address.AddressLocation;
import com.mrc.db.dto.address.AddressLocationBase;
import com.mrc.db.dto.common.UploadFiles;
import com.mrc.framework.FileInfo;
import com.mrc.framework.Global;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Created by mrc0700@gmail.com on 2021-01-13
 * Discription :
 */
@RestController
public class AddressLocationController {
    @PostMapping("/file/addressLocationfilepond")
    public String addressLocationfilepond(@RequestParam MultipartFile[] filepond) throws Exception {

        List<UploadFiles> rtn = Global.getFileInfo().uploadFile(filepond);
        Global.getFileInfo().setCallBack(new FileInfo.textfileToArrayListCallBack() {

            @Override
            public void result(Map<String, Object> map) throws Exception {
                ObjectMapper mapper = new ObjectMapper();
                //mapper.writer
                AddressLocationBase addressLocation = setAddressLocation(map);
                if(addressLocation.getSaveMode().equals("31"))
                    GlobalDao.getAddressLocationBaseDao().save(addressLocation);
                else if(addressLocation.getSaveMode().equals("34"))
                    GlobalDao.getAddressLocationBaseDao().update(addressLocation);
                else if(addressLocation.getSaveMode().equals("63"))
                    GlobalDao.getAddressLocationBaseDao().delete(addressLocation);
            }
        });
        String res = Global.getFileInfo().textfileToArrayList( rtn.get(0).getAttachmentUrl(), Charset.forName("euc-kr"));
        if(res == "success") res= "업로드완료";
        return res;
    }



    private AddressLocationBase setAddressLocation(Map<String, Object> map)
    {
			/*1 시군구코드 5 문자
					2 출입구일련번호 10 문자
					3 법정동코드 10 문자 PK5
					(시군구코드(5) + 읍면동코드(3) + 00)
					4 시도명 40 문자
					5 시군구명 40 문자
					6 읍면동명 40 문자
					7 도로명코드 12 문자 PK1
					(시군구코드(5)+도로명번호(7))
					8 도로명 80 문자
					9 지하여부 1 문자 PK2
					10 건물본번 5 숫자 PK3
					11 건물부번 5 숫자 PK4
					12 건물명 40 문자
					13 우편번호 5 문자
					14 건물용도분류 100 문자 복수 건물용도가 존재시 콤마(,)로 구분
					15 건물군여부 1 문자 0:단독건물, 1:건물군
					16 관할행정동 8 문자 * 참고용
					17 X좌표 15,6 숫자
					18 Y좌표 15,6 숫자
					19 이동사유코드 2 문자 31 : 생성, 34 : 변동, 63 : 삭제*/
        ProjCoordinate projCoordinate = Global.getMapInfo().utmkToWgs84(
                Double.parseDouble(map.get("F16").toString())
                ,Double.parseDouble(map.get("F17").toString()));

        AddressLocationBase rtn = AddressLocationBase.builder()
                                    .dongCode(map.get("F2").toString())
                                    .sidoName(map.get("F3").toString())
                                    .sigunguName(map.get("F4").toString())
                                    .dongName(map.get("F15").toString())
                                    .zipCode(map.get("F12").toString())
                                    .x(Double.parseDouble(map.get("F16").toString()))
                                    .y(Double.parseDouble(map.get("F17").toString()))
                                    .searchLatitude(Global.getNumberInfo().floor(projCoordinate.y, 2))
                                    .searchLongitude(Global.getNumberInfo().floor(projCoordinate.x, 2))
                                    .latitude(projCoordinate.y)
                                    .longitude(projCoordinate.x)
                                    .saveMode(Integer.parseInt(map.get("F18").toString()))
                                    .build();
        return rtn;
    }
}
