package com.mairwunnx.application;

import lombok.Getter;

import java.net.http.HttpClient;
import java.time.Duration;

public final class HttpClientComponent {
    private static volatile HttpClientComponent instance;

    @Getter
    private final HttpClient httpClient;

    private HttpClientComponent() {
        httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }

    public static HttpClientComponent getInstance() {
        var localInstance = instance;
        if (localInstance == null) {
            synchronized (HttpClientComponent.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new HttpClientComponent();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }
}
