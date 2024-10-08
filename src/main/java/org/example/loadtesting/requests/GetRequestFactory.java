package org.example.loadtesting.requests;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Random;

public class GetRequestFactory implements RequestFactory {
    private long minId;
    private long maxId;
    private Random random = new Random();

    public GetRequestFactory(long minId, long maxId) {
        this.minId = minId;
        this.maxId = maxId;
    }

    @Override
    public HttpRequest createHttpRequest() {
        long id = this.random.nextLong(this.maxId - this.minId) + this.minId;
        return HttpRequest.newBuilder()
                .uri(URI.create(("http://localhost:8080/get?id=" + id)))
                .build();
    }
}