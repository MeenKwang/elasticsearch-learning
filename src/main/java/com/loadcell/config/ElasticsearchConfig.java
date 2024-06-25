package com.loadcell.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.time.Duration;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.loadcell.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {


    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl("7bc1f23b6b8ab2796aa2bff2fdb090f9671b9e1cd325c8af702e933b00eb3614") //add the generated sha-256 fingerprint
                .withBasicAuth("elastic", "QTzC8Fjd30GSZ2WThjr+")
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(10))
                .build();
    }
}
