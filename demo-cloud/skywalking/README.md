# skywalking

## 下载
到 [SkyWalkingJavaAgent](https://skywalking.apache.org/downloads/#SkyWalkingJavaAgent) 下载java的agent, agent的版本需要同skywalking-oap的版本对应    
使用terminal cd到agent目录下
```
wget https://dlcdn.apache.org/skywalking/java-agent/8.8.0/apache-skywalking-java-agent-8.8.0.tgz
tar -xvf apache-skywalking-java-agent-8.8.0.tgz 
```

## 应用
启动的jvm参数添加`-javaagent:$ProjectFileDir$/demo-cloud/skywalking/agent/skywalking-agent/skywalking-agent.jar -DSW_AGENT_NAME=<applicationName>`  
配置文件`agent.config`配置的参数可以使用-D传进去  
如果启动的是jar,则需要放在-jar之前