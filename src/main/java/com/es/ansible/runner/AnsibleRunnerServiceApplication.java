package com.es.ansible.runner;

import com.es.ansible.runner.playbook.PlaybookController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackageClasses = PlaybookController.class)
public class AnsibleRunnerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnsibleRunnerServiceApplication.class, args);
	}

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("ansible-service-thread-");
        executor.initialize();
        return executor;
    }

}
