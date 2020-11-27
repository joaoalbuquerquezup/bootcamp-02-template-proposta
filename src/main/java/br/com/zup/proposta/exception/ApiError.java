package br.com.zup.proposta.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ApiError {

    private final String field;
    private final String errorMessage;

    public ApiError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }
}
