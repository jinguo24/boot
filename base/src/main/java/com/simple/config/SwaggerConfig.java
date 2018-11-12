//package com.simple.config;
//
//import com.google.common.base.Predicate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.ResponseEntity;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import static com.google.common.base.Predicates.or;
//import static springfox.documentation.builders.PathSelectors.regex;
//
///**
// * @author chenkx
// * @date 2018-01-13.
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//
//    @Value("${server.servlet-path}")
//    private String pathMapping;
//
//    private ApiInfo initApiInfo() {
//        ApiInfo apiInfo = new ApiInfo("swagger",//大标题
//                "swagger接口",//简单的描述
//                "1.0.0",//版本
//                "服务条款",
//                "后台开发团队",//作者
//                "",//链接显示文字
//                ""//网站链接
//        );
//
//        return apiInfo;
//    }
//
//    @Bean
//    public Docket restfulApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("RestfulApi")
////                .genericModelSubstitutes(DeferredResult.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .useDefaultResponseMessages(true)
//                .forCodeGeneration(false)
//                .pathMapping(pathMapping) // base，最终调用接口后会和paths拼接在一起
//                .select()
//                .paths(doFilteringRules())
//                .build()
//                .apiInfo(initApiInfo());
//    }
//
//    /**
//     * 设置过滤规则
//     * 这里的过滤规则支持正则匹配
//     *
//     * @return
//     */
//    private Predicate<String> doFilteringRules() {
//        return or(
//                regex("/hello.*"),
//                regex("/vehicles.*")
//        );
//    }
//}
