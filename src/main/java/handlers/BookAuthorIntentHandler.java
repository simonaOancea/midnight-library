package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import utils.SkillUtils;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static utils.Attributes.*;

public class BookAuthorIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("BookAuthorIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        String authorNotFoundSpeech = messages.getString("AUTHOR_NOT_FOUND");
        if (sessionAttributes.size() > 0 && sessionAttributes.containsKey(CURRENT_BOOK_SUMMARY)) {
            String bookAuthor = sessionAttributes.get(CURRENT_BOOK_AUTHOR).toString();
            String bookTitle = sessionAttributes.get(CURRENT_BOOK_TITLE).toString();
            if (bookAuthor != null && !bookAuthor.isBlank()) {
                String authorFoundSpeech = String.format(messages.getString("AUTHOR_FOUND"), bookTitle, bookAuthor);
                return handlerInput.getResponseBuilder()
                        .withSpeech(authorFoundSpeech)
                        .withShouldEndSession(false)
                        .build();
            }
        }
        return handlerInput.getResponseBuilder()
                .withSpeech(authorNotFoundSpeech)
                .withShouldEndSession(false)
                .build();
    }
}
