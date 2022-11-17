package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import utils.SkillUtils;

import java.util.Optional;
import java.util.ResourceBundle;


public class ExplainBookTypesIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("ExplainBookTypesIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        String optionOne = "fantasy";
        String optionTwo = "mystery";
        String optionThree = "science fiction";
        String optionFour = "self-help";
        String speech = String.format(messages.getString("BOOK_TYPES_EXAMPLES"), optionOne, optionTwo, optionThree, optionFour);

        return handlerInput.getResponseBuilder()
                //.addDelegateDirective()
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();
    }
}
