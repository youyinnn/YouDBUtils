## YouDBUtils
[![Travis](https://img.shields.io/badge/version-2.2.3-green.svg)]()
[![Travis](https://img.shields.io/badge/druid-1.1.8-brightgreen.svg)]()
[![Travis](https://img.shields.io/badge/cglibnodep-3.2.5-brightgreen.svg)]()

**这是一个“自用”的数据库工具包，你就把它当成orm框架也行 :smile:**

#### 使用

通过maven:
```xml
    <dependency>
        <groupId>com.github.youyinnn</groupId>
        <artifactId>you-db-utils</artifactId>
        <version>2.2.3</version>
    </dependency>
```

- - -

#### 特点

- **拒绝配置文件：** 以alibaba的**Druid**为基础（拒绝**DBCP/C3P0**），支持代码配置**Druid**，拒绝配置文件

- **常规：** 仅支持**MySQL/SQLite**（自用. 轻巧）

- **IOC/DI/Transaction：** 类似spring的控制反转，主要用于生成指定包下的DAO类的代理类（依赖**cglib**），让其具有事务特性，在ioc容器中以类名获取

- **不忘底层：** 提供**CRUD**的基本操作类**SqlExecutor**

- **可直接面向Model：** 提供直接面向**Model**类的操作类**ModelHandler**，和数据库记录可直接映射对应，无需映射文件（**这就需要遵循本人的惯例了**）

- **内置log输出：** 工具内置log输出，依赖**log4j2**，并且与用户配置的log4j2.xml完全无缝融合

> *需要特别声明的是，因为这个工具类是自用的工具类，所以使用了本人所习惯的惯例来减少配置量，所以惯例部分一定要看仔细。*

- - -

#### TODO
- [ ] ~~YouController注释,配合Service的自动装配~~(cancel)
- [x] 事务的传播行为可选功能待实现(done:只存在PROPAGATION_REQUIRED)
- [x] 其他重要监控的配置(done:wall默认, stat, log4j2可高度配置)
- [x] 启动初始化信息的输出(done:基本的启动和业务流)
- [x] log4j2的引入(done:嵌入)
- [ ] 一级甚至多级缓存

###### 更多详情请移步[wiki页](https://github.com/youyinnn/YouDBUtils/wiki)了解