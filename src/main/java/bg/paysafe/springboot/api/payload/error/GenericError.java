package bg.paysafe.springboot.api.payload.error;

import java.util.List;

public class GenericError {

    private final int errorCode;

    private final List<String> errors;

    public GenericError(int errorCode, List<String> errors) {
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public List<String> getErrors() {
        return errors;
    }
}
