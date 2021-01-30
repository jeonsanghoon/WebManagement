SET @area_code := '01|01|01';

SELECT a.banner_code
      ,a.title
      ,a.sub_title
      ,a.thumb_image_url
      ,a.search_cnt
      ,TIMESTAMPDIFF( MINUTE,  a.update_date, NOW()) AS diff_min
      ,b.area_data
      ,b.max_gate
      ,c.name AS localbox_name
      ,c.profile_url
      
  FROM t_banner a
 INNER JOIN 
	(SELECT a.banner_code
		,MAX(CASE WHEN @area_code = b.area_code THEN b.gage_rate ELSE 0 END) AS max_gate
		, GROUP_CONCAT(
			DISTINCT CONCAT(b.area_code,'-',b.name,'-', CAST(b.gage_rate AS VARCHAR(10))) 
			ORDER BY b.gage_rate DESC
		  ) AS area_data
	   FROM t_banner_place a
	  INNER JOIN t_banner_place_square b
	     ON a.place_code =b.place_code
	  WHERE (b.area_code LIKE CONCAT(@area_code,'%')
	        OR b.area_code = SUBSTR(@area_code,1,2)
	        OR b.area_code = SUBSTR(@area_code,1,5)
	        )
	  GROUP BY a.banner_code
	 )
	b
    ON a.banner_code = b.banner_code
    LEFT JOIN (
	SELECT b.banner_code, a.profile_url, a.name
	  FROM t_localbox a
	 INNER JOIN t_localbox_banner b
	    ON a.localbox_code = b.localbox_code
	  LIMIT 1
    ) c ON a.banner_code = c.banner_code
    INNER JOIN (
	SELECT banner_code FROM t_banner_keyword
	 GROUP BY banner_code
    ) d ON a.banner_code = d.banner_code
  WHERE a.status = 99
ORDER BY b.max_gate DESC, a.search_cnt DESC
    ;

