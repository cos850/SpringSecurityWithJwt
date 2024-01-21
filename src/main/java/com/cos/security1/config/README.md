Configuration
==========


## 1. WebMvcConfig
### 1-1. View Resolver
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

## 1. SecurityConfig
- [참고 - springDoc: Security](https://docs.spring.io/spring-boot/docs/2.0.0.M4/reference/html/boot-features-security.html)
   
SpringSecurity를 사용하는 경우 기본적으로 단일 사용자로 구성해 basic 인증을 수행한다.   
기본 사용자의 ID는 'user'를 사용하고, 패스워드는 어플리케이션 기동 로그에 INFO 레벨로 아래와 같이 출력된다.
```
Using generated security password: 109f44f6-2684-4206-93f1-18a658a532e4
```
기본적인 보안 사용 시 특정 url로 접근 시 로그인 전인 경우 "/login" 페이지로 리다이렉트 되어 기본 로그인 화면을 적용할 수 있고, "/logout" url 호출 시 로그아웃 처리된다.   

### 1-1. @EnableWebSecurity
```@EnableWebSecurity``` 어노테이션을 추가하면 기본 웹 보안 구성이 꺼지면서 사용자가 정의한 보안 규칙을 적용한다.   
```@WebSecurityConfigurerAdapter```를 상속받은 클래스에서 함수를 오버라이드해서 사용했지만 이젠 따로 ```Bean```을 등록해서 사용해야하는 것으로 변경되었다. (```WebSecurityConfigurerAdapter disabled```)   

#### 예시 - 기존 WebSecurityConfigurerAdapter 상속 예시
```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) {
        http.csrf().disable();
        http.autorizeRequests()
            .antMatchers("/user/**").authenticated()
            .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
    }
}
```
#### 예시 - 변경 예시
```
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .formLogin(formLogin-> formLogin.loginPage("/login"))
                .build();
    }

}
```