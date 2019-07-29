# +

https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework
https://www.soapui.org/load-testing/concept.html

Pattern.compile("date\\(.*\\)").matcher(value).find()

# Encodings

https://docs.oracle.com/javase/7/docs/technotes/tools/solaris/native2ascii.html

# Check environment variables

System.getProperties()

# JSON serialize

com.fasterxml.jackson.databind.ObjectMapper jsonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
return jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(doc);

# Spring

org.springframework.web.client.RestTemplate.doExecute
https://dzone.com/articles/logging-spring-rest-apis

properties
    org.springframework.util.PropertyPlaceholderHelper.parseStringValue(String, PlaceholderResolver, Set<String>)

# PermGen, Heap

jstat –gc $java_pid

http://www.eclipse.org/mat/

Run > Debug Configurations
-XX:MaxPermSize=512m

# Optimize memory usage

-XX:+UseStringDeduplication -XX:+PrintStringDeduplicationStatistics
    https://blog.codecentric.de/en/2014/08/string-deduplication-new-feature-java-8-update-20-2/

# Monitoring

jconsole

# deadlock

jstack $pid
~/code/snippets/java/Deadlock.java

# Profiling

Run the JUnit tests once, to create the run configuration
Edit the Run Configuration (Run->Run Configurations...)
In the Test tab check the box 'Keep JUnit running after test when debugging'
Rerun the test (with Debug). This will run the tests, but importantly, leave the JVM around, so that you can attach to it using JVisualVM.

ftp://ftp.informatik.uni-stuttgart.de/pub/library/medoc.ustuttgart_fi/FACH-0184/FACH-0184.pdf

https://stackoverflow.com/questions/6846049/profiling-a-running-java-application-in-command-line

### jvm explorer

http://www.jvmmonitor.org/doc/

### jvisualvm

http://visualvm.java.net/eclipse-launcher.html

eclipse
Run As > Run Configuration > Arguments > VM arguments > Add the below
-Djava.rmi.server.hostname=hostname -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=10001

visualvm.exe --jdkhome "C:\Software\Java\jdk1.6.0" --userdir "C:\Temp\visualvm_userdir"
File > Add JMX Connection > In connection text box > localhost:10001 

Tomcat > Heap Dump
select x from org.apache.catalina.loader.WebappClassLoader x
    LeftClick Query Result > RightClick Instance > Show Nearest GC Root
https://cdivilly.wordpress.com/tag/outofmemoryerror/
https://github.com/eclipse-ee4j/glassfish/issues/16917#issuecomment-421844492
https://cdn.app.compendium.com/uploads/user/e7c690e8-6ff9-102a-ac6d-e4aebca50425/f4a5b21d-66fa-4885-92bf-c4e81c06d916/File/30ba02a656e6f6e05da94eac8661e88a/oql.htm#map

# Tomcat debug

export JPDA_SUSPEND=y
${TOMCAT_HOME}/bin/catalina.sh jpda

# jdbc, mybatis

dao.sqlSession.configuration.environment.dataSource.dataSource.url

### mappings
org.apache.ibatis.binding.MapperMethod.execute(SqlSession, Object[])
org.apache.ibatis.executor.CachingExecutor.query(MappedStatement, Object, RowBounds, ResultHandler, CacheKey, BoundSql)
org.apache.ibatis.executor.SimpleExecutor.doQuery(MappedStatement, Object, RowBounds, ResultHandler, BoundSql)
org.apache.ibatis.executor.statement.PreparedStatementHandler.query(Statement, ResultHandler)

### table, model inferred
org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleResultSets(Statement)

# logging

final MyService mock = Mockito.mock(MyService.class, Mockito.withSettings().verboseLogging());

http://logging.apache.org/log4j/1.2/manual.html#defaultInit

-Dlog4j.debug -Dlog4j.configuration=file:/c:/foobar.xml

NOTE: Only 1 config is loaded. If >1 projects in server, first run project's config is loaded

-Djava.util.logging.config.file=/c/Users/foo/.m2/conf/logging/logging.properties
-Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager
-Djaxp.debug=1

# local datasource

```context.xml
<Resource name="jdbc/docecataleg2Ds" auth="Container" type="javax.sql.DataSource" 
               maxActive="20" maxIdle="10" maxWait="-1" 
               username="system" password="liberty09" driverClassName="oracle.jdbc.OracleDriver" 
               url="jdbc:oracle:thin:@localhost:1521:ecataleg"/>
```

```web.xml
<resource-ref>
    <description>DB Connection</description>
    <res-ref-name>jdbc/docecataleg2Ds</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
</resource-ref>
```

# h2

http://localhost:8082
jdbc:h2:mem:testdb
file://~/code/snippets/java/PersistenceServiceConfig.java

jdbc:h2:tcp://localhost//C:\Users\foo\opt\motion\motion-engine-2.7.2\h2\motion-engine

# tracing

```sh
D:\opt\btrace-bin-1.3.11\bin\btrace.bat 10764 D:\bin\btrace\AllCallsAgent.java -v

/d/opt/btrace-bin-1.3.11/bin/btracec *.java
-javaagent:D:\opt\btrace-bin-1.3.11\build\btrace-agent.jar=noServer=true,debug=false,trusted=true,script=D:\bin\btrace\com\sun\btrace\samples\AllCallsAgent.class,scriptOutputFile=D:\btrace.out
-javaagent:/tmp/btrace/build/btrace-agent.jar=noServer=true,debug=false,trusted=true,script=/tmp/btrace/com/sun/btrace/samples/AllCallsAgent.class,scriptOutputFile=/tmp/btrace.out

# References:
# https://github.com/btraceio/btrace/blob/bf48290d06193e0f28c904183cb3e15906fa14f7/src/share/classes/com/sun/btrace/BTraceUtils.java

# Reproducable:
# 1. dir for script
# 2. make compiled script
# 3. pass jvm option
# || attach with jvm pid
```

```ps1
# btrace
$env:JAVA_HOME="D:\jdk-7u80-windows-x64"

# javaw PID
.\btrace.bat 18032 C:\Users\foo\Documents\AllCalls1.java -v
```

# remote debugging

java -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=y _

# jsp

https://tomcat.apache.org/tomcat-7.0-doc/jasper-howto.html

# jvm options

```bash
# version: any
jps -lvm

# version: <=7
jinfo -flags $PID
jinfo -sysprops $PID

# version: >=8
jcmd -l
jcmd $PID VM.system_properties
jcmd $PID VM.flags
```

# code dump

# Java >= 9
-XX:+CreateCoredumpOnCrash

# Java <= 8, Windows
-XX:+CreateMinidumpOnCrash

# debug

java -agentlib:jdwp=transport=dt_shmem,address=jdbconn,server=y,suspend=n MyClass
java -agentlib:jdwp=transport=dt_socket,address=localhost:29010,server=y,suspend=y MyClass

-dbgtrace
jdb -attach localhost:8000 -sourcepath ~/dev/remote/src/main/java/
UNIX:
jdb -attach localhost:29010
Windows:
jdb -connect com.sun.jdi.SocketAttach:hostname=localhost,port=29010
```
stop in com.stackify.debug.rest.HelloController.hello(java.lang.String)

threads
thread 0x9
where

# if compiled with `-g`
locals
print [local]
dump [local]

help
eval <expr>               -- evaluate expression (same as print)
set <lvalue> = <expr>     -- assign new value to field/variable/array element

# References:
# https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/tooldescr011.html
# https://www.infoq.com/articles/Troubleshooting-Java-Memory-Issues/
```

# Thread dump

jstack -J-d64 -l $pid > /tmp/thread_dump.log
# ||
jstack -F $JAVA_PID > /tmp/thread_dump.log
# ||
sudo -u $USER_OF_JAVA_PID jstack $JAVA_PID
# ||
kill -3 $JAVA_PID

# heap dump
pgrep java | xargs -d'\n' -n1 -I{} sh -c '
    dump_file=$(mktemp) && \
    echo "Dump file: $dump_file, Process: "'{}'
    jmap -F -dump:file="$dump_file" '{}

# @server
mknod backpipe p;
tail -f -n +1 backpipe | ssh "${SSH_CLIENT%%\ *}" 'cat - > ~/out.dump'
# || if ssh client isn't running sshd
tail -f -n +1 backpipe | nc localhost 60123
# ||
tar -czf - backpipe | nc localhost 60123
# @client
ssh foo -R 60123:127.0.0.1:60123 -N
~/bin/receive-tcp-message.ps1 -Port 60123 > ./dump
# ||
# https://github.com/besimorhino/powercat
. ./powercat.ps1
powercat -l -p 60123 -of ./dump
# ?||
ssh foo@bar:60123 tar -xzf - -C ./dump
# @server
( \
    trap 'rm -f backpipe' EXIT INT QUIT TERM && \
    jmap -F -dump:file=backpipe 5225 \
);

jhat -debug 1 "$dump_file"
jmap -permgen "$dump_file"

# JDK JVM Options
# -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp

# OpenJDK JVM Options
# -verbose:gc –XX:+PrintGCDetails –XX:+PrintGCTimeStamps –Xloggc:<app path>/gc.log

# Remote debug

```~/jstatd.all.policy
grant codebase "file:${java.home}/../lib/tools.jar" {
   permission java.security.AllPermission;
};
```

```bash
jstatd -p 39999 -J-Djava.security.policy=/home/foo/jstatd.all.policy -J-Djava.rmi.server.logCalls=true

# Reference:
# https://www.toptal.com/java/hunting-memory-leaks-in-java#enabling-remote-connection-for-the-jvm

# ||

java -jar foo.jar \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -Dcom.sun.management.jmxremote.port=9010 \
    -Dcom.sun.management.jmxremote.rmi.port=9011 \
    -Djava.rmi.server.hostname=localhost \
    -Dcom.sun.management.jmxremote.local.only=false

# Reference:
# https://docs.oracle.com/javase/7/docs/technotes/guides/rmi/faq.html#domain
```

---

https://docs.oracle.com/javase/6/docs/technotes/tools/share/jmap.html#options
https://docs.oracle.com/javase/6/docs/technotes/tools/share/jhat.html#options
Java Memory Profiler
monitor memory
    jmx based tools - jconsole
        jvisualvm

```
env _JAVA_OPTIONS="-Djavax.net.debug=all"
env _JAVA_OPTIONS="-Djavax.net.debug=ssl"

env _JAVA_OPTIONS="-Dcom.sun.net.ssl.checkRevocation=false -Dsun.security.ssl.allowUnsafeRenegotiation=true"
env _JAVA_OPTIONS="-Djavax.net.ssl.Keystore="
```
https://github.com/codeFX-org/demo-java-9-migration
https://tiny.cc/java-9-migration

# Maven

version 3
    colors

# Java 9,)

### repl

jshell -v

### profile

<activation>
    <jdk>[9,)</jdk>
</activation>
<properties>
    <maven.compiler.release>[9,)</maven.compiler.release>
</properties>

### report internal dependencies

```
jdeps -summary foo.jar
jdeps --jdk-internals -recursive --class-path 'lib/*'

<build>
    <plugin>
        <artifactId>maven-surefire-plugin
        <configuration>
            <argLine>
                --add-exports, 
                --add-opens=java.base/java.lang=ALL-UNNAMED, 
                --permit-illegal-access

<build>
    <plugin>
        <artifactId>maven-compiler-plugin
        <configuration>
            <compilerArgs>
                <arg>--add-modules=java.xml.bind
```

### split packages

=> classloader loads one module, misses dependencies from second module

put both artifacts on classpath
--patch-module

### runtime images

jlink

### module system

http://openjdk.java.net/projects/jigsaw/spec/sotms/#readability

// explicit dependencies (<=> between jars)
module foo {
    requires transitive bar
}

// encapsulation (defeats reflection)
module foo {
    exports foo.bar
}
