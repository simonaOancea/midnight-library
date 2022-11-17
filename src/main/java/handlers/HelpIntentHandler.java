package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import utils.SkillUtils;

import java.util.Optional;
import java.util.ResourceBundle;

public class HelpIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("AMAZON.HelpIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        String helpSpeech = String.format(messages.getString("HELP"));
        return handlerInput.getResponseBuilder()
                .withSpeech(helpSpeech)
                .withReprompt("What would you like to do?")
                .build();
    }
}
