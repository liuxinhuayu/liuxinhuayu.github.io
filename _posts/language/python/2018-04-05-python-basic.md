---
layout: post
title: python-基础
category: 编程语言
tags: python
description: 
---

# python基础

## pip管理扩展库
1、常用命令

```
pip download package[==version]
pip freeze [> requirements.txt]
pip list
pip install package[==version]
pip install package.whl 
pip install -r requirements.txt
pip install --upgrade package
pip uninstall package[==version]
```
2、使用配置文件配置更改下载源

```
[global]
trusted-host=mirrors.aliyun.com
index-url=http://mirrors.aliyun.com/pypi/simple/
```
配置文件放置位置

```
Linux下: ~/.pip/pip.conf
windows下:用户文件夹下\pip\pip.ini
```

3、使用命令行临时改变pip源

```
pip install -i <mirror> --trusted-host <mirrorhost> package
例如
pip install -i http://pypi.douban.com/simple/  --trusted-host pypi.douban.com  pandas
```
国内源:

* http://pypi.v2ex.com/simple/
* http://pypi.douban.com/simple/
* http://mirrors.aliyun.com/pypi/simple/

## 内置对象

* python严格区分大小写，
* 变量不需要提前声明，直接赋值使用
* python动态类型语言，变量的类型可随时变化

对象类型：数字(int,float,complex)、字符串(str)、字节串(bytes)、列表(list)、字典(dict)、元组(tuple)、集合(set、frozenset)、布尔型(bool)、空类型(NoneType)、异常、文件、其他迭代对象、编程单元

* 字符串以r开头包括，表示忽略转义的原始字符串
* 字符串通过encode方法可以转成字节串，字节串通过decode方法转成字符串
* 字节串以b引导
* 元组如果只有一个元素时，逗号不可省略
* 集合内所有元素都不可重复，set元素可变，frozenset元素不可变
* 布尔型只有True和False两个值，区分大小写
* 生成器对象、range对象、zip对象、enumerate对象、map对象、filter对象具有惰性求值的特点，每当使用时才取到值，用完就不再在对象里

```
>>> r = zip("abc","12")
>>> ("a","1") in r
True
>>> ("a","1") in r
False
```
* 不可变对象：数字、字符串、元组、frozenset
* 可变对象：列表、集合、字典
* 查看所有关键字，使用keyword.kwlist
* 查看所有内置对象 dir(__builtins__)
* 使用id(x)查看x的内存地址

### 字符串格式化表示


