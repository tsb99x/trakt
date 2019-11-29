package io.github.tsb99x.trakt.core

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["io.github.tsb99x.trakt"])
class CoreTestConfig
