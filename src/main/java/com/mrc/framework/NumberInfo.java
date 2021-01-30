package com.mrc.framework;

/**
 * 숫자 클래스
 * @author  : jsh
 * @Date    : 2020. 3. 30.
 * @Version :
 */
public class NumberInfo {
	/**
	 * 내림
	 * @param value
	 * @return
	 */
	public Double floor(Double value)
	{
		return  floor(value, 0) ;
	}
	/**
	 * 내림
	 * @param value
	 * @param decimalPoint  내림 자리 소숫정 이하일 경우 +, 소숫점보다 클 경우 -
	 * @return
	 */
	public Double floor(Double value, int decimalPoint)
	{
		return Math.floor(value * Math.pow(10, decimalPoint)) / Math.pow(10, decimalPoint) ;
	}
	
	/**
	 * 올림
	 * @param value
	 * @param decimalPoint  내림 자리 소숫정 이하일 경우 +, 소숫점보다 클 경우 -
	 * @return
	 */
	public Double ceil(Double value, int decimalPoint)
	{
		return Math.ceil(value * Math.pow(10, decimalPoint)) * (1/Math.pow(10, decimalPoint)) ;
	}
	
	/**
	 * 반올림
	 * @param value
	 * @param decimalPoint  내림 자리 소숫정 이하일 경우 +, 소숫점보다 클 경우 -
	 * @return
	 */
	public Double round(Double value, int decimalPoint)
	{
		return Math.round(value * Math.pow(10, decimalPoint)) * (1/Math.pow(10, decimalPoint)) ;
	}
}
