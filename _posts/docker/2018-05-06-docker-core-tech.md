---
layout: post
title: docker核心技术
category: docker
tags: docker
keywords: docker
---


## 容器核心技术

**让容器在Host上能运行起来的技术**

* 容器规范：OCI(Open Container Initiative)组织制定了runtime spec和image format spec

* 容器runtime：dockers使用的是runc

* 容器管理工具：docker engine包含后台deamon和cli两部分

* 容器定义工具：dockerfile

* Registries：统一存放镜像的仓库

* 容器OS：专门运行容器的操作系统，CoreOS、Atomic、Ubuntu Core等

## 概述

**容器是一种轻量级、可移植、自包含的软件打包技术**

**解决的问题**：如何让多种服务运行在多种运行环境中

## 架构

![docker架构图](/assets/img/2018-05-06-docker-core-1.jpg)

* docker客户端可以通过cli或者socket或者rest api与docker deamon通信

* docker deamon以服务的形式
