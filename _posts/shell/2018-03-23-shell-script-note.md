---
layout: post
title: shell脚本
category: shell
tags: [shell]
description: 记录shell脚本中碰到的一些知识点
---

### shell参数

| 变量 | 描述 |
| ---  | ---  |
|$0 | 当前脚本的名字  |
|$n | 传递给脚本或者函数的参数，n是几，代表第几个参数|
|$# | 输入参数的个数|
|$* | 输入的所有参数的列表，使用双引号括起来时，所有参数当做一个整体|
|$@ | 输入的所有参数，使用双引号时，每个参数一个个体|
|$? | 上一个命令的执行结果，正确时返回0，不正确时返回非0|
|$$ | 当前进程的pid|

### set使用

碰到错误时退出整个脚本

```
set -e
```

开启调试

```
set -x
```

### 记录每天磁盘使用情况

```
#!/bin/bash
d=`date +%F` #等价于d=`date +%Y-%m-%d`
logfile=$d.log
df -h > $logfile
```

### 测试多次执行某个命令的结果

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

### 删除包含某个进程关键字的进程

```
ps -ef | grep XXX | grep -v grep | cut -c 9-15 | xargs kill -9
```

### 循环读取文件内容

使用while实现

```
cat test.txt |while read line  
do  
  echo $line;  
done  
```

使用for实现，IFS为行内定义的间隔符合,如果不指定以'\n'做分割，则空格、tab都会分行打印

```
SAVEIFS=$IFS  
IFS=$(echo -en "\n")  
for line in $(cat test.txt)  
do  
  echo  $line;  
done  
IFS=$SAVEIFS
```

### 文本内容搜索

    grep aaa * 

### 文件夹操作

    查看文件夹大小   du -h --max-depth=1 /home/ys
    查看驱动器空间   df -h 

### 压缩命令

    tar zxvf aaa.tar.gz
    tar zcvf aaa.tar.gz aaa

### 登陆到其他用户

    login

### 查看端口的占用

    lsof -i:8087  查看8087端口的使用

### 查看当前时间

    date       时间
    date +%s   时间戳
	date +%F   以指定格式显示时间
    date -d "2010-07-20 10:25:30" +%s  指定时间时间戳
    date -d "@1279592730"    时间戳转时间
    date -d "1970-01-01 14781 days" "+%Y/%m/%d %H:%M:%S" 

### 查看进程内存使用情况

    top -d 1 -p pid [,pid ...]
    pmap pid 
    ps aux|grep process_name
    查看/proc/process_id/文件夹下的status文件

### 查看Linux内核版本或发布版本

    lsb_release -a
    uname -a

### 一句话实现一个HTTP服务，把当前文件夹作为根目录

    python -m SimpleHTTPServer

### 查看本地网络服务活动状态

    lsof -i

### 查看自己的外网ip

    curl -i https://ip.cn

### 下载整个网站

    wget --random-wait -r -p -e robots=off -U mozilla http://www.example.com

### 后台运行一段不中止的程序，并可随时查看它的状态

    screen -d -m -S some_ name ping my_router

### 查看进程执行的时间 

    ps -A -opid,stime,etime,args | grep python

### 创建守护进程

    nohup python /var/www/a.py &

### 查看当前文件夹下文件（文件夹）大小

    du -h --max-depth=1 .

### 查看所有磁盘大小

    df -h

### 诊断网络

    mtr 
    ping
    traceroute
    dig

### 列出本机监听的端口号

    netstat –tlnp
    netstat -anop

### 在远程机器上运行一段脚本

    ssh user@server bash < /path/to/local/script.sh

### 端口扫描

    nc -z -v -n 127.0.0.1 20-100

### 负载测试，30秒内向Google发起20个并发连接

    siege -c20 www.google.co.uk -b -t30s

### 封禁一个ip的访问

    iptables -I INPUT -s 211.1.0.0 -j DROP

### 连续不中断执行

用`;`可以让多个命令连续知行，中间出现错误并不会中断后面命令，如

    mkdir test; mkdir test; rmdir test;

虽然第二条指令会报错，但是不会影响后面的指令，最后test目录不存在

### 出错停止后面指令

用`&&`分割的命令，如果没有错误会一直执行下去，出现错误立即中止，如

    mkdir test && mkdir test && rmdir test

这回在第二个指令处就中止了

### 一次正确即停止

用`||`分割的命令，如果有错误就一直执行下去，直到一次正确立即中止，如

    mkdir test || mkdir test || rmdir test
    mkdir test || mkdir test || rmdir test || mkdir test

第一次执行第一条指令就正确，后面的不执行

第二次执行前两条都错误，直到最后一条才正确，最后一条不再执行

