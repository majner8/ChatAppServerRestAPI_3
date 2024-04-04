package ChatAPP_Security.Filter.HttpRequestFilter;

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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ChatAPP_Security.Authorization.CustomSecurityContextHolder.CustomUserDetail;
import ChatAPP_Security.Authorization.JwtToken.AuthorizationUserTokenValue;
import ChatAPP_Security.Authorization.JwtToken.jwtToken;
import ChatAPP_Security.Properties.SecurityProperties;
import chatAPP_CommontPart.Log4j2.Log4j2;

@Component
public class jwtAuthorizationFilter extends OncePerRequestFilter {
	@Autowired
	private DefineFilterSkipPath.pathForAuthorizationFilterFilter skip;
	@Autowired
	private jwtToken.jwtTokenValidator jwtValidator;
	@Autowired
	private SecurityProperties prop;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(this.skip.getPathAuthorizationFilterFilter().contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		AuthorizationUserTokenValue token=this.jwtValidator.jwtTokenAuthorizationUserTokenValidator(request);
		ArrayList<SimpleGrantedAuthority> authority=new ArrayList<>();
		SimpleGrantedAuthority author=token.isUserEnable()?
				new SimpleGrantedAuthority(prop.isUserActiveAuthorityName())
				:
					new SimpleGrantedAuthority(prop.isUserUnActiveAuthorityName());
		authority.add(author);
		CustomUserDetail aut=CustomUserDetail.createUser(token,authority);
		Authentication auth = new UsernamePasswordAuthenticationToken(aut, null, aut.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		Log4j2.log.debug(Log4j2.MarkerLog.Security.getMarker(),"User is authorizated");
		filterChain.doFilter(request, response);

	}

}
