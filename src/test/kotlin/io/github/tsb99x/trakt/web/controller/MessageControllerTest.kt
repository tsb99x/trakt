package io.github.tsb99x.trakt.web.controller

import io.github.tsb99x.trakt.INTEGRATION
import io.github.tsb99x.trakt.data.MessageDao
import io.github.tsb99x.trakt.truncate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Tag(INTEGRATION)
class MessageControllerTest @Autowired constructor(
    private val jdbcTemplate: JdbcTemplate,
    private val mockMvc: MockMvc,
    private val messageDao: MessageDao
) {

    @BeforeEach
    fun beforeEach() {

        jdbcTemplate.truncate("messages")

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
    fun `expect controller to return index page`() {

        val expected = ClassPathResource("index.html").file.readText()

        val request = get("/")

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().string(expected))

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
    fun `expect controller to add page`() {

        val msg = "my message"
        val request = post("/?msg={msg}", msg)

        mockMvc.perform(request)
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/"))

        val messages = messageDao.selectAllOrderByCreationTimeDesc()
            .filter { it.text == msg }

        assertEquals(1, messages.size)

    }

}
