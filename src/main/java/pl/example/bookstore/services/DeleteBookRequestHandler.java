package pl.example.bookstore.services;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import pl.example.bookstore.model.AwsRequestHandler;
import pl.example.bookstore.model.Book;
import pl.example.bookstore.model.BookRequest;
import pl.example.bookstore.model.BookResponse;

public class DeleteBookRequestHandler implements AwsRequestHandler {

    private static final String TABLE_NAME= "book";
    private static final String TABLE_ID="id";
    private final DynamoDB dynamoDB;

    public DeleteBookRequestHandler(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }
    @Override
    public BookResponse handleRequest(BookRequest bookRequest) {
        BookResponse bookResponse = new BookResponse();
        Table table = this.dynamoDB.getTable(TABLE_NAME);
        GetItemSpec itemSpec = new GetItemSpec().withPrimaryKey(TABLE_ID, Integer.parseInt(bookRequest.getEndpoint()));
        try {
            Item item = table.getItem(itemSpec);
            table.deleteItem(TABLE_ID, Integer.parseInt(item.get(TABLE_ID).toString()));
            bookResponse.setData("deleted successfully!");
            return bookResponse;
        } catch (NullPointerException e){
            bookResponse.setData("deletion failed");
            return bookResponse;
        }
    }
}
