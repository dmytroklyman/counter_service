package de.klyman.increment;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.klyman.increment.exception.InvalidCredentialsException;
import de.klyman.increment.model.ApiError;
import de.klyman.increment.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {
	
	private final Integer TOKEN_LIFETIME = 600000;

	/**
	 * Login method
	 * 
	 * Just as an example it contains only 1 user: admin/admin
	 * Of course it is not suitable for production - at least DB storage with password hash is required 
	 * 
	 * @param String username
	 * @param String pwd
	 * @return ResponseEntity<Object>
	 * @throws InvalidCredentialsException
	 */
	@PostMapping("user")
	public ResponseEntity<Object> login(@RequestParam("user") String username, @RequestParam("password") String pwd) throws InvalidCredentialsException {
		String token = getJWTToken(username);
		
		// It is hardcoded here just as an example
		if (!username.equals("admin") || !pwd.equals("admin")) {
			System.out.println("Username: " + username);
			System.out.println("Pwd: " + pwd);
			InvalidCredentialsException ex = new InvalidCredentialsException("Invalid credentials given");
			ApiError errorResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
			
			return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
		}

		User user = new User(username, token);	
		return ResponseEntity.ok(user);
	}

	/**
	 * 
	 * @param String username
	 * @return String
	 */
	private String getJWTToken(String username) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("incrementServiceJWT")
				.setSubject(username)
				.claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFETIME))
				.signWith(SignatureAlgorithm.HS512, JWTAuthorizationFilter.SECRET_KEY.getBytes()).compact();

		return "Bearer " + token;
	}
}
