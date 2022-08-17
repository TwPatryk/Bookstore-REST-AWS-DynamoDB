package pl.example.bookstore.services;

import com.amazonaws.services.dynamodbv2.document.*;
import pl.example.bookstore.model.AwsRequestHandler;
import pl.example.bookstore.model.Book;
import pl.example.bookstore.model.BookRequest;
import pl.example.bookstore.model.BookResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetAllBooksRequestHandler implements AwsRequestHandler {

    private static final String TABLE_NAME= "book";
    private static final String TABLE_ID="id";
    private final DynamoDB dynamoDB;

    public GetAllBooksRequestHandler(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }
    @Override
    public BookResponse handleRequest(BookRequest bookRequest) {
        BookResponse bookResponse = new BookResponse();
        Table table = this.dynamoDB.getTable(TABLE_NAME);
        ItemCollection<ScanOutcome> items = table.scan();
        Iterator<Item> itemIterator = items.iterator();
        List<Book> bookList = new ArrayList<>();
        while(itemIterator.hasNext()) {
            Item item = itemIterator.next();
            Book book = new Book();
            book.setId(item.getInt("id"));
            book.setTitle(item.get("title").toString());
            book.setCategory(item.get("category").toString());
            bookList.add(book);

        }
        bookResponse.setData(bookList.toString());
        return bookResponse;
    }
}
