package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.model.dialog.DelegateDirective;
import com.amazon.ask.request.RequestHelper;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InProgressBookRecommendationIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final RequestHelper helper = RequestHelper.forHandlerInput(handlerInput);
        return helper.getRequestType().equals("IntentRequest") &&
                helper.getIntentName().equals("BookRecommendationMatchIntent") &&
                !helper.getDialogState().equals(DialogState.COMPLETED);
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final Intent currentIntent = intentRequest.getIntent();

        return handlerInput.getResponseBuilder()
                .addDirective(DelegateDirective.builder().withUpdatedIntent(currentIntent).build())
                //.addDelegateDirective(currentIntent)
                .withShouldEndSession(false)
                .build();
     }
}

