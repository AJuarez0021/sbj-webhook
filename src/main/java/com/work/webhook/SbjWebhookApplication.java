package com.work.webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = { "com.work.webhook" })
@EntityScan(basePackages = "com.work.webhook.model")
@EnableJpaRepositories(basePackages = "com.work.webhook.repository")
@EnableJpaAuditing
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@EnableFeignClients
public class SbjWebhookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbjWebhookApplication.class, args);
	}

}
