package com.mrc.scheduling;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by mrc0700@gmail.com on 2021-01-28
 * Discription :
 */
@Service

public class LoadDataTxtFile {
    @Autowired
    @Value("${spring.datasource.jdbc-url}")
    private String url;

    @Autowired
    @Value("${spring.datasource.hikari.username}")
    private String id;
    @Autowired
    @Value("${spring.datasource.hikari.password}")
    private String pw;
    String driver = "com.mysql.jdbc.Driver";

    protected Connection conn;
    public LoadDataTxtFile(){

    }
    public void exec(String path) throws Exception {
        {
            Connection conn = null;

            Statement stmt = null;

            Class.forName(driver); //간혹 jdbc 드라이버를 못찾을 경우가 있어 넣음 없어도 무방

            try {
                conn = DriverManager.getConnection(url, id, pw);
                stmt = conn.createStatement();

                StringBuffer sbSql = new StringBuffer();
                sbSql.append(" LOAD DATA LOCAL INFILE '").append(path).append("' ").append("\n");

                sbSql.append(" INTO TABLE ondaji.tmp_origin_address_txtfile ").append("\n");
                sbSql.append(" CHARACTER SET euckr ").append("\n"); //인코딩
                sbSql.append(" FIELDS TERMINATED BY '|' ").append("\n"); // | 기준으로 파싱
                sbSql.append(" ENCLOSED BY '\"' ").append("\n");
                sbSql.append(" LINES TERMINATED BY '\\n' ").append("\n"); //라인끝 (윈도우는 \r\n 이라함)
                sbSql.append(" ( @sigungu_code	   ,@enter_code	    ,@dong_code	 ,@sido_name		,@sigungu_name ").append("\n");
                sbSql.append("  ,@dong_name        ,@road_code	    ,@road_name	 ,@is_basement		,@building_no	   ").append("\n");
                sbSql.append("  ,@building_sub_no  ,@building_name  ,@zone_no	 ,@buinding_kind	,@building_type	   ").append("\n");
                sbSql.append("  ,@dong2_name	   ,@x		    ,@y		 ,@save_type").append("\n");
                sbSql.append(" ) " ).append("\n");
                sbSql.append(" set sigungu_code	   = @sigungu_code ").append("\n");
                sbSql.append("    ,enter_code      = @enter_code ").append("\n");
                sbSql.append("    ,dong_code       = @dong_code ").append("\n");
                sbSql.append("    ,sido_name       = @sido_name ").append("\n");
                sbSql.append("    ,sigungu_name	   = @sigungu_name	").append("\n");
                sbSql.append("    ,dong_name       = @dong_name ").append("\n");
                sbSql.append("    ,road_code	   = @road_code ").append("\n");
                sbSql.append("    ,road_name	   = @road_name ").append("\n");
                sbSql.append("    ,is_basement     = @is_basement ").append("\n");
                sbSql.append("    ,building_no     = @building_no ").append("\n");
                sbSql.append("    ,building_sub_no = @bui~lding_sub_no ").append("\n");
                sbSql.append("    ,building_name   = @building_name ").append("\n");
                sbSql.append("    ,zone_no	       = @zone_no	").append("\n");
                sbSql.append("    ,buinding_kind   = @buinding_kind " ).append("\n");
                sbSql.append("    ,building_type   = @building_type ").append("\n");
                sbSql.append("    ,dong2_name      = @dong2_name ").append("\n");
                sbSql.append("    ,x			   = @x ").append("\n");
                sbSql.append("    ,y			   = @y ").append("\n");
                sbSql.append("    ,save_type       = @save_type; ").append("\n");

                boolean result = stmt.execute(sbSql.toString());
            } catch (Exception e) {

                System.out.println("e: " + e);

            } finally {
                conn.close();
                stmt.close();
            }
        }
    }
}

