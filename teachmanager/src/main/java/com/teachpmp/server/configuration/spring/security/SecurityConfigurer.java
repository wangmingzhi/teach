package com.teachpmp.server.configuration.spring.security;

import com.teachpmp.server.configuration.property.CookieConfig;
import com.teachpmp.server.configuration.property.SystemConfig;
import com.teachpmp.server.entity.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;




@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private SystemConfig systemConfig;
        @Autowired
        private LoginAuthenticationEntryPoint restAuthenticationEntryPoint;
        @Autowired
        private RestAuthenticationProvider restAuthenticationProvider;
        @Autowired
        private RestDetailsServiceImpl formDetailsService;
        @Autowired
        private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
        @Autowired
        private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
        @Autowired
        private RestLogoutSuccessHandler restLogoutSuccessHandler;
        @Autowired
        private RestAccessDeniedHandler restAccessDeniedHandler;
        @Autowired
        CustomFilterInvocation customFilterInvocationSecurityMetadataSource;
        @Autowired
        CustomUrlDecisionManager customUrlDecisionManager;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/","/login","/home","/admin/**", "/class/**","/question/**","/static/**","/index.html","/favicon.ico");
        }
        /**
         * @param http http
         * @throws Exception exception
         *                   csrf is the from submit get method
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            List<String> securityIgnoreUrls = systemConfig.getSecurityIgnoreUrls();
            String[] ignores = new String[securityIgnoreUrls.size()];
            http
                    .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                    .and().authenticationProvider(restAuthenticationProvider)
                    .authorizeRequests().antMatchers(securityIgnoreUrls.toArray(ignores)).permitAll()
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                            object.setAccessDecisionManager(customUrlDecisionManager);
                            object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                            return object;
                        }
                    })
//                    .expressionHandler(webExpressionHandler())

//                    .antMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
//                    .antMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())
                    .anyRequest().permitAll()
                    .and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                    .and().formLogin().successHandler(restAuthenticationSuccessHandler).failureHandler(restAuthenticationFailureHandler)
                    .and().logout().logoutUrl("/api/user/logout").logoutSuccessHandler(restLogoutSuccessHandler).invalidateHttpSession(true)
                    .and().rememberMe().key(CookieConfig.getName()).tokenValiditySeconds(CookieConfig.getInterval()).userDetailsService(formDetailsService)
                    .and().csrf().disable()
                    .cors();
        }

        private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
            DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
            defaultWebSecurityExpressionHandler.setDefaultRolePrefix("");
            return defaultWebSecurityExpressionHandler;
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setMaxAge(3600L);
            configuration.setAllowedOrigins(Collections.singletonList("*"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/api/**", configuration);
            return source;
        }


        @Bean
        public RestLoginAuthenticationFilter authenticationFilter() throws Exception {
            RestLoginAuthenticationFilter authenticationFilter = new RestLoginAuthenticationFilter();
            authenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
            authenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
            authenticationFilter.setAuthenticationManager(authenticationManagerBean());
            authenticationFilter.setUserDetailsService(formDetailsService);
            return authenticationFilter;
        }


    }
}
