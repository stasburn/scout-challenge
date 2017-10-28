package com.autoscout24;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan("com.autoscout24")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    FilterRegistrationBean corsFilter(
            @Value("${tagit.origin:*}") String origin) {
        return new FilterRegistrationBean(new Filter() {
            public void doFilter(ServletRequest req, ServletResponse res,
                                 FilterChain chain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                String method = request.getMethod();

                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Methods",
                        "POST,GET,OPTIONS,DELETE");
                response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
                response.setHeader("Access-Control-Allow-Credentials", "true");

                if ("OPTIONS".equals(method)) {
                    response.setStatus(HttpStatus.OK.value());
                } else {
                    chain.doFilter(req, res);
                }
            }

            public void init(FilterConfig filterConfig) {
            }

            public void destroy() {
            }
        });
    }

}
