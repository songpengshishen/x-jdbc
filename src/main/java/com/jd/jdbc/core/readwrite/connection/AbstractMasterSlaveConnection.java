package com.jd.jdbc.core.readwrite.connection;
import com.jd.jdbc.core.ProxyWrapper;
import com.jd.jdbc.core.readwrite.MasterSlaveConnection;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Title :抽象的主从数据源连接实现
 * Description: 实现一些事物提交等辅助方法,具体的创建Statement方法由子类实现</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public abstract class AbstractMasterSlaveConnection extends ProxyWrapper  implements MasterSlaveConnection{

    /**
     * 用户名
     */
    protected String userName;

    /**
     * 密码
     */
    protected String passWord;

    /**
     * 目标JDBC数据源的连接
     */
    protected Connection targetConnection;

    /**
     * 事务是否自动提交
     */
    private boolean autoCommit = true;

    /**
     * 事务的隔离级别
     */
    private int transactionIsolation = TRANSACTION_REPEATABLE_READ ;

    /**
     * 是否只读
     */
    private boolean readOnly = false;

    /**
     * 是否关闭
     */
    private boolean closed = false;


    protected AbstractMasterSlaveConnection(String userName,String passWord){
       this.userName = userName;
       this.passWord = passWord;
    }


    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.autoCommit = autoCommit;
        if(null != this.targetConnection){
            targetConnection.setAutoCommit(autoCommit);
        }else{
            recordMethodInvocation(Connection.class, "setAutoCommit", new Class[] {boolean.class}, new Object[] {autoCommit});
        }
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    @Override
    public void commit() throws SQLException {
        targetConnection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        targetConnection.rollback();
    }

    @Override
    public Clob createClob() throws SQLException {
        if(null != targetConnection){
            return targetConnection.createClob();
        }
        throw new SQLFeatureNotSupportedException("unsupported createClob() By AbstractMasterSlaveConnection");
    }

    @Override
    public Blob createBlob() throws SQLException {
        if(null != targetConnection){
            return targetConnection.createBlob();
        }
        throw new SQLFeatureNotSupportedException("unsupported createBlob() By AbstractMasterSlaveConnection");
    }

    @Override
    public NClob createNClob() throws SQLException {
        if(null != targetConnection){
            return targetConnection.createNClob();
        }
        throw new SQLFeatureNotSupportedException("unsupported createNClob() By AbstractMasterSlaveConnection");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        if(null != targetConnection){
            return targetConnection.createSQLXML();
        }
        throw new SQLFeatureNotSupportedException("unsupported createSQLXML() By AbstractMasterSlaveConnection");
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        if(null != targetConnection){
            return targetConnection.isValid(timeout);
        }
        throw new SQLFeatureNotSupportedException("unsupported isValid(int timeout) By AbstractMasterSlaveConnection");
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        if(null != targetConnection){
            targetConnection.setClientInfo(name,value);
        }else{
            throw new UnsupportedOperationException("unsupported setClientInfo(String name, String value) By AbstractMasterSlaveConnection");
        }
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        if(null != targetConnection){
            targetConnection.setClientInfo(properties);
        }else{
            throw new UnsupportedOperationException("unsupported setClientInfo(Properties properties) By AbstractMasterSlaveConnection");
        }
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        if(null != targetConnection){
            return targetConnection.getClientInfo(name);
        }
        throw new SQLFeatureNotSupportedException("unsupported getClientInfo(String name) By AbstractMasterSlaveConnection");
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        if(null != targetConnection){
            return targetConnection.getClientInfo();
        }
        throw new SQLFeatureNotSupportedException("unsupported getClientInfo() By AbstractMasterSlaveConnection");
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        if(null != targetConnection){
            return targetConnection.createArrayOf(typeName,elements);
        }
        throw new SQLFeatureNotSupportedException("unsupported createArrayOf(String typeName, Object[] elements) By AbstractMasterSlaveConnection");
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        if(null != targetConnection){
            return targetConnection.createStruct(typeName,attributes);
        }
        throw new SQLFeatureNotSupportedException("unsupported createStruct(String typeName, Object[] attributes) By AbstractMasterSlaveConnection");
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        if(null != targetConnection){
             targetConnection.setSchema(schema);
        }
        throw new SQLFeatureNotSupportedException("unsupported setSchema(String schema) By AbstractMasterSlaveConnection");
    }

    @Override
    public String getSchema() throws SQLException {
        if(null != targetConnection){
            return targetConnection.getSchema();
        }
        throw new SQLFeatureNotSupportedException("unsupported getSchema() By AbstractMasterSlaveConnection");
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        if(null != targetConnection){
             targetConnection.abort(executor);
        }
        throw new SQLFeatureNotSupportedException("unsupported abort(Executor executor) By AbstractMasterSlaveConnection");
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        if(null != targetConnection){
            targetConnection.setNetworkTimeout(executor,milliseconds);
        }
        throw new SQLFeatureNotSupportedException("unsupported setNetworkTimeout(Executor executor, int milliseconds) By AbstractMasterSlaveConnection");
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        if(null != targetConnection){
            return targetConnection.getNetworkTimeout();
        }
        throw new SQLFeatureNotSupportedException("unsupported getNetworkTimeout() By AbstractMasterSlaveConnection");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        if(null != targetConnection){
            return targetConnection.getTypeMap();
        }
        throw new SQLFeatureNotSupportedException("unsupported getTypeMap() By AbstractMasterSlaveConnection");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        if(null != targetConnection){
            targetConnection.setTypeMap(map);
        }
        throw new SQLFeatureNotSupportedException("unsupported setTypeMap(Map<String, Class<?>> map) By AbstractMasterSlaveConnection");
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        if(null != targetConnection){
            targetConnection.setHoldability(holdability);
        }
        throw new SQLFeatureNotSupportedException("unsupported setHoldability(int holdability) By AbstractMasterSlaveConnection");
    }

    @Override
    public int getHoldability() throws SQLException {
        if(null != targetConnection){
            return targetConnection.getHoldability();
        }
        throw new SQLFeatureNotSupportedException("unsupported getHoldability() By AbstractMasterSlaveConnection");
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return determineRealConnection(sql).nativeSQL(sql);
    }


    @Override
    public void close() throws SQLException {

    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }


}
