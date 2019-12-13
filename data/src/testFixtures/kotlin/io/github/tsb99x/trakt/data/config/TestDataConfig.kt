package io.github.tsb99x.trakt.data.config

fun initTestDataConfig() =
    DataConfig(
        datasourceUrl = "jdbc:postgresql://localhost:49432/trakt",
        datasourceUsername = "trakt",
        datasourcePassword = "trakt",
        tokenLifetimeInMinutes = 60L
    )
