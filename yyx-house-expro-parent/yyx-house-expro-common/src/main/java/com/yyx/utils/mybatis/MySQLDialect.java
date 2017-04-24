package com.yyx.utils.mybatis;

import java.util.List;
import java.util.Map;

import com.yyx.utils.Dialect;


public class MySQLDialect extends Dialect{  
	  
    public String getLimitString(String sql, int offset, int limit) {  
            return getLimitString(sql,offset,String.valueOf(offset),limit,String.valueOf(limit));  
    }  
      
    public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {  
//        if (offset > limit) {     
//            return sql + " limit "+offsetPlaceholder+","+limitPlaceholder;   
//        } else {     
//            return sql + " limit "+limitPlaceholder;  
//        }    
    	sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append(sql).append(" limit ").append(offsetPlaceholder).append(" , ").append(limitPlaceholder);
		return pagingSelect.toString();
    }     
    public String getAuth(String sql, List<Map<String, Object>> roleList ) {  
    	StringBuffer pagingSelect = new StringBuffer();
    	System.out.println(sql);
    	
    	
    	
//    	pagingSelect.append(sql).append(" limit ").append(offsetPlaceholder).append(" , ").append(limitPlaceholder);
    	return pagingSelect.toString();
    }     
    
}  