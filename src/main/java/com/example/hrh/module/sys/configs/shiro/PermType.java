package com.example.hrh.module.sys.configs.shiro;/**
 * Created by Administrator on 2018/10/25 0025.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/25 0025 14:45
 */
public enum PermType {

    ADMIN("admin", "超级管理员"),
    SUB_ADMIN("sub_admin", "管理员");

    PermType(String flag, String value) {
        this.flag = flag;
        this.value = value;
    }

    public static List<String> keySet() {
        List<String> flagSet = new ArrayList<>();
        PermType[] types = values();
        for (PermType type : types) {
            flagSet.add(type.getFlag());
        }
        return flagSet;
    }

    private String flag;

    private String value;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
