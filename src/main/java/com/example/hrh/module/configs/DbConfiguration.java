package com.example.hrh.module.configs;
/**
 * Created by Administrator on 2018/10/15 0015.
 */


import com.example.hrh.module.common.services.DbRegister;
import com.example.hrh.module.common.utils.Log4jUtils;

import org.apache.commons.collections.map.HashedMap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.*;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/15 0015 17:45
 */
@MapperScan(basePackages = "com.example.hrh.module.*.dao.jpas")
@EnableTransactionManagement
@Configuration
public class DbConfiguration {

    @Value("${db.type-aliases-package}")
    private String typeAliasesPackage;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initDB() {

        DbRegister dbRegister = new DbRegister();
        Map<String, String> tableMap = new HashedMap();
        List<String> typeAliasesPackages = splitPackagesPath(typeAliasesPackage);

        for (String path : typeAliasesPackages) {
            tableMap.putAll(dbRegister.registerClassToDB(path));
        }

        if (!tableMap.keySet().isEmpty()) {

            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(dbRegister.getTableFactory().showTableSQL());
            Set<String> tableNameSet = new HashSet<>();
            for (Map<String, Object> item : mapList) {
                for (String key : item.keySet()) {
                    tableNameSet.add((String) item.get(key));
                }
            }

            for (String key : tableMap.keySet()) {
                if (!tableNameSet.contains(key)) {
                    Log4jUtils.getInstance(getClass()).info("未找到表[" + key + "],进行创建.");
                    String sql = tableMap.get(key);
                    if (sql.trim().length() > 0) {
                        jdbcTemplate.execute(sql);
                        Log4jUtils.getInstance(getClass()).info("\n\n" + sql);
                    }
                } else {
                    Log4jUtils.getInstance(getClass()).info("表[" + key + "] 已存在");
                }
            }
        }
    }

    private List<String> splitPackagesPath(String typeAliasesPackage) {
        List<String> paths = new ArrayList<>();
        String[] packagePaths = typeAliasesPackage.split(";");
        for (String path : packagePaths) {
            if (!StringUtils.isEmpty(path)) {
                paths.add(path);
            }
        }
        return paths;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        System.out.println(JdbcTemplate.class.getSimpleName());

        return new JdbcTemplate(dataSource);
    }

}
