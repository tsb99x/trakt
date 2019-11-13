package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.INTEGRATION
import io.github.tsb99x.trakt.adminUser
import io.github.tsb99x.trakt.toUTC
import io.github.tsb99x.trakt.truncate
import org.junit.jupiter.api.Assertions.assertEquals
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
    private val jdbcTemplate: JdbcTemplate
) {

    @BeforeEach
    fun beforeEach() {

        jdbcTemplate.truncate("api_tokens")

    }

    @Test
    fun `expect insert to work`() {

        val apiToken = ApiTokenEntity(UUID.randomUUID(), adminUser.id, Instant.now())

        apiTokenDao.insert(apiToken)

        assertEquals(1, countOf(apiToken))

    }

    @Test
    fun `expect select one by id to work`() {

        val apiToken = ApiTokenEntity(UUID.randomUUID(), adminUser.id, Instant.now().truncatedTo(ChronoUnit.SECONDS))
        apiTokenDao.insert(apiToken)

        val res = apiTokenDao.selectOneById(apiToken.id)

        assertEquals(apiToken, res)

    }

    private fun countOf(
        entity: ApiTokenEntity
    ): Int {

        return jdbcTemplate.queryForObject( // language=SQL
            """
                
                SELECT COUNT(*)
                FROM api_tokens
                WHERE id = ?
                AND user_id = ?
                AND created_at = ?
                
            """.trimIndent(),
            arrayOf(entity.id, entity.userId, entity.createdAt.toUTC()),
            Int::class.java
        )

    }

}
