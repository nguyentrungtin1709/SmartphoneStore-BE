package online.shop.SmartphoneStore.security;

import online.shop.SmartphoneStore.entity.Enum.Role;
import online.shop.SmartphoneStore.security.filter.JsonWebTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final AuthenticationProvider authenticationProvider;

    private final JsonWebTokenFilter jsonWebTokenFilter;

    @Autowired
    public WebSecurity(
            AuthenticationProvider authenticationProvider,
            JsonWebTokenFilter jsonWebTokenFilter)
    {
        this.authenticationProvider = authenticationProvider;
        this.jsonWebTokenFilter = jsonWebTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize ->
                    authorize
                            .requestMatchers(
                                    "/api/v1/auth/**",
                                    "/api/v1/resources/**",
                                    "/api/v1/brands/**",
                                    "/api/v1/smartphones/**"
                            )
                            .permitAll()
                            .requestMatchers(
                                    "/api/v1/account/**"
                            )
                            .hasAnyAuthority(Role.CUSTOMER.name(), Role.ADMIN.name())
                            .requestMatchers(
                                "/api/v1/admin/**"
                            )
                            .hasAuthority(Role.ADMIN.name())
                            .anyRequest()
                            .authenticated()
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
            );
        return http.build();
    }
}
