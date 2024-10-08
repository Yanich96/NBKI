package org.example.loadtesting;

import org.example.loadtesting.requests.RequestFactory;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;

public class Tank {
    private final int threadPoolSize;
    private ScheduledThreadPoolExecutor threadPool;

    public Tank(int threads) {
        this.threadPoolSize = threads;
        threadPool = new ScheduledThreadPoolExecutor(this.threadPoolSize);
    }

    HttpClient httpClient = HttpClient.newHttpClient();

    public List<Long> fire(RequestFactory factory, int countRequest) {
        List<Future<Long>> futures = new ArrayList<>();
        for (int i = 0; i < countRequest; i++) {
            var request = factory.createHttpRequest();
            var future = this.threadPool.submit(() -> {
                long first = System.nanoTime();
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                return System.nanoTime() - first;
            });
            futures.add(future);
        }
        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted()
                .collect(Collectors.toList());
    }

    public void shutdown() {
        threadPool.shutdown();
    }
}
