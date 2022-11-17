package requestinterceptors;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLogger implements RequestInterceptor {

    private static final Logger requestLogger = LoggerFactory.getLogger(RequestLogger.class);

    @Override
    public void process(HandlerInput handlerInput) {
        requestLogger.info(String.format("Request Envelope: %s", handlerInput.getRequestEnvelope()));
    }

}
