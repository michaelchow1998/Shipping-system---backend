package com.michael.shipping_system.security.config;

import com.michael.shipping_system.security.filter.CustomAuthorizationFilter;
import com.michael.shipping_system.security.filter.CustomAuthticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    String[] BypassLists = {
            "/api/v1/login",
            "/api/v1/token/refresh",
            "/api/v1/guest/register",
            "/api/v1/guest/changepw",
            "/api/v1/guest/orders/**",
            "/api/v1/guest/locations"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthticationFilter customAuthticationFilter = new CustomAuthticationFilter(authenticationManagerBean());
        customAuthticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.cors().configurationSource(new CorsConfigurationSource() {
                                            @Override
                                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                                CorsConfiguration config = new CorsConfiguration();
                                                config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                                config.setAllowedOrigins(Collections.singletonList("https://shipsheep.netlify.app"));
                                                config.setAllowedMethods(Collections.singletonList("*"));
                                                config.setAllowCredentials(true);
                                                config.setAllowedHeaders(Collections.singletonList("*"));
                                                config.setMaxAge(3600L);
                                                return config;
                                            }
                                        });
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(BypassLists).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


}
