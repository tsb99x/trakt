package io.github.tsb99x.trakt.config

import com.zaxxer.hikari.HikariDataSource
import io.github.tsb99x.trakt.core.getConfigVariable
import io.github.tsb99x.trakt.data.dao.*
import org.flywaydb.core.Flyway
import javax.sql.DataSource

const val DEFAULT_TOKEN_LIFETIME_IN_MINUTES = (7 /* days */ * 24 /* hours */ * 60 /* minutes */).toString()

fun initDataConfig() =
    DataConfig(
        getConfigVariable("datasource.url"),
        getConfigVariable("datasource.username"),
        getConfigVariable("datasource.password"),
        getConfigVariable("token.lifetime.in.minutes", DEFAULT_TOKEN_LIFETIME_IN_MINUTES).toLong()
    )

fun initCoreConfig(
    dataConfig: DataConfig
) =
    CoreConfig(
        dataConfig.apiTokenDao,
        dataConfig.userDao,
        dataConfig.messageDao
    )

class DataConfig(
    val datasourceUrl: String,
    val datasourceUsername: String,
    val datasourcePassword: String,
    val tokenLifetimeInMinutes: Long
) {

    val dataSource: DataSource =
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
        ApiTokenDaoImpl(dataSource, tokenLifetimeInMinutes)

    val userDao: UserDao =
        UserDaoImpl(dataSource)

    val messageDao: MessageDao =
        MessageDaoImpl(dataSource)

}
