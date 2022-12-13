package com.springSecurity.jwt.config;

import com.springSecurity.jwt.domain.SecurityUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
    public final UserDetailsService userDetailsService;
    @Qualifier("userService")
    private final  JwtUtils jwtUtils;

    public JwtAuthFilter( UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader=request.getHeader(AUTHORIZATION);
        String username;
        String jwtToken;
        if (authHeader==null || !authHeader.startsWith("Bearer")){

            filterChain.doFilter(request,response);
        }
        assert authHeader != null;
        jwtToken=authHeader.substring(7);
        username=jwtUtils.extractUsername(jwtToken);
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            SecurityUser userDetails= (SecurityUser) userDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
