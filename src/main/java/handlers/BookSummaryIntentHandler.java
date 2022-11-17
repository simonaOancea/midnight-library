package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import utils.SkillUtils;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static utils.Attributes.CURRENT_BOOK_SUMMARY;
import static utils.Attributes.CURRENT_BOOK_TITLE;

public class BookSummaryIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("BookSummaryIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        String summaryNotFoundSpeech = messages.getString("BOOK_SUMMARY_NOT_FOUND");

        if (sessionAttributes.size() > 0 && sessionAttributes.containsKey(CURRENT_BOOK_SUMMARY)) {
            String bookSummary = sessionAttributes.get(CURRENT_BOOK_SUMMARY).toString();
            String bookTitle = (String) sessionAttributes.get(CURRENT_BOOK_TITLE);
            if (bookSummary != null && !bookSummary.isBlank()) {
                String summaryFoundSpeech = String.format(messages.getString("BOOK_SUMMARY_FOUND"), bookTitle, bookSummary);
                return handlerInput.getResponseBuilder()
                        .withSpeech(summaryFoundSpeech)
                        .withShouldEndSession(false)
                        .build();
            }
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(summaryNotFoundSpeech)
                .withShouldEndSession(false)
                .build();
    }
}
