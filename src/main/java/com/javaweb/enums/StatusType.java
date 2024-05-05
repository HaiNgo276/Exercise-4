package com.javaweb.enums;

import java.util.Map;
import java.util.TreeMap;

public enum StatusType {
    DXL ("Đang xử lý"),
    XLX(" Đã xử lý"),
    CXL("Chưa xử lý");

    private final String statusName;
    StatusType(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName(){
        return statusName;
    }

    public static Map<String,String> type(){
        Map<String,String> listType = new TreeMap<>();
        for(StatusType item : StatusType.values()){
            listType.put(item.getStatusName() , item.statusName);
        }
        return listType;
    }
}
