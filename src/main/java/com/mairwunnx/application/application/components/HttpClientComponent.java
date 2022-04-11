package com.mairwunnx.application.application.components;

import lombok.Getter;

import java.net.http.HttpClient;
import java.time.Duration;

// TODO: Move in the services layer.
public final class HttpClientComponent {
    @Getter
    private final HttpClient httpClient;

    public HttpClientComponent() {
        httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }
}
