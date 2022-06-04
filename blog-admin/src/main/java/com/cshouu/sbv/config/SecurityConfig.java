package com.cshouu.sbv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        String cshouu = new BCryptPasswordEncoder().encode("cshouu");
        System.out.println(cshouu);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/findAll").hasRole("admin") //访问接口需要admin的角色
                .antMatchers("/css/**").permitAll() //无需登录就可访问
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/plugins/**").permitAll()
                .antMatchers("/admin/**").access("@authService.auth(request,authentication)") //需要认证
                .antMatchers("/pages/**").authenticated() //认证成功可以访问
                .and().formLogin()
                .loginPage("/login.html") //自定义登录页面
                .loginProcessingUrl("/login") //spring security实现了这个接口，可覆盖
                .usernameParameter("username") //登录页面内属性
                .passwordParameter("password")
                .defaultSuccessUrl("/pages/main.html") //登录成功跳转
                .failureUrl("/login.html") //失败跳转
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .permitAll()
                .and()
                .httpBasic() //postman访问拦截
                .and()
                .csrf().disable() //跨站访问拦截，如果自定义登录，需要关闭
                .headers().frameOptions().sameOrigin(); //支持iframe嵌套
    }
}
