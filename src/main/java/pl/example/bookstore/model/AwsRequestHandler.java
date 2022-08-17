package pl.example.bookstore.model;

public interface AwsRequestHandler {

    public BookResponse handleRequest(BookRequest bookRequest);
}
