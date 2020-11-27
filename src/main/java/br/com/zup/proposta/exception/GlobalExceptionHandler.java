package br.com.zup.proposta.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String GENERIC_ERROR = "Ocorreu um erro inesperado no sistema. Tente mais tarde";

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ApiError> handleControllerValidation(MethodArgumentNotValidException exception) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return fieldErrors.stream().map(fieldError -> {
            String message = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            return new ApiError(fieldError.getField(), message);
        }).collect(Collectors.toList());
    }

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ApiError> handleInvalidState(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        return constraintViolations
                .stream()
                .map(violation -> new ApiError(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUncaught(Exception ex) {
        Throwable cause = ex.getCause();
        LOGGER.error(cause.toString() + " | " + cause.getMessage(), cause);

        Map<String, String> ERROR = Map.of("message", GlobalExceptionHandler.GENERIC_ERROR);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ERROR);
    }
}
