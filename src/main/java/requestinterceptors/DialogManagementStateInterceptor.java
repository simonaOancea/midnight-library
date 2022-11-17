package requestinterceptors;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.amazon.ask.model.DialogState;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;;

public class DialogManagementStateInterceptor implements RequestInterceptor {
    @Override
    public void process(HandlerInput handlerInput) {
        if (handlerInput.getRequestEnvelope().getRequest().getType().equals("IntentRequest")) {
            IntentRequest currentIntentRequest = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
            Intent currentIntent = currentIntentRequest.getIntent();
            if (currentIntentRequest.getType().equals("IntentRequest")
                    && currentIntentRequest.getIntent().getName().equals("BookRecommendationMatchIntent")
                    && currentIntentRequest.getDialogState() != DialogState.COMPLETED) {
                Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
                AttributesManager attributesManager = handlerInput.getAttributesManager();

                // If there are no session attributes we've never entered dialog management or this intent before.
                if (sessionAttributes.get("BookRecommendationMatchIntent") != null) {
                    Map<String, Object> sessionAttributesBookRecommendation = (Map) sessionAttributes.get("BookRecommendationMatchIntent");
                    ObjectMapper mapper = new ObjectMapper();
                    Intent previousIntent = mapper.convertValue(sessionAttributesBookRecommendation, Intent.class);
                    if (previousIntent != null) {
                        // We have slots to restore!
                        Map<String, Slot> savedSlots = previousIntent.getSlots();
                        if (savedSlots != null && savedSlots.size() > 0) {
                            for (Map.Entry<String, Slot> savedSlot : savedSlots.entrySet()) {
                                // We let the current intent's values override the session attributes. That way the user can override previously given values.
                                // This includes anything we have previously stored in their profile.
                                if (savedSlot.getValue().getValue() != null && currentIntent.getSlots().get(savedSlot.getKey()).getValue() == null) {
                                    currentIntent.getSlots().replace(savedSlot.getKey(), savedSlot.getValue());
                                }
                            }
                        }
                    }
                }
                sessionAttributes.put(currentIntent.getName(), currentIntent);
                attributesManager.setSessionAttributes(sessionAttributes);
            }
        }
    }
}
