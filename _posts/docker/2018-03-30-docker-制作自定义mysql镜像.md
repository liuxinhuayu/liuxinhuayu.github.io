---
layout: post
title: docker-制作自定义mysql镜像
category: docker
tags: [docker,mysql]
description: 记录shell脚本中碰到的一些知识点
---

#docker-制作自定义mysql镜像

- 需求一：运行初始SQL脚本
- 需求二：修改mysql配置，忽略大小写
- 需求三：更改默认字符集为UTF8
- 需求四：允许root远程访问

## 封装的dockerfile目录

```
root@ubuntu14:/opt/BI/mysql# tree /opt/BI/mysql/
/opt/BI/mysql/
├── dockerfile
├── install_data.sh
├── mysqld.cnf
├── pure.sql
└── vbap.sql
```

其中dockerfile内容为：

```
root@ubuntu14:/opt/BI/mysql# cat dockerfile
FROM mysql
COPY ./install_data.sh /docker-entrypoint-initdb.d/install_data.sh
COPY ./*.sql /opt/
COPY ./mysqld.cnf /etc/mysql/mysql.conf.d/mysqld.cnf
```

其中install_data.sh内容为：

```
 #!/bin/bash
 mysql -uroot -p$MYSQL_ROOT_PASSWORD <<EOF
 source /opt/pure.sql
 source /opt/vbap.sql
```

两个SQL文件为要做初始化用的SQL文件，注意SQL前部分要创建自定义的数据库

```
 CREATE DATABASE IF NOT EXISTS pure DEFAULT CHARACTER SET utf8;
 USE pure;
 SET NAMES utf8;
```

上面的部分就能够实现需求一，实现在容器启动时，创建自定义的数据库和插入数据等操作

修改mysql配置实现字符集统一为utf8，需求将自定义的mysqld.cnf文件导入镜像

```
 # set client default character
 [mysql]
 default-character-set=utf8
 [mysqld]
 pid-file = /var/run/mysqld/mysqld.pid
 socket = /var/run/mysqld/mysqld.sock
 datadir = /var/lib/mysql
 log-error = /var/log/mysql/error.log
 # By default we only accept connections from localhost
 bind-address = 0.0.0.0
 # Disabling symbolic-links is recommended to prevent assorted security risks
 symbolic-links=0
 # set 1 means Ignore capital letters and lowercase letters
 lower_case_table_names=1
 # set client default character
 character-set-server = utf8
```

## 打包生成镜像

执行docker build命令,注意后面的点，表示以当前目录作为上下文内容

```
 docker build -t [自定义镜像名称]:[自定义标签] .
```

## 导出镜像

将镜像打成tar包，方便在其他环境中使用

```
 docker save [自定义镜像名称]:[自定义标签] > [自定义镜像名称]-[自定义标签].tar
```
或者

```
 docker save [自定义镜像名称]:[自定义标签] | gzip > [自定义镜像名称]-[自定义标签].tar.gz
```

## 导入镜像

使用docker load命令

```
 docker load -i [自定义镜像名称]-[自定义标签].tar.gz
```
组合使用

```
 docker save <镜像名> | bzip2 | pv | ssh <用户名>@<主机名> 'cat | docker load'
```

## 检查mysql属性

```
 show variables like "%charac%";
```
