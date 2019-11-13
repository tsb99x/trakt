package io.github.tsb99x.trakt.config

import io.github.tsb99x.trakt.authorization.AuthorizationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorConfig(
    private val requestInterceptor: RequestInterceptor,
    private val authorizationInterceptor: AuthorizationInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(
        registry: InterceptorRegistry
    ) {

        registry.addInterceptor(requestInterceptor)
            .addPathPatterns("/api/v1/**")

        registry.addInterceptor(authorizationInterceptor)
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns("/api/v1/authenticate")

    }

}
