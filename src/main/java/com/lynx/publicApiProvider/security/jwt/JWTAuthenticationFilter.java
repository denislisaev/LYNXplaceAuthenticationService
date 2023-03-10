package com.lynx.publicApiProvider.security.jwt;

import com.lynx.publicApiProvider.entity.User;
import com.lynx.publicApiProvider.security.SecurityConstants;
import com.lynx.publicApiProvider.service.ConfigUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends OncePerRequestFilter{

    public static final Logger LOG = LoggerFactory.getLogger(JWTProvider.class);

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private ConfigUserDetailsService configUserDetailsService;

    public String getJWTFromRequest(HttpServletRequest request){
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(header) && header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return header.split(" ")[1];
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtProvider.validToken(jwt)){
                Long userId = jwtProvider.getUserIdFromToken(jwt);
                User user = configUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.emptyList()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((javax.servlet.http.HttpServletRequest) request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            LOG.error("Can not set user authentication");
        }

        filterChain.doFilter(request, response);
    }
}
