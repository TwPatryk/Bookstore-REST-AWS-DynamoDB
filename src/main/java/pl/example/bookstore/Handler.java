package pl.example.bookstore;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import pl.example.bookstore.model.BookRequest;
import pl.example.bookstore.model.BookResponse;
import pl.example.bookstore.services.Factory;

public class Handler implements RequestHandler<BookRequest, BookResponse> {


    @Override
    public BookResponse handleRequest(BookRequest bookRequest, Context context) {
        Factory factory = new Factory();
        return factory.getResponse(bookRequest);
    }
}
