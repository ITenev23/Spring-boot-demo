package bg.paysafe.springboot.api.config;

import bg.paysafe.springboot.api.constant.Constants;
import bg.paysafe.springboot.api.filters.ExceptionHandlerFilter;
import bg.paysafe.springboot.api.filters.JWTAuthenticationFilter;
import bg.paysafe.springboot.api.filters.JWTAuthorizationFilter;
import bg.paysafe.springboot.api.handler.exception.ExceptionResolver;
import bg.paysafe.springboot.api.service.user.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static bg.paysafe.springboot.api.constant.URLMappings.*;
import static bg.paysafe.springboot.api.constant.URLMappings.User.VALIDATE;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userDetailsService;

    private PasswordEncoder passwordEncoder;

    private final ExceptionResolver exceptionResolver;

    private final String secretKey;

    private final ObjectMapper objectMapper;

    public SecurityConfig(UserServiceImpl userDetailsService,
                          PasswordEncoder passwordEncoder,
                          ExceptionResolver exceptionResolver,
                          @Value("${jwt.secret.key}") String secretKey,
                          ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.exceptionResolver = exceptionResolver;
        this.secretKey = secretKey;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, USER_BASE + VALIDATE).permitAll()
//                .antMatchers(HttpMethod.GET, USER_BASE).hasAnyAuthority(Constants.ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, VERIFY_USER).hasAnyAuthority(Constants.ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, USER_BY_ID).hasAnyAuthority(Constants.ALL_ROLES)
//                .antMatchers(HttpMethod.POST, CONVERSATION_BASE, CONVERSATION_BASE + ID, CREATE_CONVERSATION_NAME).hasAnyAuthority(Constants.ROLES_VERIFIED)
                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(this.objectMapper, this.authenticationManager(), this.secretKey, this.userDetailsService))
                .addFilter(new JWTAuthorizationFilter(this.authenticationManager(), this.secretKey, this.userDetailsService))
                .addFilterBefore(new ExceptionHandlerFilter(this.exceptionResolver), JWTAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(ALL_MAPPINGS, new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
}
