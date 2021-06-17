# ilearn


<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [一、项目运行](#一-项目运行)
- [二、基本功能](#二-基本功能)
  - [1. 匿名用户功能列表](#1-匿名用户功能列表)
  - [2. 认证用户功能列表](#2-认证用户功能列表)
  - [3. 用户的功能用例图](#3-用户的功能用例图)
- [三、数据库设计](#三-数据库设计)
  - [E-R 图](#e-r-图)
- [四、后端实现](#四-后端实现)
  - [1. 用户](#1-用户)
  - [2. 课程](#2-课程)
  - [3. 文件](#3-文件)
  - [4. 讨论](#4-讨论)
  - [5. 通知](#5-通知)
  - [6. 考试](#6-考试)
  - [7. 认证和授权](#7-认证和授权)

<!-- /code_chunk_output -->


## 一、项目运行

- 运行环境
    - JDK 1.8
    - MySQL 8.0 / Redis 5 以上
- 数据库/文件资源
    - 微云（需要登陆）
        - 链接：https://share.weiyun.com/r3zXOJBN 密码：zptqpv
    - 天翼云盘（需要登陆）
        - https://cloud.189.cn/t/YNbeAjr6n67v (访问码:wd08)
- 用户帐号
    - 管理员
        - admin@admin.com
        - 123456
    - 其他用户
        - user1@qq.com 到 user11@qq.com
        - 123456

1. 修改配置
    - <code>app.filesystem.resourceLocation</code>：文件资源的目录
    - <code>spring.application.datasource.username</code>/<code>password</code>：数据库连接帐号
2. 启动前后端项目
3. 前端启动后，自动弹出页面 http://localhost:8080/

## 二、基本功能

可以按是否登录将用户分为两类。

### 1. 匿名用户功能列表

| 功能名称     | 说明                                           |
| ------------ | ---------------------------------------------- |
| 用户注册     | 用户根据邮箱注册帐号。                         |
| 用户登录     | 用户根据邮箱和密码登录系统。                   |
| 浏览课程列表 | 在课程页，查看所有课程                         |
| 查看课程详情 | 在课程详情页，查看课程的介绍、公告和参考链接。 |
| 查看课程章节 | 浏览各章节的多媒体视频                         |
| 查看课件     | 查看和下载课件                                 |
| 查看讨论     | 匿名用户可以查看课程的讨论                     |

匿名用户是未登录的用户，可以注册、登录和查看课程相关信息和讨论。

### 2. 认证用户功能列表

| 功能名称     | 说明                                                         |
| ------------ | ------------------------------------------------------------ |
| 编辑个人信息 | 上传头像、修改昵称、性别、生日和个人简介。                   |
| 编辑帐号信息 | 用户可以绑定和解绑邮箱，以及修改密码。                       |
| 查看学习记录 | 查看在学的课程，以及学习的进度。                             |
| 更新学习记录 | 自动记录已经学习过的小节。                                   |
| 参加讨论     | 发布讨论和进行回复（需要「参与讨论」角色），以及点赞。         |
| 查看通知     | 其他用户回复后，能收到一个通知。                             |
| 加入考试     | 用户可以浏览加入的考试以及加入考试。                         |
| 添加课程     | 用户可以添加课程，编辑需要展示的信息，上传章节视频和课件。（需要「发布课程」角色） |
| 添加考试     | 可以添加考试信息（需要「发起考试」角色）                       |
| 权限管理     | 可以对用户的权限进行修改（需要「管理员」角色）                 |

认证用户是已经登录的用户，可以对系统资源的状态进行修改，登录以后系统就可以识别用户信息并持久化用户的操作。

### 3. 用户的功能用例图

![use-case-diagram-anonymous][file:use-case-diagram-anonymous.png]

![use-case-diagram-authenticated][file:use-case-diagram-authenticated.png]

## 三、数据库设计

### E-R 图

![E-R-diagram.png][file:E-R-diagram.png]

## 四、后端实现

后端开发使用的是 Spring Boot 框架，可以简化集成的操作和部署的流程。系统使用了 SSM 的三层架构，接收消息使用 Query 封装请求数据，分别使用 DO、DTO、VO
封装数据访问层、业务层和控制层向上层传输的数据。这些类型的映射和转换是通过 MapStruct 框架实现的，避免过多的影响效率。

系统中使用了通用的返回值类型、通用异常类型和全局异常处理器，会对返回值和异常做统一处理。

### 1. 用户

用户注册时，会根据一个随机颜色，生成一个 svg 格式的头像，并检查注册邮箱是否重复。当用户对邮箱解除绑定时，会检查是否是主邮箱，避免删除主邮箱记录。用户查询邮箱列表时，会对邮箱进行排序，将主邮箱作为第一个元素。

### 2. 课程

添加课程时，会生成一个该课程的课件组记录，添加课件项时就是对这一组进行添加。

当查询课程列表或是根据 ID 查询时，会检查课程是否禁用，如果已禁用，只有当前用户是课程的创建者时才会返回课程数据。当用户对课程和学习资源进行操作时，如果当前用户不是课程的创建者，会拒绝操作。当删除课程章时，会将数据这一章的课程节一并删除。

当用户查询课程章时，还会查询属于这些章的课程节；如果已经登录，还会连接查询每一节的学习记录。当用户对学习记录进行分页查询时，会先查询用户学习过的且未禁用的课程总数；然后会查询其中一组课程的最近的学习记录，按时间降序排序；最后查询这些学习记录对应的节、课程、课程节数和开始学习数。

### 3. 文件

系统中使用了 SpringMVC 的资源处理器功能将「archive」路径映射到本地文件系统，浏览文件文件时可以使用；文件下载使用了 Controller 处理请求，路径是「download」。文件下载时的文件名称是在 URL
参数中指定的，发送文件流时设置了「Content-Disposition」响应头指定文件名，并且解决了文件名乱码的问题。

文件上传时为防止文件名重复，使用了序列号作为新的文件名。序列号存在一个表中，每取一次都会自增。

### 4. 讨论

用户发布讨论贴时，会开启一个事务，插入发言和讨论贴记录。用户查询讨论贴时，会根据课程 ID 查询一组数据，如果用户已登录，还会根据这些讨论贴发言的 ID 记录查询用户已点赞的发言。前端在处理时，创建一个点赞的集合
Set，遍历讨论贴查看是否点赞。

用户评论时，会检查这一评论是哪一级。如果是一级，则寻找它回复的讨论贴；如果是二级，先找到它回复的评论，再寻找讨论贴；如果是二级以上，先找到上一级评论，再根据 rootId
字段找到一级评论，最后找到讨论贴。然后如果这一评论是一级评论，会将讨论贴的一级评论数和评论总数加 1；如果不是则只增加后者。最后插入发言记录、评论记录、接收回复记录和回复未读记录。整个过程是在事务中进行的。

用户查询评论时，会查询一组一级评论，对每个一级评论会根据 rootId 查询一组二级以上的评论及其总数。然后会查询用户在这些评论中的点赞列表。

### 5. 通知

用户获取通知列表时，会先获取当前最新通知的 ID，之后的操作是在 ID 的基础上进行的。分页查询时，按提供的通知 ID 查询一组数据，前端会将下一次查询使用的 ID 更新为这一组通知的最小 ID。删除未读通知时，根据用户和提供的通知 ID
删除记录，新到达的通知不会被删除。

### 6. 考试

用户添加考试时，会添加考试记录，根据考试 ID 生成 Base34 编码作为考试码。考试码字段添加了索引，可以通过考试码查询考试信息。用户加入考试时，会先判断考试是否存在、是否已结束、用户是否已加入该考试，最后添加用户考试记录。

### 7. 认证和授权

用户登录时，会根据邮箱查询用户的密码和角色，如果密码匹配，根据用户名、权限和过期时间等信息使用 HS512 签名算法生成 Token，并设置到 token 响应头中。用户登录后，会将用户的信息存放到 Redis 中，使用的 key
是由用户的邮箱生成的，收到请求时，可以使用「SecurityContextHolder」类获取当前用户的邮箱，再查询当前用户。

系统的用户鉴权使用的是 Spring Security 框架，会拦截请求，对设置的路径进行检查，可以设置匿名权限、认证权限、需要的角色或是全部放行。当用户未认证或授权时访问对应的路径，就返回一个 403 状态码；当用户登录失效时，会返回
401 状态码，前端就清除 Token 内容。


[file:use-case-diagram-anonymous.png]: .github/readme/use-case-diagram-anonymous.png

[file:use-case-diagram-authenticated.png]: .github/readme/use-case-diagram-authenticated.png

[file:E-R-diagram.png]: .github/readme/E-R-diagram.png

