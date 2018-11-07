package com.example.hrh.module.sys.controllers;/**
 * Created by Administrator on 2018/11/4 0004.
 */

import com.example.hrh.module.common.utils.HttpJsonUtils;
import com.example.hrh.module.sys.dao.jpas.TreeNodeMapper;
import com.example.hrh.module.sys.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/4 0004 17:49
 */
@RequestMapping("tree")
@RestController
public class TreeController {

    @Autowired
    private TreeService treeService;

    /**
     * 获取树
     *
     * @param entityKey 实体类别
     * @param status    状态
     * @return
     */
    @GetMapping("{entityKey}")
    public Object getTree(@PathVariable("entityKey") String entityKey, Integer status) {
        return treeService.getTree(entityKey, status);
    }

    /**
     * 获取树
     *
     * @param entityKey 实体类别
     * @param status    状态
     * @return
     */
    @GetMapping("{entityKey}/bootstrap")
    public Object getBootstrapTree(@PathVariable("entityKey") String entityKey, Integer status) {
        return treeService.getBootstrapTree(treeService.getTree(entityKey, status), null);
    }

}
