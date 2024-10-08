package org.example.loadtesting.requests;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class AddRequestFactory implements RequestFactory {
    private final List<String> data;
    private int index;

    public AddRequestFactory(ArrayList<String> data) {
        this.data = data;
        this.index = 0;
    }

    @Override
    public HttpRequest createHttpRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/add" + "?record=" + data.get(this.index++ % data.size())))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }
}

