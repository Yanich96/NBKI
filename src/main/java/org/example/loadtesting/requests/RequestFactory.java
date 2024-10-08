package org.example.loadtesting.requests;

import java.net.http.HttpRequest;

public interface RequestFactory {
    HttpRequest createHttpRequest();
}

