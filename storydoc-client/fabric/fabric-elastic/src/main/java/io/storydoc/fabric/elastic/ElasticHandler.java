package io.storydoc.fabric.elastic;

import io.storydoc.fabric.core.ComponentHandler;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.*;

import java.io.IOException;

public class ElasticHandler extends ComponentHandler<ElasticComponent, ElasticComponentDescriptor> {

    public ElasticHandler() {
        super(ElasticComponent.class);
    }

    @Override
    @SneakyThrows
    public void createBundle(ElasticComponentDescriptor componentDescriptor) {

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("??", "**"));

        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback = new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        };

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(httpClientConfigCallback);

        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);

        RestClient restClient = client.getLowLevelClient();
        Response response = null;
        try {
            response = restClient.performRequest(new Request("GET", "/_cat/indices?v&format=json"));
        } catch (IOException e) {
        }

     }

}