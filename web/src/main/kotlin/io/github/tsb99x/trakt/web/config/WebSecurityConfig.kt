package io.github.tsb99x.trakt.web.config

import io.github.tsb99x.trakt.core.config.ConfigProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val dataSource: DataSource,
    private val configProperties: ConfigProperties
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        if (!configProperties.csrfEnabled) {
            http.csrf()
                .disable()

            http.headers()
                .frameOptions()
                .disable()
        }

        http.authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/**").hasRole("ADMIN")
            .and().formLogin()

    }

    override fun configure(auth: AuthenticationManagerBuilder) {

        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(
                """
                
                    SELECT name, hash, enabled
                    FROM users
                    WHERE name = ?
                
                """.trimIndent()
            )
            .authoritiesByUsernameQuery(
                """
                
                    SELECT u.name, r.name
                    FROM users u
                    JOIN user_roles ur ON u.id = ur.user_id
                    JOIN roles r ON r.id = ur.role_id
                    WHERE u.name = ?
                 
                """.trimIndent()
            )

    }

}
