package com.nil1one.config;// package com.nil1one.config;
//
// import org.elasticsearch.client.RestHighLevelClient;
// import org.springframework.context.annotation.Bean;
// import org.springframework.data.elasticsearch.client.ClientConfiguration;
// import org.springframework.data.elasticsearch.client.RestClients;
// import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
// public class RestClientConfig extends AbstractElasticsearchConfiguration {
//
//     @Override
//     @Bean
//     public RestHighLevelClient elasticsearchClient() {
//
//         final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//             .connectedTo("localhost:9200")
//             .build();
//
//         return RestClients.create(clientConfiguration).rest();
//     }
// }