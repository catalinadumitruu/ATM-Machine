package atmmachine.config;

import atmmachine.security.SuccessfulLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class Profiles {
    public static final String PRODUCTION = "prod";
    public static final String TESTING = "test";
}

// -Dspring.profiles.active=prod
@Configuration
@EnableWebSecurity
@Profile(Profiles.PRODUCTION)
@Order(1)
public class WebSecurityConfig_Prod extends WebSecurityConfigurerAdapter {

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

        http
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
                .antMatchers("/deleteAccount*", "/deleteClient*", "/clients**", "/accounts*", "/bigAccounts").hasRole("ADMIN")
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/login")
                .successHandler(successfulLogin)
//                .defaultSuccessUrl("/home")
//                .successForwardUrl("/home")
                .failureUrl("/loginFailed")
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
                .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}


// -Dspring.profiles.active=test
@Profile(Profiles.TESTING)
@Configuration
@Order(2)
class WebSecurityConfig_Test extends  WebSecurityConfigurerAdapter  {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }
}