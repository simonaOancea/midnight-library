package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import utils.Attributes;
import utils.SkillUtils;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class NameIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("NameIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        String speechOutput = messages.getString("INTRODUCTION_WITH_NAME");
        final String userName = intentRequest.getIntent().getSlots().get("name").getValue();
        Map<String, Object> persistenceAttributes = handlerInput.getAttributesManager().getPersistentAttributes();
        if (userName != null) {
            persistenceAttributes.put(Attributes.USER_NAME, userName);
            handlerInput.getAttributesManager().setPersistentAttributes(persistenceAttributes);
            handlerInput.getAttributesManager().savePersistentAttributes();
            speechOutput = String.format(messages.getString("INTRODUCTION_WITH_NAME"), userName);
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(speechOutput)
                .withShouldEndSession(false)
                .build();
    }
}
