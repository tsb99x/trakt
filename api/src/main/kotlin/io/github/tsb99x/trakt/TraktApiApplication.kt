package io.github.tsb99x.trakt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TraktApiApplication

fun main(
    args: Array<String>
) {

    runApplication<TraktApiApplication>(*args)

}
