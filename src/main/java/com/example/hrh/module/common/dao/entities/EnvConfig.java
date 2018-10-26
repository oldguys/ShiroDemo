package com.example.hrh.module.common.dao.entities;/**
 * Created by Administrator on 2018/10/16 0016.
 */

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/16 0016 17:18
 */
@Entity
@Data
public class EnvConfig {

    @Id
    @GeneratedValue
    private Integer id;

    private Boolean initStatus;

    private Date createTime;
}
