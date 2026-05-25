package dev.chinh.itcanclick.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.awt.Robot

@Configuration
class TaskConfig {

    @Bean
    public fun robot() = Robot()
}