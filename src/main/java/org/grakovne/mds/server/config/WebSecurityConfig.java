package org.grakovne.mds.server.config;

import org.grakovne.mds.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring security config.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .headers()
            .disable();

        http
            .httpBasic();

        http
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/", "/api/v1/user/**")
            .permitAll()
            .anyRequest()
            .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Data auth provider.
     *
     * @return DaoAuthenticationProvider object.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    /**
     * returns password encoder.
     *
     * @return PasswordEncoder entity
     */
    @Bean
    public ShaPasswordEncoder encoder() {
        return new ShaPasswordEncoder(512);
    }
}
