package com.mrc.scheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.address.AddressAreaCond;
import com.mrc.db.dto.address.AddressLocationBase;
import com.mrc.db.dto.address.AddressLocationBaseCond;
import com.mrc.db.dto.common.KoreanUnit;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.framework.FileInfo;
import com.mrc.framework.Global;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mrc0700@gmail.com on 2021-01-27
 * Discription :
 */
public class addressLocationScheduler {
    /**
     * 방법2 Text파일을 tmp_origin_address_txtfile 테이블로 일괄 인서트 후 처리
     * @throws Exception
     */
    public void addressLocationUpdateAfterByTempTable() throws Exception {

        Timestamp timestampStart = new Timestamp(System.currentTimeMillis());

        String dirPath = Global.getConfigInfo().getFileBasePath() + "locationSchedule";
        File files[] = Global.getFileInfo().filesInDirectory(dirPath);
        int idx = 0;
        for(File file : files)
        {
            String path = Global.getFileInfo().getRealFilePath(file.getPath());
            this.fileUpdate(path);
            this.fileCompletedMove(path);
            idx++;
        }
        System.out.println(dirPath);
        if(idx > 0) {
            GlobalDao.getAddressLocationBaseDao().autoAddAddressAreaByAddressLocationBase();
            this.addressAreaUpdate();
        }
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
        filePath = filePath.replace("\\", "/");
        List<AddressLocationBase> list = GlobalDao.getAddressLocationDao().getAddressLocationBaseListAftertextFilaSave(filePath);
        //AddressLocationBaseCond cond = AddressLocationBaseCond.builder().isNullSearchLatitude(true).build();
        //List<AddressLocationBase> list = GlobalMapper.getAddressLocationBaseMapper().getList(cond);
        List<AddressLocationBase> saveList = new ArrayList<>();
        int idx = 1;
        for (AddressLocationBase data : list) {
            ProjCoordinate projCoordinate = Global.getMapInfo().utmkToWgs84(
                    Double.parseDouble(data.getX().toString())
                    , Double.parseDouble(data.getY().toString())
            );

            data.setSearchLatitude(Global.getNumberInfo().floor(projCoordinate.y, 2));
            data.setSearchLongitude(Global.getNumberInfo().floor(projCoordinate.x, 2));
            data.setBaseLatitude(this.getBaseLocation(projCoordinate.y));
            data.setBaseLongitude(this.getBaseLocation(projCoordinate.x));
            data.setLatitude(projCoordinate.y);
            data.setLongitude(projCoordinate.x);

            saveList.add(data);
            if (idx % 10 == 0) {
                GlobalDao.getAddressLocationBaseDao().updateList(saveList);
                saveList = new ArrayList<>();
            }
            idx++;
        }

        if (saveList.size() > 0) {
            GlobalDao.getAddressLocationBaseDao().updateList(saveList);
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
    private AddressArea setLatitudeFromKakaoApi(AddressArea addressArea , JSONObject jsonObject)
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
