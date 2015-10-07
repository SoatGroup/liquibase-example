package fr.patouche.soat.web.error;

import org.springframework.http.HttpStatus;

/**
 * @author : patouche - 06/10/15.
 */
public class HttpStatusException extends RuntimeException {

    private final HttpStatus httpStatus;

    public HttpStatusException(HttpStatus httpStatus, final String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatusException(HttpStatus httpStatus, final String message, final Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
