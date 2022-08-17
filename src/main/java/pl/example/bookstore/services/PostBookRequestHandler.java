package pl.example.bookstore.services;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import pl.example.bookstore.model.AwsRequestHandler;
import pl.example.bookstore.model.Book;
import pl.example.bookstore.model.BookRequest;
import pl.example.bookstore.model.BookResponse;

public class PostBookRequestHandler implements AwsRequestHandler {

    private static final String TABLE_NAME="book";

    private final DynamoDB dynamoDB;

    public PostBookRequestHandler(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }
    @Override
    public BookResponse handleRequest(BookRequest bookRequest) {
        BookResponse bookResponse = new BookResponse();
        Book book = bookRequest.getBook();
        this.dynamoDB.getTable(TABLE_NAME)
                .putItem(new PutItemSpec().withItem(new Item()
                        .withNumber("id", book.getId())
                        .withString("title", book.getTitle())
                        .withString("category", book.getCategory())
                        .withBoolean("isAvailable", book.isAvailable())));
        bookResponse.setData(bookRequest.getBook().toString());
        bookResponse.setHttpMethod(bookRequest.getHttpMethod());
        return bookResponse;
    }
}
