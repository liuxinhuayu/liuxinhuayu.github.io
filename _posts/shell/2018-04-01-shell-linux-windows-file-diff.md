---
layout: post
title: shell-回车换行
category: shell
tags: [shell]
description: 记录shell脚本中碰到的一些知识点
---

“回车”（carriage return）：将打印起始位置换到左边界

“换行”（line feed）：光标下下移一行

* Unix系统里，每行结尾只有“<换行>”，即“\n”;

* Windows系统里面，每行结尾是“<换行><回车>”，即“\n\r”；

* Mac系统里，每行结尾是“<回车>”

直接后果是:

Unix/Mac系统下的文件在Windows里打开的话，所有文字会变成一行；

Windows里的文件在Unix/Mac下打开的话，在每行的结尾可能会多出一个^M符号

windows编辑的文件拿到linux环境需要做的操作：

sed -i s/^M//g 源文件
或者使用dos2unix命令

```
dos2unix [-hkqV] [-c convmode] [-o file ...] [-n infile outfile ...]
 
 1. -k：保持输出文件的日期不变 
 2. -q：安静模式，不提示任何警告信息
 3. -V：查看版本
 4. -c：转换模式，模式有：ASCII, 7bit, ISO, Mac, 默认是：ASCII
 5. -o：写入到源文件
 6. -n：写入到新文件
 
find public/components/ -name "*.py" | xargs dos2unix

```

