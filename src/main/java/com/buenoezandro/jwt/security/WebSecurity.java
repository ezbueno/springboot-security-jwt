package com.buenoezandro.jwt.security;

import com.buenoezandro.jwt.encrypt.PasswordEncoderEncrypt;
import com.buenoezandro.jwt.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.buenoezandro.jwt.security.constants.SecurityConstants.SIGN_UP_URL;
import static com.buenoezandro.jwt.security.constants.SecurityConstants.STATUS_URL;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private static final String PATTERN = "/**";

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoderEncrypt passwordEncoderEncrypt;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(GET, STATUS_URL)
                .permitAll()
                .antMatchers(POST, SIGN_UP_URL)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(this.authenticationManagerBean()))
                .addFilter(new JWTAuthorizationFilter(this.authenticationManagerBean()))
                .sessionManagement().sessionCreationPolicy(STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                this.userDetailsService).passwordEncoder(
                this.passwordEncoderEncrypt.bCryptPasswordEncoder()
        );
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(PATTERN, new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
