package io.github.tsb99x.trakt.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
            .antMatchers("/**").hasRole("ADMIN")
            .and()
            .formLogin()

    }

    override fun configure(auth: AuthenticationManagerBuilder) {

        auth.jdbcAuthentication()
            .passwordEncoder(BCryptPasswordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery( // language=SQL
                """
                
                    SELECT name, hash, enabled
                    FROM users
                    WHERE name = ?
                
                """.trimIndent()
            )
            .authoritiesByUsernameQuery( // language=SQL
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
