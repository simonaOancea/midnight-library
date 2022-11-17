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


public class BookRatingIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("BookRatingIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        String ratingNotFoundSpeech = messages.getString("RATING_NOT_FOUND");
        String bookTitle = "";
        if (sessionAttributes.size() > 0 && sessionAttributes.containsKey(CURRENT_BOOK_RATING)) {
            bookTitle = sessionAttributes.get(CURRENT_BOOK_TITLE).toString();
            String averageRating = sessionAttributes.get(CURRENT_BOOK_RATING).toString();
            if (averageRating != null && !averageRating.isBlank()) {
                String ratingFoundSpeech = String.format(messages.getString("RATING_FOUND"), bookTitle, averageRating);
                return handlerInput.getResponseBuilder()
                        .withSpeech(ratingFoundSpeech)
                        .withShouldEndSession(false)
                        .build();
            }
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(ratingNotFoundSpeech)
                .withShouldEndSession(false)
                .build();
    }
}
