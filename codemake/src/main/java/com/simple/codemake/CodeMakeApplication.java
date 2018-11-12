package com.simple.codemake;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenkx
 * @date   2017-12-26
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.simple.codemake.mapper"})
public class CodeMakeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeMakeApplication.class, args);
	}
}
