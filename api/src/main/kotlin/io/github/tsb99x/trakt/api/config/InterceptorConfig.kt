package io.github.tsb99x.trakt.api.config

import io.github.tsb99x.trakt.api.interceptor.AuthorizationInterceptor
import io.github.tsb99x.trakt.api.interceptor.RequestInterceptor
import io.github.tsb99x.trakt.core.API_V1_PATH
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
            .addPathPatterns("$API_V1_PATH/**")

        registry.addInterceptor(authorizationInterceptor)
            .addPathPatterns("$API_V1_PATH/**")
            .excludePathPatterns("$API_V1_PATH/auth/login")

    }

}
