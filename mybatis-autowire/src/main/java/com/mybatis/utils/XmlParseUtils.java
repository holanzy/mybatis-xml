package com.mybatis.utils;

import com.mybatis.mapper.InterfaceMethodInfo;
import com.mybatis.mapper.MapperBean;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlParseUtils {
    public static MapperBean loadXml(String path) {
        InputStream is = getXmlPathInputStream(path);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(is);
            Element rootElement = document.getRootElement();
            String nameSpace = rootElement.attributeValue("namespace").trim();
            List<InterfaceMethodInfo> interfaceMethodInfoList = parseXml(rootElement);
            return getMapperBean(nameSpace, interfaceMethodInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MapperBean getMapperBean(String nameSpace, List<InterfaceMethodInfo> interfaceMethodInfoList) {
        MapperBean mapperBean = new MapperBean();
        mapperBean.setInterfaceName(nameSpace);
        mapperBean.setInterfaceMethoodInfoList(interfaceMethodInfoList);
        return mapperBean;
    }

    private static List<InterfaceMethodInfo> parseXml(Element rootElement) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<InterfaceMethodInfo> interfaceMethodInfoList = new ArrayList<>();
        Iterator iterator = rootElement.elementIterator();

        while (iterator.hasNext()) {
            InterfaceMethodInfo interfaceMethodInfo = new InterfaceMethodInfo();
            Element element = (Element) iterator.next();
            String sqlType = element.getName().trim();
            String methodName = element.attributeValue("id").trim();
            String sql = element.getText().trim();
            String resultType = element.attributeValue("resultType").trim();

            interfaceMethodInfo.setSqlType(sqlType);
            interfaceMethodInfo.setMethodName(methodName);
            interfaceMethodInfo.setSql(sql);

            interfaceMethodInfoList.add(interfaceMethodInfo);
            Object object = Class.forName(resultType).newInstance();
            interfaceMethodInfo.setResultType(object);
        }

        return interfaceMethodInfoList;
    }

    private static InputStream getXmlPathInputStream(String path) {
        return XmlParseUtils.class.getClassLoader().getResourceAsStream(path);
    }
}
