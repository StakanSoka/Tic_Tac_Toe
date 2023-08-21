package config;

import manager.UserDetailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailManager userDetailManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/create").permitAll()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/game/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .defaultSuccessUrl("/")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
    }

    // @formatter:off
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    public void setUserDetailManager(UserDetailManager userDetailManager) {
        this.userDetailManager = userDetailManager;
    }

}
