package io.github.tsb99x.trakt.api.controller

import io.github.tsb99x.trakt.api.model.LoginRequest
import io.github.tsb99x.trakt.api.model.LoginResponse
import io.github.tsb99x.trakt.core.API_TOKEN_ID_ATTRIBUTE
import io.github.tsb99x.trakt.core.API_V1_PATH
import io.github.tsb99x.trakt.core.REQUEST_ID
import io.github.tsb99x.trakt.core.service.AuthorizationService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("$API_V1_PATH/auth")
class AuthController(
    private val authorizationService: AuthorizationService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
        @RequestAttribute(REQUEST_ID) requestId: UUID
    ): LoginResponse {

        val apiToken = authorizationService.login(request.username, request.password)
        return LoginResponse(requestId, apiToken.id)

    }

    @PostMapping("/logout")
    fun logout(
        @RequestAttribute(API_TOKEN_ID_ATTRIBUTE) apiTokenId: UUID
    ) {

        authorizationService.logout(apiTokenId)

    }

}
