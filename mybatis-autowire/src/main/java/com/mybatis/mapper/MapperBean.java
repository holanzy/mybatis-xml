package com.mybatis.mapper;

import lombok.Data;

import java.util.List;

@Data
public class MapperBean {
    private String interfaceName;

    private List<InterfaceMethodInfo> interfaceMethoodInfoList;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<InterfaceMethodInfo> getInterfaceMethoodInfoList() {
        return interfaceMethoodInfoList;
    }

    public void setInterfaceMethoodInfoList(List<InterfaceMethodInfo> interfaceMethoodInfoList) {
        this.interfaceMethoodInfoList = interfaceMethoodInfoList;
    }


}
