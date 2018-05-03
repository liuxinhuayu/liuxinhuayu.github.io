---
layout: post
title: docker-容器技术
category: docker
tags: Docker
keywords: Docker
---

## 容器技术

指让Container在host上运行起来的技术

### 容器规范

Open Container Initiative(OCI)组织发布了两个规范：runtime spec和image format spec

* runtime类似java虚拟机，是在host上一个能运行容器的环境，主流的三种runtime

   * lxc: linux系统较早的一种runtime
   * runc: 是docker自己开发的一种runtime
   * rkt：CoreOS公司开发的runtime

* 容器管理工具：runc的管理工具docker engine包含deamon和cli两部分

* 容器定义工具：docker使用dockerfile定义

