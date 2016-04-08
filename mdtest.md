>开发的单号list 
>
>>["808517"](https://oa.ztesoft.com/queryTransDtl.action?transid=808517&orgState=O)
>
>>["811772"](https://oa.ztesoft.com/queryTransDtl.action?transid=811772&orgState=O)


#本文的目的
教会你如何使用自定义TU

##Introduce
写用例的时候你一定遇到过下面这些情况：

1.不支持分支判断

2.系统提供的函数不符合要求

3.我是测试中最好的开发，为啥要让我'点点点'写用例，我喜欢写代码！！！！



**自定义TU就是拿来干这些事的**

```groovy
package ztp.tu.common

import com.ztesoft.zsmart.ztp.tu.annotation.*

@Step(id="ztp.tu.common.MyPrintTU", name="MyPrintTU", desc="My Print TU")


@Category("Common")

class TestClient{

def sql
def log
def clientMap  //clientMap(变量名不可更改),包含运行环境中绑定的所有client,map形式，通过clientMap(key)获取client

def globalVarMap //globalVarMap(变量名不可更改),包含运行环境中绑定的所有全局变量,map形式，通过globalVarMap(key)获取全局变量值 

public exec(){

//获取变量名为"aa"的全局变量

  def varAa = globalVarMap.get("aa"));

//获取绑定的oracle_cc的客户端

  def varOracleCCClient = clientMap.get("oracle_cc"))


  Sql s = new Sql(clientMap.get("oracle_cc"))
  s.rows(sql)

}
}

```



###什么是Groovy
["Groovy"](http://baike.baidu.com/link?url=ikHhBijnIdyTfWvXXGeZk-S7F7UScoryhddkhYadcw-k1zvMJm6WKfOS1J4Juse4U9Da-DSqNLaE7APwaT-YUK)是一种基于JVM（Java虚拟机）的敏捷开发语言，它结合了Python、Ruby和Smalltalk的许多强大的特性，Groovy 代码能够与 Java 代码很好地结合，也能用于扩展现有代码。由于其运行在 JVM 上的特性，Groovy 可以使用其他 Java 语言编写的库

["Groovy的官网"](http://www.groovy-lang.org/)
