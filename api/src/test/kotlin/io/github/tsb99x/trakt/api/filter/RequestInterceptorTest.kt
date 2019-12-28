package io.github.tsb99x.trakt.api.filter

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import io.github.tsb99x.trakt.api.FINISHED
import io.github.tsb99x.trakt.api.SERVING
import io.github.tsb99x.trakt.core.REQUEST_ID
import io.github.tsb99x.trakt.core.classLogger
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.MDC
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest

class RequestInterceptorTest {

    private val interceptor = RequestFilter()
    private val appender: Appender<ILoggingEvent> = mockk {
        every { doAppend(any()) } just runs
    }

    @BeforeEach
    fun beforeEach() {

        val logger = interceptor.classLogger() as Logger
        logger.addAppender(appender)

    }

    @Test
    fun `expect interceptor to work`() {

        val request: HttpServletRequest = mockk {
            every { setAttribute(any(), any()) } just runs
        }

        val chain: FilterChain = mockk {
            every { doFilter(any(), any()) } just runs
        }

        interceptor.doFilter(request, mockk(), chain)

        verify {
            appender.doAppend(withArg {
                it.level == Level.INFO && it.message == SERVING
                it.mdcPropertyMap.containsKey(REQUEST_ID)
            })
        }

        verify {
            appender.doAppend(withArg {
                it.level == Level.INFO && it.message == FINISHED
                it.mdcPropertyMap.containsKey(REQUEST_ID)
            })
        }

        confirmVerified(appender)

        MDC.clear()

    }

}
