package com.example.hrh.module.sys.controllers;/**
 * Created by Administrator on 2018/11/11 0011.
 */

import com.example.hrh.module.common.utils.HttpJsonUtils;
import com.example.hrh.module.sys.service.ResourcePermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/11 0011 14:56
 */
@RestController
@RequestMapping("resource/perm/")
public class ResourcePermController {


    @Autowired
    private ResourcePermService resourcePermService;

    @GetMapping("levelMap")
    public Object getResourcePermLevel(){
        return ResourcePermService.permLevelMap;
    }

    @GetMapping("update")
    public Object update(){
        resourcePermService.initPermLevel();
        return HttpJsonUtils.OK;
    }
}
