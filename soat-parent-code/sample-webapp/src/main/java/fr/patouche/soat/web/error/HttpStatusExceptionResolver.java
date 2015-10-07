package fr.patouche.soat.web.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : patouche - 06/10/15.
 */
public class HttpStatusExceptionResolver implements HandlerExceptionResolver {

    /** The class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpStatusExceptionResolver.class);

    @Override
    public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler, final Exception ex) {

        if (ex instanceof HttpStatusException) {
            final HttpStatusException httpError = (HttpStatusException) ex;
            response.setStatus(httpError.getHttpStatus().value());

            final Map<String, Object> model = new HashMap<>();
            model.put("status", httpError.getHttpStatus());
            model.put("message", httpError.getMessage());
            return new ModelAndView("layout/error", model);
        }

        return null;
    }
}
