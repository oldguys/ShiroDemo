package com.example.hrh.module.common.controllers;/**
 * Created by Administrator on 2018/10/31 0031.
 */

import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/31 0031 17:36
 */
public abstract class BaseController {

    public static final String SEPARATOR = ";";

    public void separateStringToLongSet(String source, Set<Long> idSet){
        if(!StringUtils.isEmpty(source)){
            String[] ids = source.split(SEPARATOR);
            for (String id : ids) {
                if (!StringUtils.isEmpty(id)) {
                    idSet.add(Long.valueOf(id));
                }
            }
        }
    }

    public void separateStringToStringSet(String source, Set<String> idSet){
        if(!StringUtils.isEmpty(source)){
            String[] ids = source.split(SEPARATOR);
            for (String id : ids) {
                if (!StringUtils.isEmpty(id)) {
                    idSet.add(id);
                }
            }
        }
    }
}
