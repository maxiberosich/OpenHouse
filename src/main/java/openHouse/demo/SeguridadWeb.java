package openHouse.demo;

import openHouse.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
        
    }
    
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
                .authorizeRequests()
                    .requestMatchers("/admin/*").hasRole("ADMIN")
                    .requestMatchers("/css/","/js/","/img/","/*")
                        .permitAll()
                .and().formLogin()
                    .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio")
                            .permitAll()
                .and().logout()
                        .logoutSuccessUrl("/login")
                        .permitAll()
                .and().csrf()
                .disable();
        return http.build();
    }
}
