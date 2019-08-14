package com.mybatis.mapper;

import lombok.Data;

public class InterfaceMethodInfo {
    private String sqlType;
    private String methodName;
    private String sql;
    private String paramterType;
    private Object resultType;

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParamterType() {
        return paramterType;
    }

    public void setParamterType(String paramterType) {
        this.paramterType = paramterType;
    }

    public Object getResultType() {
        return resultType;
    }

    public void setResultType(Object resultType) {
        this.resultType = resultType;
    }
}
