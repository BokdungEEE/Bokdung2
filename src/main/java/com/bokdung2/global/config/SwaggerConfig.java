package com.bokdung2.global.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

  //리소스 핸들러 설
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }


  // API마다 구분짓기 위한 설정.
  @Bean
  public Docket userApi() {
    return getDocket("유저", Predicates.or(
            PathSelectors.regex("/users.*")));
  }


  @Bean
  public Docket postApi() {
    return getDocket("메시지", Predicates.or(
            PathSelectors.regex("/posts.*")));
  }


  @Bean
  public Docket commonApi() {
    return getDocket("공통", Predicates.or(
            PathSelectors.regex("/test.*")));

  }

  @Bean
  public Docket allApi() {
    return getDocket("전체", Predicates.or(
            PathSelectors.regex("/*.*")));
  }

  //swagger 설정.
  public Docket getDocket(String groupName, Predicate<String> predicate) {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName(groupName)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.cc.kr"))
            .paths(predicate)
            .apis(RequestHandlerSelectors.any())
            .build();
  }

  //swagger ui 설정.
  @Bean
  public UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
            .displayRequestDuration(true)
            .validatorUrl("")
            .build();
  }

}
