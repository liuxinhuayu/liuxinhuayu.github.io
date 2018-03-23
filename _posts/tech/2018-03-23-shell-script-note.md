---
layout: post
title: shell脚本
category: 工具
tags: [shell]
description: 记录shell脚本中碰到的一些知识点
---

##shell的参数
变量|描述
-----|-----
$0|当前脚本的名字
$n|传递给脚本或者函数的参数，n是几，代表第几个参数
$#|输入参数的个数
$*|输入的所有参数的列表，使用双引号括起来时，所有参数当做一个整体
$@|输入的所有参数，使用双引号时，每个参数一个个体
$?|上一个命令的执行结果，正确时返回0，不正确时返回非0
$$|当前进程的pid

##set使用
碰到错误时退出整个脚本

```set -e```

开启调试

```set -x```


##记录每天磁盘使用情况

```
#!/bin/bash
d=`date +%F` #等价于d=`date +%Y-%m-%d`
logfile=$d.log
df -h > $logfile
```

## 测试多次执行某个命令的结果

```
function with_retry() {
    local retry_limit=$1
    local cmd=("${@:2}")  #表示从第2个参数开始后面所有的参数

    local retry_count=0
    local rc=0

    until [[ ${retry_count} -ge ${retry_limit} ]]; do
        ((retry_count+=1))
        "${cmd[@]}" && rc=0 || rc=$?
    if [[ ${rc} == 0 ]]; then
        return 0
    fi
        sleep 3
    done

    if [[ ${rc} -ne 0 ]]; then
    {
        logger_Error "exec ${cmd[@]} failed after retry ${retry_limit} times"
        exit ${rc}
    }
    fi
}
#使用示例
with_retry 3 curl www.baidu.com
```