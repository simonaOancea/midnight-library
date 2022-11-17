package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.request.RequestHelper;

import utils.SkillUtils;

import java.util.*;

public class CompletedBookRecommendationIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final RequestHelper helper = RequestHelper.forHandlerInput(handlerInput);
        return helper.getRequestType().equals("IntentRequest") &&
                helper.getIntentName().equals("BookRecommendationMatchIntent") &&
                helper.getDialogState().equals(DialogState.COMPLETED);
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        final Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        String speech = messages.getString("BOOK_RECOMMENDATION_FOUND");

        return handlerInput.getResponseBuilder()
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();
    }
}
