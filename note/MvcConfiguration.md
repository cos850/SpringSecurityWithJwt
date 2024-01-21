WebMvcConfigurer
==========


## 1. View Resolver
- [참고 - springDoc: View Resolution](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/viewresolver.html)   
- [참고 - Baeldung: ViewResolver 가이드](https://www.baeldung.com/spring-mvc-view-resolver-tutorial)

```@Controller```가 반환한 View 이름을 실제 ```View``` 로 변환해주는 역할을 수행한다.
관련 설정은 application.properties 파일에서 변경하거나, ```WebMvcConfigurer``` 을 상속한 클래스에서 <code>configureViewResolvers()</code> 함수를 재정의하여 변경할 수 있다.
다중 View Resolver를 지원하므로 ```bean.setOrder``` 을 통해 순서를 결정할 수 있다. 

#### 예시 - application.properties
```
spring.mvc.view.prefix=/resources/templates/
spring.mvc.view.suffix=.html

# controller "index" 반환 시 => /src/main/resources/templates/index.mustache 를 찾아서 반환
```

#### 예시 - WebMvcConfigurer
```
@Override
public void configureViewResolvers(ViewResolverRegistry registry) {
    MustacheViewResolver resolver = new MustacheViewResolver();
    resolver.setCharset("UTF-8");
    resolver.setContentType("text/html; charset=UTF-8");
    resolver.setPrefix("classpath:/templates/");
    resolver.setSuffix(".html");

    registry.viewResolver(resolver);
}
```



