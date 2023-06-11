package com.kmhoon.licensingservice.configuration;

import com.kmhoon.licensingservice.utils.KeyCloakRestTemplateInterceptor;
import com.kmhoon.licensingservice.utils.UserContextInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    /**
     * 로드 밸런서를 지원하는 RestTemplate
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors =
                restTemplate.getInterceptors();
        if(interceptors == null) {
            restTemplate.setInterceptors(Arrays.asList(new UserContextInterceptor(), new KeyCloakRestTemplateInterceptor()));
        } else {
            interceptors.addAll(Arrays.asList(new UserContextInterceptor(), new KeyCloakRestTemplateInterceptor()));
            restTemplate.setInterceptors(interceptors);
        }
        return restTemplate;
    }
}
