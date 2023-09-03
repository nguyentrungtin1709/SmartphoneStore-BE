package online.shop.SmartphoneStore.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.service.AccountDetailsService;
import online.shop.SmartphoneStore.service.JsonWebTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final JsonWebTokenService jsonWebTokenService;

    private final AccountDetailsService accountDetailsService;

    @Autowired
    public JsonWebTokenFilter(
            JsonWebTokenService jsonWebTokenService,
            AccountDetailsService accountDetailsService
    ) {
        this.jsonWebTokenService = jsonWebTokenService;
        this.accountDetailsService = accountDetailsService;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getToken(request);
        if (token == null){
            filterChain.doFilter(request, response);
            return;
        }
        Optional<String> email = jsonWebTokenService.extractEmail(token);
        if (email.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null){
            Account account = (Account) accountDetailsService
                    .loadUserByUsername(
                            email.orElseThrow()
                    );
            if (jsonWebTokenService.isValidToken(token, account)){
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                account,
                                null,
                                account.getAuthorities()
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(
            HttpServletRequest request
    ) {
        String authorization = request.getHeader("Authorization");
        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }
        return null;
    }
}
