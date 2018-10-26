### 初始化配置
配置之后可以不配置Mybatis-spring参数
1. column-like 开启列模糊查询
2. 使用 com.baomidou.mybatisplus.annotation.TableName 配置表名，不配置默认为实体类名（userentity），若开启的驼峰转双峰，则为下划线（user_entity）


    mybatis-plus:
      config-location: classpath:configs/myBatis-config.xml
      mapper-locations: classpath:mappers/**/*.xml
      type-aliases-package: com.example.hrh.module.sys.dao.entities
      global-config:
        db-config:
          column-like: true