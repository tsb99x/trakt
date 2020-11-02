package io.github.tsb99x.trakt.data.config

import com.zaxxer.hikari.HikariDataSource
import io.github.tsb99x.trakt.core.getConfigVariable
import io.github.tsb99x.trakt.data.dao.*
import org.flywaydb.core.Flyway
import javax.sql.DataSource

const val defaultTokenLifetimeInMinutes = (7 /* days */ * 24 /* hours */ * 60 /* minutes */).toString()

fun initDataConfig() =
    DataConfig(
        getConfigVariable("datasource.url"),
        getConfigVariable("datasource.username"),
        getConfigVariable("datasource.password"),
        getConfigVariable("token.lifetime.in.minutes", defaultTokenLifetimeInMinutes).toLong()
    )

class DataConfig(
    val datasourceUrl: String,
    val datasourceUsername: String,
    val datasourcePassword: String,
    val tokenLifetimeInMinutes: Long
) {

    private val dataSource: DataSource =
        HikariDataSource().apply {

            jdbcUrl = datasourceUrl
            username = datasourceUsername
            password = datasourcePassword

            Flyway.configure()
                .dataSource(this)
                .load()
                .migrate()

        }

    val apiTokenDao: ApiTokenDao =
        ApiTokenDao(dataSource, tokenLifetimeInMinutes)

    val userDao: UserDao =
        UserDao(dataSource)

}
