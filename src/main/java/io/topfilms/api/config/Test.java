package io.topfilms.api.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    @Value("${DATASOURCE_URL}")
    private String url;

    @Value("${DB_USERNAME}")
    private String u;

    @Value("${DB_PASSWORD}")
    private String p;

    @PostConstruct
    private void test() {
        LOGGER.info(url);
        LOGGER.info(u);
        LOGGER.info(p);
    }

}
