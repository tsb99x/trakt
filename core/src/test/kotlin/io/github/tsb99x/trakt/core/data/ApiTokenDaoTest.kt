package io.github.tsb99x.trakt.core.data

import io.github.tsb99x.trakt.core.ADMIN_USER
import io.github.tsb99x.trakt.core.INTEGRATION
import io.github.tsb99x.trakt.core.config.ConfigProperties
import io.github.tsb99x.trakt.core.minusMinutes
import io.github.tsb99x.trakt.core.truncate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@SpringBootTest
@ActiveProfiles("dev")
@Tag(INTEGRATION)
class ApiTokenDaoTest @Autowired constructor(
    private val apiTokenDao: ApiTokenDao,
    private val configProperties: ConfigProperties,
    private val jdbcTemplate: JdbcTemplate
) {

    @BeforeEach
    fun beforeEach() {

        jdbcTemplate.truncate("api_tokens")

    }

    @Test
    fun `expect insert and select one by id to work`() {

        val now = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val apiToken = ApiTokenEntity(UUID.randomUUID(), ADMIN_USER.id, now, now)

        val firstRes = apiTokenDao.selectOneById(apiToken.id)
        assertNull(firstRes)

        apiTokenDao.insert(apiToken)

        val secondRes = apiTokenDao.selectOneById(apiToken.id)
        assertEquals(apiToken, secondRes)

    }

    @Test
    fun `expect select one by id to return null on expired token`() {

        val expiredTime = Instant.now()
            .minusMinutes(configProperties.tokenLifetimeInMinutes + 1)
            .truncatedTo(ChronoUnit.SECONDS)

        val apiToken =
            ApiTokenEntity(UUID.randomUUID(), ADMIN_USER.id, expiredTime, expiredTime)
        apiTokenDao.insert(apiToken)

        val res = apiTokenDao.selectOneById(apiToken.id)
        assertNull(res)

    }

    @Test
    fun `expect delete one by id to work`() {

        val now = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val apiToken = ApiTokenEntity(UUID.randomUUID(), ADMIN_USER.id, now, now)
        apiTokenDao.insert(apiToken)

        apiTokenDao.deleteById(apiToken.id)

        val res = apiTokenDao.selectOneById(apiToken.id)
        assertNull(res)

    }

    @Test
    fun `expect update last used at by id to work`() {

        val now = Instant.now().minusSeconds(60).truncatedTo(ChronoUnit.SECONDS)
        val apiToken = ApiTokenEntity(UUID.randomUUID(), ADMIN_USER.id, now, now)
        apiTokenDao.insert(apiToken)

        apiTokenDao.updateLastUsedAtById(apiToken.id)

        val res = apiTokenDao.selectOneById(apiToken.id)!!
        assertTrue(res.lastUsedAt.isAfter(now))

    }

}
