package bg.paysafe.springboot.api.filters;

import bg.paysafe.springboot.api.handler.exception.ExceptionResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ExceptionResolver exceptionResolver;

    public ExceptionHandlerFilter(ExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException ext) {
            this.exceptionResolver.resolveRuntimeException(ext, response, HttpStatus.BAD_REQUEST);
        }
    }


}
