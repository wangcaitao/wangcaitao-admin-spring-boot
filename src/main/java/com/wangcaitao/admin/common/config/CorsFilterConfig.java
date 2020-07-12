package com.wangcaitao.admin.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcaitao
 */
@Configuration
public class CorsFilterConfig {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        List<String> allowedMethods = new ArrayList<>();
        allowedMethods.add(HttpMethod.GET.name());
        allowedMethods.add(HttpMethod.POST.name());
        allowedMethods.add(HttpMethod.PUT.name());
        allowedMethods.add(HttpMethod.DELETE.name());
        allowedMethods.add(HttpMethod.OPTIONS.name());
        config.setAllowedMethods(allowedMethods);

        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(false);
        config.setMaxAge(1800L);

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
