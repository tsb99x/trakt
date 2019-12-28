package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.config.DataConfig
import javax.sql.DataSource

fun initTestDataConfig() =
    DataConfig(
        datasourceUrl = "jdbc:postgresql://localhost:49432/trakt",
        datasourceUsername = "trakt",
        datasourcePassword = "trakt",
        tokenLifetimeInMinutes = 60L
    )

fun DataSource.truncate(tableName: String) =
    this.executeUpdate("TRUNCATE TABLE $tableName")
