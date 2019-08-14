package com.mybatis.handler;

import com.mybatis.mapper.InterfaceMethodInfo;
import com.mybatis.mapper.MapperBean;
import com.mybatis.utils.JDBCUtils;
import com.mybatis.utils.XmlParseUtils;
import org.springframework.jdbc.support.JdbcUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserMapperInvocationHandler implements InvocationHandler {
    private String path;

    public UserMapperInvocationHandler(String path) {
        this.path = path;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = XmlParseUtils.loadXml(path);
        if (!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())) {
            return null;
        }
        List<InterfaceMethodInfo> interfaceMethodInfoList = mapperBean.getInterfaceMethoodInfoList();
        InterfaceMethodInfo currentMethodInfo = getInterfaceMethodInfo(method, interfaceMethodInfoList);
        if (currentMethodInfo == null) {
            return null;
        }
        //返回值对象
        Object returnTypeObj = currentMethodInfo.getResultType();
        //查询入参
        List<Object> paramsList = new ArrayList<>();
        paramsList.add(args[0]);

        ResultSet resultSet = JDBCUtils.query(currentMethodInfo.getSql(), paramsList);
        //结果姐和对象映射
        setReturnTypeObj(returnTypeObj, resultSet);

        return returnTypeObj;
    }

    private void setReturnTypeObj(Object returnTypeObj, ResultSet resultSet) throws Exception {
        if (resultSet != null) {
            while (resultSet.next()) {
                Field[] fields = returnTypeObj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    String fieldValue = resultSet.getString(fieldName);
                    field.setAccessible(true);
                    field.set(returnTypeObj, fieldValue);
                }
            }
        }

    }

    private InterfaceMethodInfo getInterfaceMethodInfo(Method method, List<InterfaceMethodInfo> interfaceMethodInfoList) {
        InterfaceMethodInfo currentMethodInfo = null;
        if (interfaceMethodInfoList != null && interfaceMethodInfoList.size() > 0) {
            for (InterfaceMethodInfo interfaceMethodInfo : interfaceMethodInfoList) {
                if (method.getName().equals(interfaceMethodInfo.getMethodName())) {
                    currentMethodInfo = interfaceMethodInfo;
                    break;

                }
            }
        }
        return currentMethodInfo;
    }
}
