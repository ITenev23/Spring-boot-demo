package bg.paysafe.springboot.api.filters;

import bg.paysafe.springboot.api.constant.ConfigurationConstants;
import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.payload.user.UserLoginRequestModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.impl.PublicClaims;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static bg.paysafe.springboot.api.constant.ConfigurationConstants.*;
import static bg.paysafe.springboot.api.constant.ConfigurationConstants.Headers.ACCESS_NAME;
import static bg.paysafe.springboot.api.constant.ConfigurationConstants.Headers.ACCESS_VALUE;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    private final AuthenticationManager authenticationManager;

    private final String secretKey;

    private final UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(ObjectMapper objectMapper,
                                   AuthenticationManager authenticationManager,
                                   String secretKey,
                                   UserDetailsService userDetailsService) {
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws AuthenticationException {
        try {
            var inputCredentials = this.objectMapper.readValue(
                    req.getInputStream(),
                    UserLoginRequestModel.class
            );

            var user = this.userDetailsService.loadUserByUsername(inputCredentials.getUsername());
            res.addHeader(ACCESS_NAME, ACCESS_VALUE);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user,
                            inputCredentials.getPassword(),
                            user.getAuthorities()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth
    ) throws IOException {
        var userEntity = ((User) auth.getPrincipal());

        var token = JWT.create()
                .withSubject(userEntity.getEmail())
                .withKeyId(userEntity.getId().toString())
                .withClaim(ROLES, userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + ConfigurationConstants.EXPIRATION_TIME)
                )
                .sign(HMAC512(this.secretKey.getBytes()));

        res.addHeader(ConfigurationConstants.HEADER_AUTHORIZATION, ConfigurationConstants.TOKEN_PREFIX + token);

        res.setContentType(PublicClaims.CONTENT_TYPE);
        res.setCharacterEncoding(UTF);
        res.getWriter().write(this.objectMapper.writeValueAsString(Map.of(LOGIN_USER_ID, userEntity.getId())));
    }
}
