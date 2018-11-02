package com.example.hrh.module.sys.controllers;/**
 * Created by Administrator on 2018/10/30 0030.
 */

import com.example.hrh.module.common.utils.HttpJsonUtils;
import com.example.hrh.module.common.utils.ValidateUtils;
import com.example.hrh.module.sys.dao.entities.Dictionary;
import com.example.hrh.module.sys.dao.entities.UserGroup;
import com.example.hrh.module.sys.dao.jpas.DictionaryMapper;
import com.example.hrh.module.sys.dto.form.dictionary.DictionaryAddForm;
import com.example.hrh.module.sys.dto.form.dictionary.DictionaryUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/30 0030 11:05
 */
@RestController
@RequestMapping("dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryMapper dictionaryMapper;
    private static final Map<String,String> DICTIONARY_TYPE = new HashMap<>();

    @GetMapping("all")
    public Object getAll(Integer status) {
        return dictionaryMapper.findAllByStatus(status);
    }

    @GetMapping("{id}")
    public Object getOne(@PathVariable Long id) {
        return dictionaryMapper.selectById(id);
    }

    @PostMapping("addition")
    @Transactional(rollbackOn = Exception.class)
    public Object addition(@Valid DictionaryAddForm form, BindingResult bindingResult) {

        ValidateUtils.validate(bindingResult);

        Dictionary entity = form.trainToEntity();
        entity.setStatus(1);
        entity.setCreateTime(new Date());

        int resultCode = dictionaryMapper.insert(entity);
        return resultCode > 0 ? HttpJsonUtils.buildSuccess("新增成功！",entity) : HttpJsonUtils.ERROR;
    }

    @PostMapping("modify")
    @Transactional(rollbackOn = Exception.class)
    public Object modify(@Valid DictionaryUpdateForm form, BindingResult bindingResult) {

        ValidateUtils.validate(bindingResult);

        Dictionary entity = form.trainToEntity();
        int resultCode = dictionaryMapper.updateById(entity);

        return resultCode > 0 ? HttpJsonUtils.buildSuccess("修改成功！",entity) : HttpJsonUtils.ERROR;
    }

    @PostMapping("{id}/{status}")
    public Object updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {

        int resultCode = dictionaryMapper.updateStatus(id, status);
        return resultCode > 0 ? HttpJsonUtils.OK : HttpJsonUtils.ERROR;
    }

    @GetMapping("type")
    public Object getRoleType() {
        if (DICTIONARY_TYPE.isEmpty()) {
            synchronized (getClass()){
                if(DICTIONARY_TYPE.isEmpty()){
                    Dictionary.DictionaryType[] dictionaryTypes = Dictionary.DictionaryType.values();
                    for (Dictionary.DictionaryType type : dictionaryTypes) {
                        DICTIONARY_TYPE.put(type.getName(),type.getValue());
                    }
                }
            }
        }
        return DICTIONARY_TYPE;
    }

}
