package com.example.hrh.module.sys.dto.form.menu;


import com.example.hrh.module.common.dto.Form;
import com.example.hrh.module.sys.dao.entities.Menu;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author huangrenhao
 * @date 2018/8/24
 */
public class MenuAddForm implements Form<Menu> {

    private String icon;

    private String url;

    @NotBlank(message = "目录标题不能为空")
    private String name;

    private Long afterMenuId;

    @NotNull(message = "父级ID不能为空")
    private Long parentId;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAfterMenuId() {
        return afterMenuId;
    }

    public void setAfterMenuId(Long afterMenuId) {
        this.afterMenuId = afterMenuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public Menu trainToEntity() {

        Menu menu = new Menu();
        menu.setName(name);
        menu.setIcon(icon);
        menu.setUrl(url);
        return menu;
    }
}
