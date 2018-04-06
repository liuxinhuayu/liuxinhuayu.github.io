---
layout: post
title: python-pip、内置对象、运算符
category: 编程语言
tags: python
description: 
---

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
* 查看所有内置对象 `dir(__builtins__)`
* 使用id(x)查看x的内存地址
* 使用ord('A')求字符A对应的unicode码，chr(65)返回unicode码65对应的字符

## 字符串

打印字符串内容，使用print()函数
```
print(*,sep=' ',end='\n')  # 默认print的间隔是空格，结尾是换行符
```

windows中换行默认是'\r\n'，而linux文件中默认是'\n'，因此在windows中编辑的文件，上传到linux环境时，需要对换行做替换，使用命令

```
sed ‘s/^M//’ filename > newfile  #注意^M的输入方式是Ctrl+V,Ctrl+M
```
字符串的内置函数

```
>>> "abcde".capitalize()  #实现首字母大写
'Abcde'
>>> "abacad".count('a')  #统计字符串出现的次数
3
>>> "1a2b3d".find("2")  # 返回字符第一次出现的索引值
2
>>> "123".join("abc")  #字符串插入
'a123b123c'
```

更多字符串的内置函数，通过dir命令查看，

```
>>> dir(str)
['__add__', '__class__', '__contains__', '__delattr__', '__dir__', '__doc__', '_
_eq__', '__format__', '__ge__', '__getattribute__', '__getitem__', '__getnewargs
__', '__gt__', '__hash__', '__init__', '__init_subclass__', '__iter__', '__le__'
, '__len__', '__lt__', '__mod__', '__mul__', '__ne__', '__new__', '__reduce__',
'__reduce_ex__', '__repr__', '__rmod__', '__rmul__', '__setattr__', '__sizeof__'
, '__str__', '__subclasshook__', 'capitalize', 'casefold', 'center', 'count', 'e
ncode', 'endswith', 'expandtabs', 'find', 'format', 'format_map', 'index', 'isal
num', 'isalpha', 'isdecimal', 'isdigit', 'isidentifier', 'islower', 'isnumeric',
 'isprintable', 'isspace', 'istitle', 'isupper', 'join', 'ljust', 'lower', 'lstr
ip', 'maketrans', 'partition', 'replace', 'rfind', 'rindex', 'rjust', 'rpartitio
n', 'rsplit', 'rstrip', 'split', 'splitlines', 'startswith', 'strip', 'swapcase'
, 'title', 'translate', 'upper', 'zfill']
>>>
>>> help(str.rfind)
Help on method_descriptor:

rfind(...)
    S.rfind(sub[, start[, end]]) -> int

    Return the highest index in S where substring sub is found,
    such that sub is contained within S[start:end].  Optional
    arguments start and end are interpreted as in slice notation.

    Return -1 on failure.

>>>

```

## python运算符

![python](/assets/img/2018-04-06-python.jpg)

* +运算符除了算术运算外，还支持同类型对象拼接

```
>>> [1,2,3]+["a","b"]
[1, 2, 3, 'a', 'b']
>>> "A"+1
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: must be str, not int
>>> True+3
4
>>> False+3
3
```

* *运算符不仅用于乘法，还用于重复对象

```
>>> "a"*10
'aaaaaaaaaa'
>>> [1,2,3]*3
[1, 2, 3, 1, 2, 3, 1, 2, 3]
>>> (1,2,3)*3
(1, 2, 3, 1, 2, 3, 1, 2, 3)
```

