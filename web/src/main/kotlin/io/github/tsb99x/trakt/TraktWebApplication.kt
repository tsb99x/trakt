package io.github.tsb99x.trakt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TraktWebApplication

fun main(
    args: Array<String>
) {

    runApplication<TraktWebApplication>(*args)

}
