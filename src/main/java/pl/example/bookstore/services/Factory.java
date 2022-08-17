package pl.example.bookstore.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import pl.example.bookstore.model.AwsRequestHandler;
import pl.example.bookstore.model.BookRequest;
import pl.example.bookstore.model.BookResponse;

public class Factory {

    private final AwsRequestHandler getBookByIdRequestHandler;
    private final AwsRequestHandler postBookRequestHandler;
    private final AwsRequestHandler getAllBooksRequestHandler;
    private final AwsRequestHandler deleteBookRequestHandler;

    public Factory() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        this.getBookByIdRequestHandler = new GetBookByIdRequestHandler(dynamoDB);
        this.postBookRequestHandler = new PostBookRequestHandler(dynamoDB);
        this.getAllBooksRequestHandler = new GetAllBooksRequestHandler(dynamoDB);
        this.deleteBookRequestHandler = new DeleteBookRequestHandler(dynamoDB);
    }

    public BookResponse getResponse(BookRequest bookRequest) {
        String httpMethod = bookRequest.getHttpMethod();

        if(httpMethod.equals("GET")) {
            if(isNumeric(bookRequest.getEndpoint())) {
                return getBookByIdRequestHandler.handleRequest(bookRequest);
            } else {
                return getAllBooksRequestHandler.handleRequest(bookRequest);
            }
        }
        if(httpMethod.equals("POST")) {
            return postBookRequestHandler.handleRequest(bookRequest);
        }
        if(httpMethod.equals("DELETE")) {
            return deleteBookRequestHandler.handleRequest(bookRequest);
        }
        return null;
    }

    private boolean isNumeric(String num) {
        if (num == null) {
            return false;
        }
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
