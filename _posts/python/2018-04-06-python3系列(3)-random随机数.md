---
layout: post
title: python3系列(3)-random随机数
category: python
tags: python
description: 
---

## random

**使用之前需要导入该模块**

```
import random
```

### 返回0到1之间的一个随机浮点数

```
random.random()
```

### 返回一个从1到100之间的随机整数

```
random.randint(1,100)
```

### 用于生成一个指定范围内的随机浮点数，a,b为上下限

```
random.uniform(a,b)
```

### 按指定基数递增的集合中获取一个随机数，基数缺省值为1

```
random.randrange([start], stop, [,step])
```

### 从序列中获取一个随机元素，参数sequence表示一个有序类型，例如list，tuple，字符串等

```
random.choice(sequence)
```

### 打乱序列的顺序

```
a=[1,3,5,6,7]                # 将序列a中的元素顺序打乱
random.shuffle(a)
print(a)
```

### 从序列中抽取一个固定长度k的序列，并打乱其顺序

```
random.sample(sequence, k)
```
