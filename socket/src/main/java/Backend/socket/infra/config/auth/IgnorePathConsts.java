package Backend.socket.infra.config.auth;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IgnorePathConsts {

    private static final Map<String, Set<HttpMethod>> ignorePathMap=
            Map.of(
                    "/**", Set.of(HttpMethod.GET),
                    "/swagger-ui/**", Set.of(HttpMethod.GET, HttpMethod.OPTIONS)
            );

    public static Boolean isIgnorablePath(String uri, HttpMethod httpMethod){
        if(ignorePathMap.containsKey(uri)){
            Set<HttpMethod> methods = ignorePathMap.get(uri);
            return methods.stream()
                    .anyMatch(method -> method.equals(httpMethod));
        }
        return false;
    }
}
