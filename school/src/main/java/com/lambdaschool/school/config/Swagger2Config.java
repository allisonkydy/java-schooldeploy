package com.lambdaschool.school.config;

import com.fasterxml.classmate.TypeResolver;
import com.lambdaschool.school.model.APIOpenLibrary;
import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
@EnableSwagger2
public class Swagger2Config
{
  @Autowired
  private TypeResolver resolver;

  @Bean
  public Docket api()
  {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors
            .basePackage("com.lambdaschool.school"))
        .paths(PathSelectors.any())
        //                                .paths(PathSelectors.regex("/**"))
        .build()
        .useDefaultResponseMessages(false) // Allows only my exception responses
        .ignoredParameterTypes(Pageable.class) // allows only my paging parameter list
        .apiInfo(apiEndPointsInfo())
        .pathMapping("/")
        .additionalModels(resolver.resolve(APIOpenLibrary.class),
                          resolver.resolve(TokenModel.class),
                          resolver.resolve(ErrorDetail.class))
        .ignoredParameterTypes(SimpleGrantedAuthority.class);
  }

  private ApiInfo apiEndPointsInfo()
  {
    return new ApiInfoBuilder().title("School")
        .description("School project with courses, instructors, and students")
        .contact(new Contact("Allison Donnelly", "", ""))
        .license("MIT").licenseUrl("https://github.com/allisonkydy/java-schoolpagesswagger/blob/master/LICENSE")
        .version("1.0.0").build();
  }
}
