package persistence;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import handlers.BookInformationIntentHandler;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamoDbPersistenceHandler {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);

    public Item retrieveItem(String tableName, String hashKeyName, Object value) {
        Table table = dynamoDB.getTable(tableName);
        Item item = null;
        try {
            item = table.getItem(hashKeyName, value);
        } catch (Exception e) {
            Logger.getLogger(BookInformationIntentHandler.class.getCanonicalName()).log(Level.SEVERE, e.getMessage());
        }

        return item;
    }

    public Item retrieveItem(String tableName, String hashKeyName, Object value, String projectExpression, Map<String, String> values) {
        Table table = dynamoDB.getTable(tableName);
        Item item = null;
        try {
            item = table.getItem(hashKeyName, value, projectExpression, values);
        } catch (Exception e) {
            Logger.getLogger(DynamoDbPersistenceHandler.class.getCanonicalName()).log(Level.SEVERE, e.getMessage());
        }

        return item;
    }



}
