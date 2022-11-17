package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import utils.SkillUtils;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static utils.Attributes.USER_NAME;

public class LaunchHandler implements LaunchRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        final String helpMessage = messages.getString("HELP");
        String welcomeMessage = "";
        Map<String, Object> persistenceAttributes = handlerInput.getAttributesManager().getPersistentAttributes();
        if (persistenceAttributes.get(USER_NAME) != null && !persistenceAttributes.get(USER_NAME).toString().isBlank()) {
            welcomeMessage = String.format(messages.getString("WELCOME_WITH_NAME"),
                    persistenceAttributes.get(USER_NAME).toString());
        } else {
            welcomeMessage = messages.getString("WELCOME");
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(welcomeMessage)
                .withReprompt(helpMessage)
                .withShouldEndSession(false)
                .build();
    }
}
