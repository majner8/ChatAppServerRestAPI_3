package ChatAPP_Security.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import ChatAPP_Security.Authorization.JwtToken.jwtToken;

public class jwtAuthorizationFilter extends OncePerRequestFilter {
	@Autowired
	private SkipPathServiceInterface skip;
	@Autowired 
	private jwtToken.jwtTokenValidator jwtValidator;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(skip.canSkipUserAuthorizationFilterPath(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		DecodedJWT token=this.jwtValidator.jwtTokenAuthorizationUserTokenValidator(request);

	}

}
