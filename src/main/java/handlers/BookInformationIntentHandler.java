package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import com.amazonaws.services.dynamodbv2.document.Item;
import persistence.DynamoDbPersistenceHandler;
import utils.SkillUtils;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.Attributes.*;


public class BookInformationIntentHandler implements IntentRequestHandler {

    private static String tableName = "midnight_library_books";
    private DynamoDbPersistenceHandler persistenceHandler;

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("BookInformationIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        final String bookTitle = intentRequest.getIntent().getSlots().get("title").getValue();
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        String bookNotFoundSpeech = messages.getString("BOOK_NOT_FOUND");
        String author = "";
        String bookSummary = "";
        String avgRating = "";
        persistenceHandler = new DynamoDbPersistenceHandler();
        if (bookTitle != null && !bookTitle.isEmpty()) {
            Item item = persistenceHandler.retrieveItem(tableName, "title", bookTitle,
                    "title, author, book_description, avg_rating", null);
            if (item != null) {
                if (item.get("author") != null) {
                    author = item.get("author").toString();
                }
                if (item.get("book_description") != null) {
                    bookSummary = item.get("book_description").toString();
                }
                if (item.get("avg_rating") != null) {
                    avgRating = item.get("avg_rating").toString();
                }
                sessionAttributes.put(CURRENT_BOOK_TITLE, bookTitle);
                sessionAttributes.put(CURRENT_BOOK_AUTHOR, author);
                sessionAttributes.put(CURRENT_BOOK_SUMMARY, bookSummary);
                sessionAttributes.put(CURRENT_BOOK_RATING, avgRating);
                String bookFoundSpeech = String.format(messages.getString("BOOK_FOUND"), bookTitle);
                return handlerInput.getResponseBuilder()
                        .withSpeech(bookFoundSpeech)
                        .withShouldEndSession(false)
                        .build();
            } else {
                Logger.getLogger(BookInformationIntentHandler.class.getCanonicalName()).log(Level.SEVERE, "Item is null");
                bookNotFoundSpeech = String.format(messages.getString("BOOK_NOT_FOUND"), bookTitle);
            }
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(bookNotFoundSpeech)
                .withShouldEndSession(false)
                .build();
    }

}
