package Backend.socket.infra.config.auth;

import Backend.socket.global.common.JwtProvider;
import Backend.socket.global.error.socketException.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static Backend.socket.global.error.ErrorCode.INVALID_ACCESS_TOKEN;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //url이 통과해도되는지 체크 (http 메서드와 함께 자료구조에 저장하여 검증)
        String url = request.getRequestURI();
        String method = request.getMethod();

        if(!IgnorePathConsts.isIgnorablePath(url, HttpMethod.valueOf(method))){
//            //위가 통과되면 토큰을 검출
//            String authorization = request.getHeader("Authorization");
//
//            /*//Authorization 헤더 검증
//            if (authorization == null || !authorization.startsWith("Bearer ")) {
//
//                System.out.println("token null");
//                filterChain.doFilter(request, response); //doFilter를 통해 request와 response를
//
//            }*/
//
//            System.out.println("authorization now");
//            //Bearer 부분 제거 후 순수 토큰만 획득
//            String token = authorization.split(" ")[1];
//
//            //토큰이 유효한지 검증, 유효성 검증은 extract 메서드에서 처리
//            Long userId = jwtUtil.extractUserClaim(token).getUserId();
//
//            Authentication authToken = new JwtAuthentication(userId);
//
//            //세션에 사용자 등록
//            SecurityContextHolder.getContext().setAuthentication(authToken);
        }



        filterChain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String accessToken = getAccessTokenFromHttpServletRequest(request);
//        jwtProvider.validateAccessToken(accessToken);
//        final Long userId = jwtProvider.getSubject(accessToken);
//        setAuthentication(request, userId);
//        filterChain.doFilter(request, response);
//    }


    private String getAccessTokenFromHttpServletRequest(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER)) {
            return accessToken.substring(BEARER.length());
        }
        throw new UnauthorizedException(INVALID_ACCESS_TOKEN);
    }

    private void setAuthentication(HttpServletRequest request, Long userId) {
        UsernamePasswordAuthenticationToken authentication = new UserAuthentication(userId, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}