package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.adminUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("dev")
@Tag("integration")
class UserDaoTest @Autowired constructor(
    private val userDao: UserDao
) {

    @Test
    fun `expect find by username to work`() {

        val user = userDao.findUserByUsername("admin")

        assertEquals(adminUser, user)

    }

    @Test
    fun `expect find by username to return null on no match`() {

        val user = userDao.findUserByUsername("non-existing-username")

        assertNull(user)

    }

}
