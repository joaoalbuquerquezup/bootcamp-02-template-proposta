package br.com.zup.proposta.utils;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class AssertWithHttpStatus {

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">Assert.isNull(value, HttpStatus.BAD_REQUEST);</pre>
     * @param object the object to check
     * @param status the exception status to use if the assertion fails
     * @throws ResponseStatusException if the object is not {@code null}
     */
    public static void isNull(@Nullable Object object, HttpStatus status) {
        if (object != null) {
            throw new ResponseStatusException(status);
        }
    }

    /**
     * Assert that an object is not {@code null}.
     * <pre class="code">Assert.notNull(clazz, HttpStatus.UNPROCESSABLE_ENTITY);</pre>
     * @param object the object to check
     * @param status the exception status to use if the assertion fails
     * @throws ResponseStatusException if the object is {@code null}
     */
    public static void notNull(@Nullable Object object, HttpStatus status) {
        if (object == null) {
            throw new ResponseStatusException(status);
        }
    }
}
