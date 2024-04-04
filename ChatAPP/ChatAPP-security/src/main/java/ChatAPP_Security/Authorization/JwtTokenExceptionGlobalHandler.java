package ChatAPP_Security.Authorization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.UnsupportedJwtException;

@ControllerAdvice
public class JwtTokenExceptionGlobalHandler {

	 @ExceptionHandler(UnsupportedJwtException.class)
	    public ResponseEntity<Object> handleCustomAuthenticationException(UnsupportedJwtException ex) {
	        // Construct and return the ResponseEntity with appropriate status code and body
		 	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	 	}
}
