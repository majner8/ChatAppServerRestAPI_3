package ChatAPP_Security.Filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import ChatAPP_Security.Authorization.SecurityProperties;
import ChatAPP_Security.Authorization.CustomSecurityContextHolder.CustomUserDetail;
import ChatAPP_Security.Authorization.JwtToken.AuthorizationUserTokenValue;
import ChatAPP_Security.Authorization.JwtToken.jwtToken;

public class jwtAuthorizationFilter extends OncePerRequestFilter {
	@Autowired
	private SkipPathServiceInterface skip;
	@Autowired 
	private jwtToken.jwtTokenValidator jwtValidator;
	@Autowired
	private SecurityProperties prop;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(skip.canSkipUserAuthorizationFilterPath(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		AuthorizationUserTokenValue token=this.jwtValidator.jwtTokenAuthorizationUserTokenValidator(request);
		ArrayList<SimpleGrantedAuthority> authority=new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority author=token.isUserEnable()? 
				new SimpleGrantedAuthority(prop.isUserActiveAuthorityName())
				:
					new SimpleGrantedAuthority(prop.isUserUnActiveAuthorityName());
		authority.add(author);
		CustomUserDetail aut=CustomUserDetail.createUser(token,authority);
		Authentication auth = new UsernamePasswordAuthenticationToken(aut, null, aut.getAuthorities()); 
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}
