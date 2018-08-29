scrooge-gradle-plugin
=====================

A gradle plugin to compile thrift files into Scala code.  

It can be configured as follows:
```
compileScrooge {
    sourceDir = "src/main/thrift"
    targetDir = "target/scala"
    opts = ["--finagle"]
}
```
