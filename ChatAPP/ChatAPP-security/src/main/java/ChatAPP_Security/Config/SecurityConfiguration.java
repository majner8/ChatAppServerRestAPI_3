package ChatAPP_Security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

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

	@Override
	 protected void configure(HttpSecurity http)throws Exception {
		 
		 
		 http.csrf().disable()
		 .formLogin().disable()
		 .logout().disable()	
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 .and() 
		 .addFilterAfter(this.deviceFilter, UsernamePasswordAuthenticationFilter.class)
		 .addFilterAfter(this.autFilter, this.deviceFilter.getClass())
		 .authorizeRequests()
		 .antMatchers(AuthorizationPath.deviceIDPath).permitAll()
		// .anyRequest().hasAuthority(RoleManagement.deviceIDRole)
		 .antMatchers(AuthorizationPath.AuthorizationPreflix+AuthorizationPath.UnAuthenticatedPreflix+"/**").permitAll()
		 .anyRequest().fullyAuthenticated()
		 ;
	 }
}