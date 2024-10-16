package com.backendcats.util.security;

import com.backendcats.model.User;
import com.backendcats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException{


        try {
            if(request.getServletPath().matches("/user/login")) {
                chain.doFilter(request, response);
            }
            else {
                final String authorizationHeader = request.getHeader("Authorization");

                if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Authorization header is missing or invalid");
                    return;
                }

                String jwt = authorizationHeader.substring(7);

                String document = jwtUtil.GetUserFromToken(jwt);

                if (document != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = userService.FindUserById(document).orElse(null);
                    if (user != null) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                user.getDocument(), user.getPassword(), new ArrayList<>());
                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                }

                chain.doFilter(request, response);
            }

        }
        catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        }

    }
}
