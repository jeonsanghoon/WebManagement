package com.mrc.scheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.address.AddressAreaCond;
import com.mrc.db.dto.address.AddressLocationBase;
import com.mrc.db.dto.address.AddressLocationBaseCond;
import com.mrc.db.dto.common.KoreanUnit;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.framework.FileInfo;
import com.mrc.framework.Global;
import groovy.util.logging.Slf4j;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mrc0700@gmail.com on 2021-01-14
 * Discription :
 */
@Slf4j
@Component
public class addressAreaScheduler {

    //@Scheduled(cron="*/30 * * * * *")

    /**
     * 방법 1 텍스트 파일을 1건씩 처리
     * @throws Exception
     */
    public void addressLocationBase() throws Exception {
        /*
        JSONObject jsonObject  = getKakaoApiLocationByaddress("경기도 안성시");
        AddressArea addressArea = new AddressArea();
        addressArea = this.setLatitudeFromKakaoApi(addressArea, jsonObject);
        System.out.println("호출");
       */

        Timestamp timestampStart = new Timestamp(System.currentTimeMillis());


        String dirPath = Global.getConfigInfo().getFileBasePath() + "locationSchedule";
        File files[] = Global.getFileInfo().filesInDirectory(dirPath);
        for(File file : files)
        {
            //System.out.println(Global.getFileInfo().getRealFilePath(file.getPath()));
            String path = Global.getFileInfo().getRealFilePath(file.getPath());
            this.fileUpdate(Global.getFileInfo().getRealFilePath(file.getPath()));
            this.fileCompletedMove(path);
        }
        System.out.println(dirPath);

        GlobalDao.getAddressLocationBaseDao().autoAddAddressAreaByAddressLocationBase();

        this.addressAreaUpdate();
        Timestamp timestampEnd = new Timestamp(System.currentTimeMillis());
        long diff = timestampEnd.getTime() - timestampStart.getTime();
        System.out.println("총실행시간 => " + Long.toString(diff));
    }

    /**
     * 방법2 Text파일을 tmp_origin_address_txtfile 테이블로 일괄 인서트 후 처리
     * @throws Exception
     */
    public void addressLocationUpdateAfterByTempTable() throws Exception {

        Timestamp timestampStart = new Timestamp(System.currentTimeMillis());


        String dirPath = Global.getConfigInfo().getFileBasePath() + "locationSchedule";
        File files[] = Global.getFileInfo().filesInDirectory(dirPath);
        for(File file : files)
        {
            //System.out.println(Global.getFileInfo().getRealFilePath(file.getPath()));
            String path = Global.getFileInfo().getRealFilePath(file.getPath());
            this.fileUpdate(Global.getFileInfo().getRealFilePath(file.getPath()));
            this.fileCompletedMove(path);
        }
        System.out.println(dirPath);

        GlobalDao.getAddressLocationBaseDao().autoAddAddressAreaByAddressLocationBase();

        this.addressAreaUpdate();
        Timestamp timestampEnd = new Timestamp(System.currentTimeMillis());
        long diff = timestampEnd.getTime() - timestampStart.getTime();
        System.out.println("총실행시간 => " + Long.toString(diff));
    }


    /***
     * 1. 파일업데이트
     * @param filePath
     * @throws Exception
     */
    private void fileUpdate(String filePath) throws Exception {
        Global.getFileInfo().setCallBack(new FileInfo.textfileToArrayListCallBack() {
            @Override
            public void result(Map<String, Object> map) throws Exception {
                ObjectMapper mapper = new ObjectMapper();
                //mapper.writer
                if(!checkData(map)) return;
                AddressLocationBase addressLocation = setAddressLocation(map);
                addressLocationBaseSave(addressLocation);
            }
        });
        String res = Global.getFileInfo().textfileToArrayList( filePath, Charset.forName("euc-kr"));
        if(res == "success") {
            System.out.println("업로드완료");

        }

    }

    /**
     * 1.1 데이터 체크
     * @param map
     * @return
     */
    private boolean checkData(Map<String, Object> map)
    {
        /**
         * 위치정보가 없는 경우
         */
        if(Global.getDataInfo().isNullEmpty(map.get("F16").toString()) ) {
            return false;
        }
        /**
         * 행정동명이 없을 경우 제외 출입구로 추정
         */
        if(Global.getDataInfo().isNullEmpty(map.get("F15").toString())){
            return false;
        }
        /**
         * 행정동명에 마지막 글자가 출장 또는 출장소일경우 제외
         */
        if(!Global.getDataInfo().isNullEmpty(map.get("F15").toString())
                && map.get("F15").toString().length() > 3
            )
        {
            String dong2Name = map.get("F15").toString();

            String chkName = dong2Name.substring(dong2Name.length() - 3, dong2Name.length() - 1);
            if (chkName.equals("출장소") || chkName.substring(1).equals("출장")) return false;

        }

        return true;
    }
    /**
     * 1.2 AddressLocationBase 데이터 셋팅
     * @param map
     * @return
     */
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


        /** wgs 84 코드로 변환 **/
        ProjCoordinate projCoordinate = Global.getMapInfo().utmkToWgs84(
                Double.parseDouble(map.get("F16").toString())
                , Double.parseDouble(map.get("F17").toString()));

        AddressLocationBase rtn = AddressLocationBase.builder()
                .dongCode(map.get("F2").toString())
                .sidoName(map.get("F3").toString())
                .sigunguName(map.get("F4").toString())
                .dongName(map.get("F5").toString())
                .dong2Name(this.Dong2NameReplace(map.get("F15").toString()))
                .zipCode(map.get("F12").toString())
                .x(Double.parseDouble(map.get("F16").toString()))
                .y(Double.parseDouble(map.get("F17").toString()))
                .searchLatitude(Global.getNumberInfo().floor(projCoordinate.y, 2))
                .searchLongitude(Global.getNumberInfo().floor(projCoordinate.x, 2))
                .baseLatitude(this.getBaseLocation(projCoordinate.y))
                .baseLongitude(this.getBaseLocation(projCoordinate.x))
                .latitude(projCoordinate.y)
                .longitude(projCoordinate.x)
                .saveMode(Integer.parseInt(map.get("F18").toString()))
                .build();
        /**
         * 31:생성, 34:변동 63 : 삭제
         */
        if(map.get("F18").toString().equals("63")) rtn.setHide(true);
        else {
            rtn.setHide(false);
        }
        return rtn;
    }

    /**
     * 1.2.1 읍면동(행정동)에서 순번에서 사용하는 "제" 글자 삭제
     * @param dong2Name
     * @return
     */
    private String Dong2NameReplace(String dong2Name)
    {
        if(Global.getDataInfo().isNullEmpty(dong2Name)) return "";
        if(dong2Name.indexOf("제") > 0)
        {
            return dong2Name.replace("제1동","1동").replace("제2동","2동")
                    .replace("제3동","3동").replace("제3동","3동")
                    .replace("제4동","4동").replace("제4동","4동")
                    .replace("제5동","5동").replace("제6동","6동")
                    .replace("제7동","7동").replace("제8동","8동")
                    .replace("제9동","9동");
        }
        else return dong2Name;
    }

    /**
     * 1.2.2 BaseLocation 계산
     * 37, 126, 37, 126.00125일 경우  0.11101
     * @param val
     * @return
     */
    private Double getBaseLocation(Double val)
    {
        //cast((floor(latitude * 100000.000) - (floor(latitude * 100000.000) % 125.000)) / 100000.000 as decimal(7,5))
        /*0.00125 약 100m 거리*/
        Double range= 100000d;
        Double distance = 125d;
        return (Global.getNumberInfo().floor(val * range) - ( Global.getNumberInfo().floor(val * range) % distance)) / range;
    }

    /**
     * 1.3 AddressLocationBase 저장
     * @param addressLocation
     */
    private void addressLocationBaseSave(AddressLocationBase addressLocation){
        AddressLocationBaseCond cond = AddressLocationBaseCond.builder()
                                    .sidoName(addressLocation.getSidoName())
                                    .sigunguName(addressLocation.getSigunguName())
                                    .dongName(addressLocation.getDongName())
                                    .dong2Name(addressLocation.getDong2Name())
                                    .x(addressLocation.getX()).y(addressLocation.getY()).build();
        if(GlobalDao.getAddressLocationBaseDao().getList(cond).size() == 0)
            GlobalDao.getAddressLocationBaseDao().save(addressLocation);
        else
            GlobalDao.getAddressLocationBaseDao().update(addressLocation);

        /**
         * 31:생성, 34:변동
         */
        /*if(addressLocation.getSaveMode() == 31 || addressLocation.getSaveMode() == 34) {
            if(GlobalDao.getAddressLocationBaseDao().getList(cond).size() == 0)
                GlobalDao.getAddressLocationBaseDao().save(addressLocation);
            else
                GlobalDao.getAddressLocationBaseDao().update(addressLocation);
        }*/
        /**
         * 63:삭제
         */
        /*
        else if(addressLocation.getSaveMode()== 63)
            GlobalDao.getAddressLocationBaseDao().delete(addressLocation);
         */
    }



    /**
     * 2.업로드 후 파일이동
     * @param path
     */
    private void fileCompletedMove(String path){

        String dirPath = (Global.getConfigInfo().getFileBasePath() + "locationScheduleCompleated");
        File file = new File(path);
        String fileName = file.getName();
        Global.getFileInfo().makeDirectory(dirPath);
        String extension = "." + FilenameUtils.getExtension(file.getName());
        String toFileName = fileName.replace(extension,"");
        toFileName  =  toFileName + "_" +  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + extension;
        String toPath = dirPath + "\\" + toFileName;

        Global.getFileInfo().moveFile(path,toPath);
    }


    /**
     * 3. 위도/경도, 키워드 추가/업데이트
     * @throws UnsupportedEncodingException
     * @throws ParseException
     */
    private void addressAreaUpdate() throws UnsupportedEncodingException, ParseException {
        AddressAreaCond addressAreaCond = AddressAreaCond.builder().isNullLatitude(true).hide(false).build();

        List<AddressArea> list = GlobalDao.getAddressAreaDao().getList(addressAreaCond);
        for(AddressArea data : list){
            if(data.getLatitude() == null || data.getLatitude() == null) {
                JSONObject jsonObject = this.getKakaoApiLocationByaddress(data.getFullName());
                data = this.setLatitudeFromKakaoApi(data, jsonObject);
            }
            if(data.getKeywordCode() == null && !Global.getDataInfo().isNullEmpty(data.getName()))
            {
                KoreanUnit koreanUnit = Global.getDataInfo().convertKoreanUnit(data.getName());
                Keyword keyword = Keyword.builder()
                                        .keywordName(data.getName())
                                        .keywordType((short) 2)
                                        .baseCode(null)
                                        .chosung(koreanUnit.getChoSung())
                                        .jungsung(koreanUnit.getJungSung())
                                        .jongsung(koreanUnit.getJongSung())
                                        .keywordUnits(koreanUnit.getKeywordUnit())
                                        .insertCode(1)
                                        .build();
                GlobalDao.getKeywordDao().save(keyword);
                data.setKeywordCode(keyword.getKeywordCode());
                GlobalDao.getAddressAreaDao().update(data);

            }
        }
    }

    /**
     * 3.1 카카오 API에서 주소 정보 가져오기
     * @param address
     * @return
     * @throws UnsupportedEncodingException
     * @throws ParseException
     */
    private JSONObject getKakaoApiLocationByaddress(String address) throws UnsupportedEncodingException, ParseException {

        address = 	URLEncoder.encode(address, "UTF-8");

        ResponseEntity<String> rtn = Global.getKakaoApi().call(URI.create("https://dapi.kakao.com/v2/local/search/address.json?query=" + address + "&page=1&size=10")) ;

        return Global.getDataInfo().getResponseEntityToJsonObject(rtn);
    }

    /**
     * 3.2 카카오 API에서 받은 주소정보에서 위도/경도 추출하기
     * @param addressArea
     * @param jsonObject
     * @return
     */
    private AddressArea setLatitudeFromKakaoApi(AddressArea addressArea ,JSONObject jsonObject)
    {
        JSONArray docuArray = (JSONArray) jsonObject.get("documents");
        if(docuArray.size() > 0)
        {
            JSONObject documentObject = (JSONObject) docuArray.get(0);
            documentObject = (JSONObject)documentObject.get("address");
            addressArea.setLatitude(Double.parseDouble(documentObject.get("y").toString()));
            addressArea.setLongitude(Double.parseDouble(documentObject.get("x").toString()));
        }
        return addressArea;
    }
}
