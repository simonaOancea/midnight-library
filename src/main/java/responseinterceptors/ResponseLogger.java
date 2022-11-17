package responseinterceptors;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ResponseLogger implements ResponseInterceptor {

    private static final Logger responseLogger = LoggerFactory.getLogger(ResponseLogger.class);

    @Override
    public void process(HandlerInput handlerInput, Optional<Response> response) {
        responseLogger.info(String.format("Response: %s", response));
    }
}
