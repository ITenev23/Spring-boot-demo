package bg.paysafe.springboot.api.handler;

import bg.paysafe.springboot.api.exception.GenericException;
import bg.paysafe.springboot.api.payload.error.ErrorViewModel;
import bg.paysafe.springboot.api.payload.error.GenericError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GenericException.class)
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex,
            WebRequest request
    ) {
        var errorViewModel = new ErrorViewModel();
        errorViewModel.add(ex.getMessage());

        return this.handleExceptionInternal(
                ex,
                errorViewModel,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request
        );
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return new ResponseEntity<>(
                new GenericError(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList())
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleGlobalExceptionErrors(Throwable throwable) {
        var lastException = throwable;
        while (!(lastException instanceof GenericException) && lastException.getCause() != null) {
            lastException = lastException.getCause();
        }

        if (lastException instanceof GenericException) {
            return new ResponseEntity<>(
                    new GenericError(
                            HttpStatus.BAD_REQUEST.value(),
                            lastException.getMessage() == null || lastException.getMessage().isEmpty()
                                    ? List.of(lastException.getClass().getSimpleName())
                                    : List.of(lastException.getMessage())
                    ),
                    new HttpHeaders(),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (lastException instanceof IllegalArgumentException) {
            return new ResponseEntity<>(
                    new GenericError(
                            HttpStatus.BAD_REQUEST.value(),
                            List.of("AlteredRequestDetectedException")
                    ),
                    new HttpHeaders(),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                new GenericError(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        List.of("UnrecoverableException_" + lastException.getClass().getName())
                ),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

