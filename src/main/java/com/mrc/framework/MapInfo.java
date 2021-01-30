package com.mrc.framework;

import org.locationtech.proj4j.BasicCoordinateTransform;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.ProjCoordinate;

/**
 * 지도 위치 정보
 * 
 * @author : jsh
 * @Date : 2020. 3. 26.
 * @Version :
 */
public class MapInfo {
	/**
	 * grs80 utm-k(x, y)좌표를 WGS84(latitude, longtude) 로 변경
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public ProjCoordinate utmkToWgs84(Double x, Double y) {
		return this.convertCoordinates("EPSG:5179", "EPSG:4326", x, y);
	}

	/**
	 * WGS84(latitude, longtude)좌표를 grs80 utm-k(x, y)로 변경
	 * 
	 * @param lng 위도
	 * @param lat 경도
	 * @return
	 */
	public ProjCoordinate wgs84ToUtmk(Double lng, Double lat) {

		return this.convertCoordinates("EPSG:4326", "EPSG:5179", lng, lat);
	}

	/**
	 * 좌표 변경
	 * 
	 * @param frTypeName 현재 좌표 예) EPSG:4326
	 * @param toTypeName 변경할 좌표 예) EPSG:5179
	 * @param x          가로좌표
	 * @param y          세로좌표
	 * @return
	 */
	public ProjCoordinate convertCoordinates(String frTypeName, String toTypeName, Double x, Double y) {
		CRSFactory factory = new CRSFactory();
		CoordinateReferenceSystem srcCrs = factory.createFromName(frTypeName);// 현재 좌표
		CoordinateReferenceSystem dstCrs = factory.createFromName(toTypeName);// 변경할 좌표
		BasicCoordinateTransform transform = new BasicCoordinateTransform(srcCrs, dstCrs);
		ProjCoordinate srcCoord = new ProjCoordinate(x, y);
		ProjCoordinate dstCoord = new ProjCoordinate();

		//System.out.println(dstCoord.x + "," + dstCoord.y);// 변환된 좌표

		return transform.transform(srcCoord, dstCoord);// 좌표변환
	}

}
