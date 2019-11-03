package io.github.tsb99x.trakt.authentication

import io.github.tsb99x.trakt.REQUEST_ID
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1")
class AuthenticationController(
    private val service: AuthenticationService
) {

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthenticationRequest,
        @RequestAttribute(REQUEST_ID) requestId: UUID
    ): AuthenticationResponse {

        val apiToken = service.authenticate(request.username, request.password)
        return AuthenticationResponse(requestId, apiToken.id)

    }

}
