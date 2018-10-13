---
layout: post
title: shell-日志函数
category: shell
tags: [shell]
description: 记录shell脚本中碰到的一些知识点
---

记录日志的工具log.sh

```
#!/bin/bash
################################################################################
## Copyright:   
## Filename:    log.sh
## Description: 
## Version:     v1.0
## Created:     Friday, 1 19, 2018
################################################################################ 

################################################################################
# Function: logDef
# Description: 记录到日志文件
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: 该函数是最低层日志函数，不会被外部函数直接调用
################################################################################
logDef()
{
    # 调用日志打印函数的函数名称
    local funcName="$1"
    shift

    # 打印的日志级别
    local logLevel="$1"
    shift

    # 外部调用日志打印函数时所在的行号
    local lineNO="$1"
    shift
    
    if [ -d "${g_logPath}" ] ; then
        # 打印时间、日志级别、日志内容、脚本名称、调用日志打印函数的函数、打印时的行号及脚本的进程号
        local logTime="$(date -d today +'%Y-%m-%d %H:%M:%S')"
        printf "[${logTime}] ${logLevel} $* [${g_scriptName}(${funcName}):${lineNO}]($$)\n" \
            >> "${g_logPath}/${g_logFile}" 2>&1
    fi

    return 0
}

################################################################################
# Function: log_error
# Description: 对外部提供的日志打印函数：记录EEROR级别日志到日志文件
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: N/A
################################################################################
log_error()
{
    # FUNCNAME是shell的内置环境变量，是一个数组变量，其中包含了整个调用链上所有
    # 的函数名字，通过该变量取出调用该函数的函数名
    logDef "${FUNCNAME[1]}" "ERROR" "$@"
}

################################################################################
# Function: log_info
# Description: 对外部提供的日志打印函数：记录INFO级别日志到日志文件
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: N/A
################################################################################
log_info()
{
    # FUNCNAME是shell的内置环境变量，是一个数组变量，其中包含了整个调用链上所有
    # 的函数名字，通过该变量取出调用该函数的函数名
    logDef "${FUNCNAME[1]}" "INFO" "$@"
}

################################################################################
# Function: showLog
# Description: 记录日志到文件并显示到屏幕
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: 该函数是低层日志函数，不会被外部函数直接调用
################################################################################
showLog()
{
    # 把日志打印到日志文件。FUNCNAME是shell的内置环境变量，是一个数组变量，其中
    # 包含了整个调用链上所有的函数名字，通过该变量取出调用该函数的函数名
    logDef "${FUNCNAME[2]}" "$@"

    # 如果是EEROR日志级别，则显示在屏幕上要带前缀：ERROR
    if [ "$1" = "ERROR" ]; then
        echo -e "ERROR:$3"
    elif [ "$1" = "WARN" ];then
        echo -e "WARN: $3"
    else
        echo -e "$3"
    fi
}

################################################################################
# Function: showLog_error
# Description: 对外部提供的日志打印函数：记录ERROR级别日志到文件并显示到屏幕
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: N/A
################################################################################
showLog_error()
{
    showLog ERROR "$@"
}

################################################################################
# Function: showLog_info
# Description: 对外部提供的日志打印函数：记录INFO级别日志到文件并显示到屏幕
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: N/A
################################################################################
showLog_info()
{
    showLog INFO "$@"
}

################################################################################
# Function: syslog
# Description: Important operation must record to syslog
# Parameters  : $1 is component name ; $2 is filename ; $3 is status ; $4 is message
# Return: 0 -- success; not 0 -- failure
# Others: N/A
################################################################################
function syslog()
{
    local component=$1
    local filename=$2
    local status=$3
    local message=$4

    if [ "$3" -eq "0" ]; then
        status="success"
    else
        status="failed"
    fi

    which logger >/dev/null 2>&1
    [ "$?" -ne "0" ] && return 0;

    login_user_ip="$(who -m | sed 's/.*(//g;s/)*$//g')"
    execute_user_name="$(whoami)"
    logger -p local0.notice -i "${component};[${filename}];${status};${login_user_ip};${execute_user_name};${message}"
    return 0
}

```

在其他脚步中引用该脚步：

```
############声明环境变量########################################################
declare g_curPath=""                                # 当前脚本所在的目录
declare g_logPath="/var/log/test"                   # 日志路径
declare g_logFile="test.log"                      # 日志文件
################################################################################
# Function: init_path
# Description: 初始化脚本所在的目录及脚本名
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: N/A
################################################################################
init_path()
{
    cd "$(dirname "${BASH_SOURCE-$0}")"
    g_curPath="${PWD}"
    g_scriptName="$(basename "${BASH_SOURCE-$0}")"
    g_setup_tool_package_home="$(dirname "${g_curPath}")"
    cd - >/dev/null
}

################################################################################
# Function: init_log
# Description: 初始化preset日志文件
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: N/A
################################################################################
init_log()
{
    [ -d ${g_logPath} ] || mkdir -p ${g_logPath}
    [ -f ${g_logPath}/${g_logFile} ] || touch "${g_logPath}/${g_logFile}"
    chmod 600 "${g_logPath}/${g_logFile}"
}

################################################################################
# Function: main
# Description: 主函数
# Parameter:
#   input:
#   N/A
#   output:
#   N/A
# Return: 0 -- success; not 0 -- failure
# Others: N/A
################################################################################
main()
{  
    # 如果不是root用户，则退出
    if [ $(id -u) -ne 0 ] ; then
        echo  "ERROR: Only super user can execute this script."
        return 1
    fi
    
    showLog_info "${LINENO}" "start to preSet"
    
    # 如果用户环境上不存在运行用户，则创建运行用户，如果已存在，则判断是否属于${RUN_USER_GROUP}组，如果不属于则报错退出
    id -u ${RUN_USER} > /dev/null 2>&1
    if [ $? -ne 0 ] ; then
        
## ...这里省略了中间部分操作

    showLog_info "${LINENO}" "success to finish test."
    return 0
}

# ---------------------------------------------------------------------------- #
#                        获取当前路径,初始化日志文件                           #
# ---------------------------------------------------------------------------- #
init_path
cd "${g_curPath}"
init_log

# ---------------------------------------------------------------------------- #
#                                   导入头文件                                 #
# ---------------------------------------------------------------------------- #
. "${g_curPath}/func/log.sh" || { echo "[${g_scriptName}:${LINENO}] ERROR: Failed to load ${g_curPath}/log.sh."; exit 1;}


# ---------------------------------------------------------------------------- #
#                                 脚本开始运行                                 #
# ---------------------------------------------------------------------------- #
main "$@"
ret=$?

#在操作系统日志中记录审计日志
syslog "manager" "test.sh" "$ret" "User root do some test"
exit $ret



```
