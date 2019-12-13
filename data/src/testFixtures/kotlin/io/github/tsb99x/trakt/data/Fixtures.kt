package io.github.tsb99x.trakt.data

import javax.sql.DataSource

fun DataSource.truncate(tableName: String) =
    this.executeUpdate("TRUNCATE TABLE $tableName")
