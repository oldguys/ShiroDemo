package com.example.hrh.module.sys.dto.form.dictionary;/**
 * Created by Administrator on 2018/10/30 0030.
 */

import com.example.hrh.module.common.dto.Form;
import com.example.hrh.module.sys.dao.entities.Dictionary;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/30 0030 11:26
 */
@Data
public class DictionaryAddForm implements Form<Dictionary> {

    @NotBlank(message = "类别不能为空！")
    private String type;

    private String description;

    @NotBlank(message = "字段名称不能为空！")
    private String filedName;

    @NotBlank(message = "字段值不能为空！")
    private String filedValue;

    @Override
    public Dictionary trainToEntity() {

        Dictionary entity = new Dictionary();
        entity.setFiledName(filedName);
        entity.setDescription(description);
        entity.setType(type);
        entity.setFiledValue(filedValue);

        return entity;
    }
}
