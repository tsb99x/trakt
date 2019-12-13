package io.github.tsb99x.trakt.api.interceptor

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import io.github.tsb99x.trakt.core.REQUEST_ID
import io.github.tsb99x.trakt.core.classLogger
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.MDC
import javax.servlet.http.HttpServletRequest

class RequestInterceptorTest {

    private val interceptor = RequestInterceptor()
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

        interceptor.preHandle(request, mockk(), mockk())

        verify { appender.doAppend(withArg {
            it.level == Level.INFO && it.message == "Serving"
            it.mdcPropertyMap.containsKey(REQUEST_ID)
        }) }

        interceptor.postHandle(request, mockk(), mockk(), null)

        verify { appender.doAppend(withArg {
            it.level == Level.INFO && it.message == "Finished"
            it.mdcPropertyMap.containsKey(REQUEST_ID)
        }) }

        confirmVerified(appender)

        MDC.clear()

    }

}
