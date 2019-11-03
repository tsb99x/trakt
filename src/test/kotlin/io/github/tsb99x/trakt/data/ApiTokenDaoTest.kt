package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.adminUser
import io.github.tsb99x.trakt.truncate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@ActiveProfiles("dev")
@Tag("integration")
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

        val apiToken = ApiTokenEntity(UUID.randomUUID(), adminUser.id, LocalDateTime.now())
        apiTokenDao.insert(apiToken)

        val count = countOf(apiToken)
        assertEquals(1, count)

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
            arrayOf(entity.id, entity.userId, entity.createdAt),
            Int::class.java
        )

    }

}
