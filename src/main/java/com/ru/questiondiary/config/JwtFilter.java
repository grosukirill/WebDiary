package com.ru.questiondiary.config;

import com.google.gson.Gson;
import com.ru.questiondiary.service.TokenService;
import com.ru.questiondiary.service.UserService;
import com.ru.questiondiary.web.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;
    private final Gson gson;

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        PrintWriter servletWriter = response.getWriter();

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            ErrorResponse errorResponse = tokenService.createTokenException();
            String errorResponseString = gson.toJson(errorResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            servletWriter.print(errorResponseString);
            servletWriter.flush();
            return;
        }

        String userEmail = userData.get("email");
        String userId = userData.get("id");

        if (userEmail == null || userId == null) {
            ErrorResponse errorResponse = tokenService.createTokenException();
            String errorResponseString = gson.toJson(errorResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            servletWriter.print(errorResponseString);
            servletWriter.flush();
        } else {
            request.setAttribute("userId", userId);
            request.setAttribute("username", userEmail);
            UserDetails userDetails = userService.loadUserByUsername(userData.get("email"));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    "", userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken;
        }
        return null;
    }
}
