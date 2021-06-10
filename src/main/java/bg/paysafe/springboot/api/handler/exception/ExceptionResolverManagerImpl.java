package bg.paysafe.springboot.api.handler.exception;

import bg.paysafe.springboot.api.payload.error.ErrorViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ExceptionResolverManagerImpl implements ExceptionResolver {

    private final ObjectMapper mapper;

    public ExceptionResolverManagerImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void resolveRuntimeException(RuntimeException e, HttpServletResponse response, HttpStatus responseStatus) {
        response.setStatus(responseStatus.value());
        try {
            response.getWriter().write(
                    this.convertObjectToJson(
                            new ErrorViewModel(
                                    e.getClass().getSimpleName()
                            )
                    )
            );
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return this.mapper.writeValueAsString(object);
    }
}
