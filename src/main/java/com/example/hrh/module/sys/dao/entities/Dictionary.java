package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/30 0030.
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/30 0030 11:01
 */
@Entity(name = "sys_dictionary")
@TableName(value = "sys_dictionary")
public class Dictionary extends BaseEntity{

    private String type;

    private String description;

    private String filedName;

    private String filedValue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getFiledValue() {
        return filedValue;
    }

    public void setFiledValue(String filedValue) {
        this.filedValue = filedValue;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", filedName='" + filedName + '\'' +
                ", filedValue='" + filedValue + '\'' +
                "} " + super.toString();
    }

    /**
     *  字典类别
     */
    public enum DictionaryType{

        ROLE_TYPE("role-type","权限-角色类型"),
        USER_GROUP_TYPE("group-type","权限-用户组类型")
        ;
        private String name;

        private String value;

        DictionaryType(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
