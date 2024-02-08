package ChatAPP_Security.Config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import ChatAPP_Security.Authorization.JwtTokenExceptionGlobalHandler;
import ChatAPP_Security.Authorization.UnAuthorizatePath;
import ChatAPP_Security.Filter.jwtAuthorizationFilter;
import ChatAPP_Security.Filter.jwtDeviceIdFilter;

@EnableWebSecurity
@Configuration
@Component
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private jwtDeviceIdFilter deviceFilter;
	@Autowired
	private jwtAuthorizationFilter autFilter;
	@Autowired
	private UnAuthorizatePath skipPath;
	@Override
	 protected void configure(HttpSecurity http)throws Exception {
		 
		 
		 http.csrf().disable()
		 .formLogin().disable()
		 .logout().disable()	
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 .and()
		 .exceptionHandling()
		 .authenticationEntryPoint((request, response, authException)->{
			 if(authException!=null) {
				 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			 }
		 })
		 .and()
		 .addFilterAfter(this.deviceFilter, UsernamePasswordAuthenticationFilter.class)
		 .addFilterAfter(this.autFilter, this.deviceFilter.getClass())
		 .authorizeRequests()
		 .antMatchers(this.skipPath.getUnAuthorizatedPath()).permitAll()
		 .anyRequest().fullyAuthenticated();
		 ;
	 }
}