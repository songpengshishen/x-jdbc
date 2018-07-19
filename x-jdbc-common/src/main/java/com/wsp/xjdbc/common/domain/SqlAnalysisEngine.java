package com.wsp.xjdbc.common.domain;


import com.wsp.xjdbc.common.enums.SQLType;
import com.wsp.xjdbc.common.exception.XJdbcBaseException;

/**
 * Sql分析引擎,生成具体的Sql对象
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/19
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
