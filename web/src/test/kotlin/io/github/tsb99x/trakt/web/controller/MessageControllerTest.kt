package io.github.tsb99x.trakt.web.controller

import io.github.tsb99x.trakt.core.INTEGRATION
import io.github.tsb99x.trakt.data.config.DataConfig
import io.github.tsb99x.trakt.data.config.SpringTestDataConfig
import io.github.tsb99x.trakt.data.truncate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@Import(SpringTestDataConfig::class)
@ActiveProfiles("dev")
@Tag(INTEGRATION)
class MessageControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val dataConfig: DataConfig
) {

    val messageDao = dataConfig.messageDao

    @BeforeEach
    fun beforeEach() {

        dataConfig.dataSource.truncate("messages")

    }

    @Test
    fun `expect controller to return index page`() {

        val expected = ClassPathResource("index.html").file.readText()

        val request = get("/")

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().string(expected))

    }

    @Test
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
