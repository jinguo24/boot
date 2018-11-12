server:
  port: 9000
  context-path: /

spring:
  application:
    name: ${projectName}
  profiles:
    active: dev
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8


# @{link} https://github.com/abel533
#Mybatis
mybatis:
  type-aliases-package: com.simple.domain.po
  mapper-locations: classpath:mapper/*Mapper.xml
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: true
    lazy-loading-enabled: true
    use-generated-keys: true
    default-fetch-size: 100

#PageHelper
pagehelper:
    helperDialect: mysql
    reasonable: false
    supportMethodsArguments: true
    params: count=countSql
    offset-as-page-num: true
    page-size-zero: true
    row-bounds-with-count: true

mapper:
  mappers:
    - com.simple.common.crud.CommonMapper
