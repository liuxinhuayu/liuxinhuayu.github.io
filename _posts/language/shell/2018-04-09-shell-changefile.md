---
layout: post
title: shell-文件编码转换
category: 编程语言
tags: [shell]
description: 记录shell脚本中碰到的一些知识点
---

查看别人的脚步时，发现里面的注释显示乱码，网上查了下转编码的材料，找到一个脚步，改动了一下，供参考

**linux环境中的ISO-8859，使用GB2312转码**

```
#!/bin/bash
set -x
# 检查输入是否两个参数，如果不是，则退出
[ "$#" != 2 ] && echo "Usage: `basename $0` [to_encoding] [file]" && exit -1

# 检查输入的第二个参数是否是文件，不是文件，则退出
[ ! -f $2 ] && echo "the second argument should be a regular file " && exit 1
file=$2

# 检查要转到的编码是否是系统支持的编码，通过 iconv -l 查看所有支持的编码
iconv -l | grep -q $1
[ $? -ne 0 ] && echo "iconv not support such encoding: $1" && exit -1
to_encoding=$1

# 检查文件是否是text文件，非text文件无法转码
file_type=`file $file | grep "text"`
[ $? -ne 0 ] && echo "$file is not a text file" && exit -1

# 获取原始文件编码格式
if [[ $file_type == *executable ]];then
   from_encoding=`echo $file_type | sed s/text.*$//g | sed s/^.*script,//g | sed s/Unicode//g`
else
   from_encoding=`echo $file_type | cut -d" " -f 2`
fi

# 检查是否支持原始编码集
from_encoding=`iconv -l | grep $from_encoding`
[ $? -ne 0 ] && echo "iconv not support the old encoding: $from_encoding"
from_encoding=`echo $from_encoding | cut -d"/" -f 1`

# convert the file from from_encoding to to_encoding，使用-o可指定转化后的文件名
iconv -f $from_encoding -t $to_encoding $file



```

