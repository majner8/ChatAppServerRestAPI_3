package ChatAPP_Security.Filter.HttpRequestFilter;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import ChatAPP_Security.Authorization.JwtToken.jwtToken;
import ChatAPP_Security.Filter.HttpRequestFilter.DefineFilterSkipPath.pathForDeviceIdFilter;
import ChatAPP_Security.Properties.SecurityProperties;
import chatAPP_CommontPart.Log4j2.Log4j2;

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
		if(Log4j2.log.isTraceEnabled()) {
			Log4j2.log.trace(Log4j2.MarkerLog.RequestLogAuthorization.getMarker(),"I am writing header of request");
			int i=1;
			Iterator<String >it=request.getHeaderNames().asIterator();
			while(it.hasNext()) {
				String value=it.next();
				Log4j2.log.trace(Log4j2.MarkerLog.RequestLogAuthorization.getMarker(),
						String.format("HeaderName: %s  value: %s",value,request.getHeader(value)));
				i++;
			}
		}
		
		
		if(this.skip.getPathForDeviceIDFilter().contains(request.getRequestURI())) {
			if(request.getHeader(this.securityProperties.getTokenDeviceIdHeaderName())!=null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			filterChain.doFilter(request, response);
			return;
		}
		
		String id=this.jwtValidator.jwtTokenDeviceIDTokenValidator(request);
		request.setAttribute(this.securityProperties.getDeviceIDRequestAttributeName(), id);
		filterChain.doFilter(request, response);

	}
	
	

}
