---
layout: post
title: python-pip、内置对象、运算符
category: 编程语言
tags: python
description: 
---


# Flask系列：HelloWord

## 创建virtualenv环境
使用pycharm免费版创建新工程，如图

![flask1](/assets/img/2018-05-27-flask-1.jpg)

## 安装第三方库flask

在项目的settings中，通过解释器安装flask

![flask1](/assets/img/2018-05-27-flask-2.jpg)

## 编写HelloWord

如图，static用于存放静态页面，为空目录，templates存放模板，为空目录，run.py为项目启动脚本

![flask1](/assets/img/2018-05-27-flask-3.jpg)

在init.py中初始化flask对象

```
from flask import Flask

app = Flask(__name__)
from app import views
```
在views.py中定义对视图函数，针对路由/给出返回值

```
from app import app


@app.route('/')
@app.route('/index')
def index():
    return "Hello, World!"
```

使用python命令启动run.py后，访问 http://localhost:5000，可查看到返回值HelloWorld
