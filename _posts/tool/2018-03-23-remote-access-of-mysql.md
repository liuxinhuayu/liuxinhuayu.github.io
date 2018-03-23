---
layout: post
title: MySQL远程访问
category: 数据库
tags: MySQL
description: 给mysql配置远程访问用户，并授予权限
---

## 增加可访问权限

    格式：grant 权限 on 数据库名.表名 to 用户@登录主机 identified by "用户密码";
    grant select,update,insert,delete on *.* to root@192.168.1.12 identified by "root";

    grant all privileges  on *.* to root@'%' identified by "root";

这样就给账号密码都是root的用户再每一台计算机上登录的权限，其中"%"就是所有的意思

如果这个不行的话直接将%改为你的ip即可

## 开放3306端口
mysql默认使用的是3306端口，为了防止防火墙将其关闭，可以使用下面方式
在CentOS6或者Redhat6下要开启防火墙 打开3306 端口
编辑这个文件vim /etc/sysconfig/iptables
输入

    -A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT   

保存后输入service iptables restart 重启防火墙
或者直接关闭防火墙，但是不建议

   /etc/rc.d/init.d/iptables stop

## MySQL自身设置
ubuntu环境使用apt-get安装的mysql除了上述配置外，还需要修改/etc/mysql/my.cnf文件的bind-address，然后重启mysql

    bind-address = 0.0.0.0

