package org.example.loadtesting;

import org.example.loadtesting.requests.AddRequestFactory;
import org.example.loadtesting.requests.GetRequestFactory;
import org.example.loadtesting.requests.RequestFactory;

import java.util.ArrayList;

public class LoadTesting {
    public static void main(String[] args) {
        int requestsCount = 1000000;
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < requestsCount; i++) {
            data.add(String.valueOf(i));
        }
        RequestFactory addFactory = new AddRequestFactory(data);
        RequestFactory getFactory = new GetRequestFactory(0, requestsCount);

        var threads = Math.min(Runtime.getRuntime().availableProcessors(), 100);
        Tank tank = new Tank(threads);
        System.out.printf("tank started at %d threads%n", threads);

        Statistics statisticsAdd = new Statistics(
                tank.fire(addFactory, requestsCount));

        System.out.println("Add Request finished");

        Statistics statisticsGet = new Statistics(
                tank.fire(getFactory, requestsCount*10));

        System.out.println("Get Request finished");

        System.out.println("Add Requests: " + statisticsAdd);
        System.out.println("Get Requests: " + statisticsGet);
        tank.shutdown();
    }
}
