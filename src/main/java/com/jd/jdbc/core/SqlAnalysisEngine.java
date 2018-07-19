package com.jd.jdbc.core;

import com.jd.jdbc.core.domain.Sql;
import com.jd.jdbc.enums.SQLType;
import com.jd.jdbc.exception.XJdbcBaseException;


/**
 * Sql分析引擎,生成具体的Sql对象
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public class SqlAnalysisEngine {


    private SqlAnalysisEngine(){

    }

    /**
     * 分析Sql语句并生成具体的Sql对象
     * @param sql
     * @return
     */
    public static Sql analysisAndGenerateSql(String sql){
        Sql sqlObj = new Sql();
        sqlObj.setSql(sql);
        sqlObj.setSqlType(analysisSql(sql));
        return sqlObj;
    }

    /**
     * 分析Sql语句
     * @param sql
     * @return
     */
    private static SQLType analysisSql(String sql){
      String text = sql.toLowerCase().trim();
      if(text.startsWith("select")){
          return SQLType.DQL;
      }else if(text.startsWith("insert") || text.startsWith("update") || text.startsWith("delete")){
          return SQLType.DML;
      }else {
          throw new XJdbcBaseException("Not Support SqlType!Sql Text : " + sql);
      }
    }
}
