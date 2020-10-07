package atmmachine.config;

import atmmachine.security.SuccessfulLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    SuccessfulLogin successfulLogin;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/**").hasAnyRole("USER")
//                .and()
//                .formLogin().loginPage("/login").defaultSuccessUrl("/home", true)
//                .failureUrl("/tryagain").permitAll()
//                .and().logout().logoutSuccessUrl("/login")
//                ; //successHandler(successfulLogin)

        http
                .authorizeRequests().anyRequest().hasAnyRole("USER")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home", true)
                .failureUrl("/tryagain").permitAll()
                .and().logout().logoutSuccessUrl("/login")
                .and()
                .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}