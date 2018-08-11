---
layout: post
title: python-requests多进程下载文件
category: python
tags: python
description: 使用进程池下载
---

如下代码实现的是登录某网站后，调用get接口下载某个文件，启动进程池Pool

```
import requests
import json
import time
from multiprocessing import Pool
import multiprocessing

baseurl = "http://8.25.3.33:28090"
userName = "xxxxx"
password = "xxxxxx"
# 进程数量
pnum = 3
# 总任务队列数量
tnum = 10

def login():
    s = requests.Session()
    url1 = baseurl + "/appstore/user/login"
    formdata1 = {
        "userName": userName,
        "password": password,
    }
    header = {"Content-type": "application/json;charset=UTF-8"}
    s.headers.update(header)
    r1 = s.post(url1, data=json.dumps(formdata1))
    return s


def downloadapp(i):
    name = multiprocessing.current_process().name
    filename = "appstore" + str(i)
    s = login()
    speed = 0.0
    url4 = baseurl + "/appstore/userDownload/1"
    r4 = s.get(url4, stream=True)
    print(r4.headers.items())
    start_time = time.time()
    offset = 1
    with open(filename, "wb") as code:
        for chunk in r4.iter_content(chunk_size=1024*1024):
            if chunk:
                # dural_time = time.time() - start_time
                code.write(chunk)
                offset = offset + len(chunk)
                # print("offset:",offset)
                # try:
                #     speed = offset / 1024 / 1024 / dural_time
                # except ZeroDivisionError as err:
                #     pass
                    # print("time zero",err)
                # print("threadname: {0}, filename : {1}, speed: {2:.2f}M/s".format(name, filename, speed))
    endtime = time.time()
    totaltime = endtime - start_time
    totalsize = offset / 1024 / 1024
    print("{name} : total size {totalsize}MB".format(name=name, totalsize=totalsize))
    print("{name} : total cost time {totaltime}".format(name=name, totaltime=totaltime))
    totalspeed = offset / 1024 / 1024 / totaltime
    print("threadname: {0}, filename : {1}, speed: {2:.2f}M/s".format(name, filename, totalspeed))
    s.close()


if __name__ == '__main__':
    # set the processes max number 3
    pool = Pool(processes=pnum)
    # i 总的队列数量
    for i in range(1, tnum+1):
        result = pool.apply_async(downloadapp, (i,))
    pool.close()
    pool.join()

```