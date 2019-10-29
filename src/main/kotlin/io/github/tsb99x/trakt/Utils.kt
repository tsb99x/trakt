package io.github.tsb99x.trakt

import java.util.*

fun String.toUUID(): UUID =
    UUID.fromString(this)
