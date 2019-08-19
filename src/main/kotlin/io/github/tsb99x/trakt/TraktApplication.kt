package io.github.tsb99x.trakt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TraktApplication

fun main(args: Array<String>) {

    runApplication<TraktApplication>(*args)

}
