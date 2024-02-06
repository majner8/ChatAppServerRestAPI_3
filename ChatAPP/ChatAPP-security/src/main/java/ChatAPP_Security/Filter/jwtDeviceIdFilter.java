package ChatAPP_Security.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import ChatAPP_Security.Authorization.JwtToken.jwtToken;
import ChatAPP_Security.Properties.SecurityProperties;

@Component
public class jwtDeviceIdFilter extends OncePerRequestFilter {
	@Autowired
	private DefineFilterSkipPath.pathForDeviceIdFilter skip;

	@Autowired
	private SecurityProperties securityProperties;
	@Autowired 
	private jwtToken.jwtTokenValidator jwtValidator;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(this.skip.getPathForDeviceIDFilter().contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String id=this.jwtValidator.jwtTokenDeviceIDTokenValidator(request);
		request.setAttribute(this.securityProperties.getDeviceIDRequestAttributeName(), id);
		filterChain.doFilter(request, response);

	}
	
	

}
