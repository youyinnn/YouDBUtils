## YouDBUtils
[![Travis](https://img.shields.io/badge/version-1.2.0-green.svg)]()
[![Travis](https://img.shields.io/badge/druid-1.1.5-brightgreen.svg)]()
[![Travis](https://img.shields.io/badge/cglibnodep-3.2.5-brightgreen.svg)]()
##### 这是一个“自用”的数据库工具包。
- - -
TODO:
- YouController注释,配合Service的自动装配
- 事务的传播行为可选功能待实现
- 其他重要监控的配置
- 启动初始化信息的输出
- log4j2的引入

- - -
使用
maven:
```xml
        <dependency>
            <groupId>com.github.youyinnn</groupId>
            <artifactId>you-db-utils</artifactId>
            <version>1.2.0</version>
        </dependency>
```
- - -

### 索引
- [介绍](#介绍)
- [架构](#架构)
  - [依赖](#依赖)
  - [架构、惯例](#架构惯例)
- [示例](#示例)

- - -

<span id="介绍"/>

#### 介绍

1、以alibaba的Druid为基础（拒绝DBCP、C3P0），支持代码控制Druid的一些功能，拒绝配置。

2、仅支持MySQL、SQLite（自用、轻巧）。

3、类似spring的控制反转，主要用于生成指定包下的DAO类的代理类（依赖cglib），让其具有事务特性，Ioc容器中以类名获取。

4、提供CRUD的基本SqlExecutor。

5、提供直接查询输出Model类的列表，或者直接输入Model类插入到数据库。

> *需要特别声明的是，因为这个工具类是自用的工具类，所以使用了本人所习惯的惯例来减少配置量，所以惯例部分一定要看仔细。*

- - -

<span id="架构"/>

#### 架构

<span id="依赖"/>

##### 依赖

必须依赖：cglib、druid
```xml
<dependencies>
    <dependency>
        <groupId>cglib</groupId>
        <artifactId>cglib-nodep</artifactId>
        <version>3.2.5</version>
    </dependency>

    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>RELEASE</version>
    </dependency>
</dependencies>
```
可选依赖：sqlite、mysql、lo4j2
```xml
<dependencies>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.20.0</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>6.0.5</version>
    </dependency>

    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.9.0</version>
    </dependency>

    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.9.0</version>
    </dependency>
</dependencies>
```

- - -

<span id="架构惯例"/>

##### 架构、惯例

首先来说架构：

架构总览：

- `package` com.youyinnn.youdbutils
  - `package` dao
    - `package` model
      - `class` ModelHandler
      ```
      主要是对查询的结果集使用反射去生成对应的Model列表，以及把Model类转为记录存入数据库
      ```
      - `class` ModelTableMessage
      ```
      静态记录了Model类的一些基本信息，可以全局获取，暂时只记录Model类的field名字列表
      ```
      - `class` ModelResultFactory
      ```
      提供把结果集转换为Model列表的方法
      ```
      - `class` ModelTableScanner
      ```
      提供对指定包下的类进行扫描的方法 并把其中所有的Model类的信息记录到ModelMassage类中
      ```
    - `class` SqlExecutor
    ```
    执行基本的CRUD的SQL的工具类
    ```
    - `class` YouDao
    ```
    所有dao类都需要继承的父类 继承的同时指定Model类泛型 提供默认的针对泛型的ModelResultFactory 减少对于Model类性相关的类的创建的考虑
    ```
    - `class` YouDaoContainer
    ```
    YouDao的容器 这保证了当前程序里 所有的YouDao对象都是单例的 并且从任何地方都可以获取
    ```
  - `package` druid
    - `package` exceptions
      - `class` NoDataSourceInitException
      ```
      描述数据源没有初始化时的异常
      ```
    - `package` filter
      - `class` YouLog4j2Filter
      ```
      对druid里Log4j2Filter的封装，代码控制功能开关
      ```
      - `class` YouStatFilter
      ```
      对druid里StatFilter的封装，代码控制功能开关
      ```
    - `class` ThreadLocalPropContainer
    ```
    保证每条Connection都和当前线程绑定的工具类 同时保证线程上的唯一的事务都使用同一个flag值来判断回滚操作
    ```
    - `class` YouDruid
    ```
    Druid连接池初始化的核心类
    ```
  - `package` interfaces
    - `@interface` ModelHandler
    ```
    Model处理器方法接口
    ```
    - `@interface` SqlExecutor
    ```
    SQL执行器方法接口
    ```
  - `package` ioc
    - `package` annotations
      - `@interface` Autowired
      ```
      描述Service中的属性需要自动装配的注解
      ```
      - `@interface` Scope
      ```
      描述代理的DAO类是单例还是多例的注解
      ```
      - `@interface` Transaction
      ```
      描述代理的Service类在方法级别或者类级别上是否需要事物管理的注解
      ```
      - `@interface` YouService
      ```
      描述Service类
      ```
    - `package` proxy
      - `class` TransactionClassCallBackFilter
      ```
      类级别的事务代理过滤器
      ```
      - `class` TransactionInterceptor
      ```
      事务代理过程
      ```
      - `class` TransactionMethodCallBackFilter
      ```
      方法级别的事务代理过滤器
      ```
      - `class` TransactionProxyGenerator
      ```
      代理对象的生成器
      ```
    - `class` ServiceIocBean
    ```
    Service的Ioc类的信息
    ```
    - `class` ServiceScanner
    ```
    Service类的扫描器
    ```
    - `class` YouServiceIocContainer
    ```
    存储代理Service类的Ioc容器
    ```
  - `package` utils
    - `class` ClassUtils
    ```
    提供指定路径下的类的迭代扫描工具类
    ```
    - `class` ReflectionUtils
    ```
    提供反射所需方法的工具类
    ```
    - `class` SqlStringUtils
    ```
    生成Sql语句的工具类
    ```
  - `class` YouDbManager
  ```
  集合整个工具类所有对外核心功能的出口方法
  ```

核心类说明：



<br>

惯例：


- - -

<span id="示例"/>

##### 示例
