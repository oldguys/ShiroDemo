package com.example.hrh.module.view.sys;/**
 * Created by Administrator on 2018/10/29 0029.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/29 0029 09:35
 */
@RequestMapping("view/perm")
@Controller
public class PermViewController {

    @RequestMapping("role/page")
    public String roleIndex(){
        return "sys/role";
    }

    @RequestMapping("userGroup/page")
    public String userGroupIndex(){
        return "sys/userGroup";
    }

    @RequestMapping("dictionary/page")
    public String dictionaryIndex(){
        return "sys/dictionary";
    }

    @RequestMapping("user/page")
    public String userPerm(){
        return "sys/user_perm";
    }
}
