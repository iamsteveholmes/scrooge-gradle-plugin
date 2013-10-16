scrooge-gradle-plugin
=====================

A gradle plugin to compile thrift files into Scala code.  

It can be configured as follows:

compileScrooge {
    thriftFiles = fileTree(dir: "src/main/thrift", include: "**/*.thrift")
    dest = file("src/gen/scala")
    opts = ["--finagle"]
}
