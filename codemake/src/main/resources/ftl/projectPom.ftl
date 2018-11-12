<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>boot</artifactId>
        <groupId>com.simple</groupId>
        <version>1.0</version>
    </parent>
    
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.simple</groupId>
    <artifactId>${projectName}</artifactId>
    <packaging>pom</packaging>
    <version>1.0</version>
    
    
    <modules>
        <module>${projectName}Service</module>
        <module>${projectName}Admin</module>
        <module>${projectName}Api</module>
    </modules>
    <dependencies>
        <dependency>
            <groupId>com.simple</groupId>
            <artifactId>base</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
    
</project>