package io.github.tsb99x.trakt.exception

import java.util.*

class GenericException(

    message: String

) : RuntimeException(message) {

    val uid: UUID = UUID.randomUUID()

}
