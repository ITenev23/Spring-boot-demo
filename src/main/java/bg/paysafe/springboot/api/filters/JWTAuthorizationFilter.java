package bg.paysafe.springboot.api.filters;

import bg.paysafe.springboot.api.constant.ConfigurationConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secretKey;

    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  String secretKey,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.secretKey = secretKey;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain
    ) throws IOException, ServletException {
        var header = req.getHeader(ConfigurationConstants.HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(ConfigurationConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        var authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        var token = request.getHeader(ConfigurationConstants.HEADER_AUTHORIZATION);
        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(this.secretKey.getBytes()))
                    .build()
                    .verify(token.replace(ConfigurationConstants.TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                var entity = this.userDetailsService.loadUserByUsername(user);
                return new UsernamePasswordAuthenticationToken(
                        entity,
                        null,
                        entity.getAuthorities()
                );
            }

            return null;
        }

        return null;
    }
}
