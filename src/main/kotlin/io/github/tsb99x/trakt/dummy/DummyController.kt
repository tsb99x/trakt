package io.github.tsb99x.trakt.dummy

import io.github.tsb99x.trakt.USER_ATTRIBUTE
import io.github.tsb99x.trakt.data.UserEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class DummyController {

    @GetMapping("/dummy")
    fun getDummy(
        @RequestAttribute(USER_ATTRIBUTE) userEntity: UserEntity
    ): UserEntity {

        return userEntity

    }

}