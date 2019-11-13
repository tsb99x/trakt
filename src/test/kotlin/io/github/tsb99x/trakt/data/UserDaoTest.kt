package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.INTEGRATION
import io.github.tsb99x.trakt.adminUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("dev")
@Tag(INTEGRATION)
class UserDaoTest @Autowired constructor(
    private val userDao: UserDao
) {

    @Test
    fun `expect find by username to work`() {

        val user = userDao.selectOneByUsername(adminUser.name)

        assertEquals(adminUser, user)

    }

    @Test
    fun `expect find by id to work`() {

        val user = userDao.selectOneById(adminUser.id)

        assertEquals(adminUser, user)

    }

}
