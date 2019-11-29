package io.github.tsb99x.trakt.api.controller

import io.github.tsb99x.trakt.core.API_V1_PATH
import io.github.tsb99x.trakt.core.USER_ATTRIBUTE
import io.github.tsb99x.trakt.core.data.UserEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API_V1_PATH)
class DummyController {

    @GetMapping("/dummy")
    fun getDummy(
        @RequestAttribute(USER_ATTRIBUTE) userEntity: UserEntity
    ): UserEntity {

        return userEntity

    }

}
