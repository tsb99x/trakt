package io.github.tsb99x.trakt.rest.interceptor

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.github.tsb99x.trakt.REQUEST_ID
import io.github.tsb99x.trakt.classLogger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.MDC

class RequestInterceptorTest {

    private val interceptor = RequestInterceptor()
    private val appender: Appender<ILoggingEvent> = mock()

    @BeforeEach
    fun beforeEach() {

        val logger = interceptor.classLogger() as Logger
        logger.addAppender(appender)

    }

    @Test
    fun `expect interceptor to work`() {

        interceptor.preHandle(mock(), mock(), mock())

        verify(appender).doAppend(argThat {
            level == Level.INFO && message =="Serving"
            mdcPropertyMap.containsKey(REQUEST_ID)
        })

        interceptor.postHandle(mock(), mock(), mock(), null)

        verify(appender).doAppend(argThat {
            level == Level.INFO && message == "Finished"
            mdcPropertyMap.containsKey(REQUEST_ID)
        })

        verifyNoMoreInteractions(appender)

        MDC.clear()

    }

}
