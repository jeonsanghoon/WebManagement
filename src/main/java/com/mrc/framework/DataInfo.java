package com.mrc.framework;

import com.google.gson.Gson;
import com.mrc.db.dto.common.KoreanUnit;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 데이터 클래스
 *
 * @author jsh
 */
public class DataInfo {

    /**
     * lPad 함수
     *
     * @param str
     * @param iLen
     * @param strChar
     * @return
     */
    public String lPad(String str, int iLen, String strChar) {
        String strResult = "";
        StringBuilder sbAddChar = new StringBuilder();
        for (int i = str.length(); i < iLen; i++) {
            // iLen길이 만큼 strChar문자로 채운다.
            sbAddChar.append(strChar);
        }
        strResult = sbAddChar + str;
        return strResult;
    }

    /**
     * rPad 함수
     *
     * @param str
     * @param iLen
     * @param strChar
     * @return
     */
    public String rPad(String str, int iLen, String strChar) {
        String strResult = "";
        StringBuilder sbAddChar = new StringBuilder();
        for (int i = str.length(); i < iLen; i++) {
            // iLen길이 만큼 strChar문자로 채운다.
            sbAddChar.append(strChar);
        }
        strResult = str + sbAddChar;
        return strResult;
    }

    /**
     * 공백 여부
     *
     * @param str
     * @return
     */
    public Boolean isNullEmpty(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 현재일자 문자로 변경
     *
     * @param format
     * @return
     */
    public String nowDateToString(String format) {
        return dateToString(new Date(), format);
    }

    /**
     * 날짜를 문자로 변경
     *
     * @param date
     * @param format
     * @return
     */
    public String dateToString(Date date, String format) {
        format = (Global.getDataInfo().isNullEmpty(format)) ? "yyyyMMdd" : format;
        SimpleDateFormat transFormat = new SimpleDateFormat(format);

        return transFormat.format(date);
    }

    /**
     * 시간을 더하여 날짜를 리턴하는 함수
     *
     * @param date
     * @param hours
     * @return
     */
    public Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    /**
     * 날짜를 더하여 TimeStamp를 리턴하는 함수
     *
     * @param date
     * @param hours
     * @return
     */
    public Timestamp addHoursToTimestamp(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        Timestamp rtn = new Timestamp(calendar.getTime().getTime());
        return rtn;
    }

    /**
     * 현재시간 가져오거
     *
     * @return
     */
    public Timestamp nowTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * List Ojbect를 Map로 변경
     *
     * @param <T>
     * @param target
     * @return
     */
    public <T> List<LinkedHashMap<String, Object>> convertListToMap(List<T> target) {

        List<LinkedHashMap<String, Object>> resultList = new ArrayList<LinkedHashMap<String, Object>>();
        for (T element : target) {
            LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
            Field[] fieldList = element.getClass().getDeclaredFields();
            if (fieldList != null && fieldList.length > 0) {
                try {
                    for (int i = 0; i < fieldList.length; i++) {
                        String curInsName = fieldList[i].getName();
                        Field field = element.getClass().getDeclaredField(curInsName);
                        field.setAccessible(true);
                        Object targetValue = field.get(element);
                        resultMap.put(curInsName, targetValue);
                    }
                    resultList.add(resultMap);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    }

    /**
     * 클래스를 key value map로 가져오기
     *
     * @param <T>
     * @param target
     * @return
     */
    public <T> LinkedHashMap<String, Object> convertClassToMap(T target) {
        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Field[] fieldList = target.getClass().getDeclaredFields();
        if (fieldList != null && fieldList.length > 0) {
            try {
                for (int i = 0; i < fieldList.length; i++) {
                    String curInsName = fieldList[i].getName();
                    Field field = target.getClass().getDeclaredField(curInsName);
                    field.setAccessible(true);
                    Object targetValue = field.get(target);
                    resultMap.put(curInsName, targetValue);
                }
                return resultMap;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 키워드 분할
     *
     * @param text
     * @return
     */
    public KoreanUnit convertKoreanUnit(String text) {
        String chosung = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ";
        String jungsung = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ";
        String jongsung = " ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ";
        int HANGEUL_BASE = 0xAC00; // '가'
        int HANGEUL_END = 0xD7AF;

        // 이하 ja, mo는 단독으로 입력된 자모에 대해 적용
        int JA_BASE = 0x3131;
        int MO_BASE = 0x314F;

        KoreanUnit rtnData = KoreanUnit.builder().choSung("").jungSung("").jongSung("").keywordUnit("").build();
        // 받침이 없을 수 있으므로 제일 첫부분은 띄워줘야 합니다.
        for (char c : text.toCharArray()) {

            if ((c <= 10 && c <= 13) || c == 32) {

                rtnData.setKeywordUnit(rtnData.getKeywordUnit().concat(Character.toString(c).trim()));
                continue;
            } else if (c >= JA_BASE && c <= JA_BASE + 36) {
                rtnData.setKeywordUnit(rtnData.getKeywordUnit().concat(Character.toString(c).trim()));
                continue;
            } else if (c >= MO_BASE && c <= MO_BASE + 58) {
                rtnData.setKeywordUnit(rtnData.getKeywordUnit().concat(Character.toString((char) 0).trim()));
                continue;
            } else if (c >= HANGEUL_BASE && c <= HANGEUL_END) {

                int Code = c - HANGEUL_BASE;

                int JongsungCode = Code % 28; // 종성 코드 분리
                Code = (Code - JongsungCode) / 28;

                int JungsungCode = Code % 21; // 중성 코드 분리
                Code = (Code - JungsungCode) / 21;

                int ChosungCode = Code; // 남는 게 자동으로 초성이 됩니다.

                char Chosung = chosung.toCharArray()[ChosungCode]; // Chosung 목록 중에서 ChosungCode 번째 있는 글자
                char Jungsung = jungsung.toCharArray()[JungsungCode];
                char Jongsung = jongsung.toCharArray()[JongsungCode];

                rtnData.setChoSung(rtnData.getChoSung().concat(Character.toString(Chosung).trim()));
                rtnData.setJungSung(rtnData.getJungSung().concat(Character.toString(Jungsung).trim()));
                rtnData.setJongSung(rtnData.getJongSung().concat(Character.toString(Jongsung).trim()));
                rtnData.setKeywordUnit(rtnData.getKeywordUnit().concat(Character.toString(Chosung).trim())
                        .concat(Character.toString(Jungsung).trim())
                        .concat(Character.toString(Jongsung).trim()));
            } else {
                rtnData.setKeywordUnit(rtnData.getKeywordUnit() + Character.toString(c).trim());
            }
        }
        return rtnData;
    }

    /**
     * 난수 자동생성
     *
     * @param numLen
     * @return
     */
    public String getGenerateNumber(int numLen) {
        Random random = new Random(System.currentTimeMillis());

        int range = (int) Math.pow(10, numLen);
        int trim = (int) Math.pow(10, numLen - 1);
        int result = random.nextInt(range) + trim;

        if (result > range) {
            result = result - trim;
        }
        return String.valueOf(result);
    }

    /**
     * 오브젝트를 Json String 로 리턴하는 함수
     *
     * @param obj
     * @param <T>
     * @return
     */
    public <T> String convertToString(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * String를 Object로 리턴하는 함수
     *
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public <T> T convertToJson(String json, Class<T> t) {
        Gson gson = new Gson();
        return gson.fromJson(json, t);
    }

    /**
     * 비즈니스 코드로 1:영리 0:비영리인지 구분
     *
     * @param businessNumber
     * @return
     */
    public short getProfitType(String businessNumber) {
        businessNumber = businessNumber.replaceAll("-", "");

        final int[] arrChk = {81, 85, 86, 87};/*비영리 코드*/

        String finalBusinessNumber = businessNumber;
        Boolean is_profit = !IntStream.of(arrChk).anyMatch(x -> x == Integer.parseInt(finalBusinessNumber.substring(4, 6)));

        if (is_profit) return 1;
        else return 0;
    }

    /**
     * 리스트 중복제거 List Distinct
     * ex) Global.getDataInfo().distinctList(list, SignageBanner::getSignCode, SignageBanner::getPage);
     * @param list
     * @param keyExtractors
     * @param <T>
     * @return
     */
    public <T> List<T> distinctList(List<T> list, Function<? super T, ?>... keyExtractors) {
        return list.stream()
                .filter(distinctByKeys(keyExtractors))
                .collect(Collectors.toList());
    }

    private <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
        return t -> {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());
            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    /**
     * ResponseEntity를 JSONObject로 변경
     * @param data
     * @return
     * @throws ParseException
     */
    public JSONObject getResponseEntityToJsonObject(ResponseEntity<String> data) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(data.getBody().toString());
    }
}
