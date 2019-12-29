package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.ADMIN_USER
import io.github.tsb99x.trakt.core.INTEGRATION
import io.github.tsb99x.trakt.data.initTestDataConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag(INTEGRATION)
class UserDaoImplTest {
    
    private val config = initTestDataConfig()
    private val userDao = config.userDao

    @Test
    fun `expect find by username to work`() {

        val user = userDao.selectOneByUsername(ADMIN_USER.name)

        assertEquals(ADMIN_USER, user)

    }

    @Test
    fun `expect find by id to work`() {

        val user = userDao.selectOneById(ADMIN_USER.id)

        assertEquals(ADMIN_USER, user)

    }

}
