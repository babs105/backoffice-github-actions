package sn.sastrans.backofficev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sn.sastrans.backofficev2.security.jwt.AuthEntryPointJwt;
import sn.sastrans.backofficev2.security.jwt.AuthTokenFilter;
import sn.sastrans.backofficev2.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/security/user/**").permitAll()
//                .antMatchers("/").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/parcauto/**").hasAnyAuthority("USER_PARC","ADMIN")
                .antMatchers("/carburant/**").hasAnyAuthority("USER_CARBURANT","ADMIN")
                .antMatchers("/trace/**").hasAnyAuthority("USER","USER_TRACE","ADMIN")
//                .antMatchers("/parameters/vehicle/vehicleByAffectation/*").hasAnyAuthority("USER_CARBURANT","ADMIN_PARA_VHL","ADMIN")
                .antMatchers("/parameters/**").hasAnyAuthority("ADMIN_PARA_VHL","ADMIN")
                .antMatchers("/paramrh/**").hasAnyAuthority("ADMIN_PARA_RH","ADMIN")
                .antMatchers("/hr/employeeByDepartmentName/*").hasAnyAuthority("USER_PARC","USER_RH","ADMIN")
                .antMatchers("/hr/**").hasAnyAuthority("USER_RH","ADMIN")
                .antMatchers("/stock/**").hasAnyAuthority("USER_STOCK","ADMIN")
                .antMatchers("/security/**").hasAuthority("SUPER_ADMIN")
                .antMatchers("/report/trace/**").hasAnyAuthority("ADMIN_PARA_TRACE","ADMIN")
                .antMatchers("/report/carburant/**").hasAnyAuthority("ADMIN_PARA_CARBU","ADMIN")
                .antMatchers("/dashboard/carburant/**").hasAnyAuthority("USER_CARBURANT","ADMIN")
                .antMatchers("/dashboard/trace/**").hasAnyAuthority("USER_TRACE","ADMIN")
// .antMatchers("/**").denyAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}