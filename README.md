## YouDBUtils
##### 这是一个“自用”的数据库工具包。
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

2、仅支持MysSQL、SQLite（自用、轻巧）。

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
```
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
```
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

- `package` cn.youyinnn.youdbutils
  - `package` dao
    - `package` annotations
      - `@interface` Scope
      ```
      描述代理的DAO类是单例还是多例的注解
      ```
      - `@interface` Transaction
      ```
      描述代理的DAO类在方法级别或者类级别上是否需要事物管理的注解
      ```
    - `package` model
      - `class` ModelHandler
      ```
      主要是对查询的结果集使用反射去生成对应的Model列表，以及把Model类转为记录存入数据库
      ```
      - `class` ModelMessage
      ```
      静态记录了Model类的一些基本信息，可以全局获取，暂时只记录Model类的field名字列表
      ```
      - `class` ModelResultFactory
      ```
      提供把结果集转换为Model列表的方法
      ```
      - `class` ModelScanner
      ```
      提供对指定包下的类进行扫描的方法 并把其中所有的Model类的信息记录到ModelMassage类中
      ```
    - `class` SqlExecuteHandler
    ```
    执行基本的CRUD的SQL的工具类
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
    - `class` ConnectionContainer
    ```
    保证每条Connection都和当前线程绑定的工具类
    ```
    - `class` YouDruid
    ```
    Druid连接池初始化的核心类
    ```
  - `package` interfaces
    - `@interface` ModelHandler
    - `@interface` SqlExecuteHandler
    - `@interface` YouDao
  - `package` ioc
    - `package` proxy
      - `class` TransactionClassCallBackFilter
      - `class` TransactionInterceptor
      - `class` TransactionMethodCallBackFilter
      - `class` TransactionProxyGenerator
    - `class` DaoScanner
    - `class` IocBean
    - `class` YouDaoIocContainer
  - `package` utils
    - `class` ClassUtils
    - `class` ReflectionUtils
    - `class` SqlStringUtils

核心类说明：



<br>

惯例：


- - -

<span id="示例"/>

##### 示例
