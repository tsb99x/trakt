package io.github.tsb99x.trakt.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorConfig(
    private val requestInterceptor: RequestInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(
        registry: InterceptorRegistry
    ) {

        registry.addInterceptor(requestInterceptor)
            .addPathPatterns("/api/v1/**")

    }

}
