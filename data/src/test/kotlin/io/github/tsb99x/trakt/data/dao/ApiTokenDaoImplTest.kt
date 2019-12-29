package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.ADMIN_USER
import io.github.tsb99x.trakt.core.INTEGRATION
import io.github.tsb99x.trakt.core.entity.ApiTokenEntity
import io.github.tsb99x.trakt.core.minusMinutes
import io.github.tsb99x.trakt.data.initTestDataConfig
import io.github.tsb99x.trakt.data.truncate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Tag(INTEGRATION)
class ApiTokenDaoImplTest {

    private val config = initTestDataConfig()
    private val apiTokenDao = config.apiTokenDao

    @BeforeEach
    fun beforeEach() {

        config.dataSource.truncate("api_tokens")

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
            .minusMinutes(config.tokenLifetimeInMinutes + 1)
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
