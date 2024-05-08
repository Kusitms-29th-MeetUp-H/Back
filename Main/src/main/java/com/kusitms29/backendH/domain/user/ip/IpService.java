package com.kusitms29.backendH.domain.user.ip;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class IpService {
    public String getClientIpAddress(HttpServletRequest request) {
        String[] headerTypes = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip = null;

        for (String headerType : headerTypes) {
            ip = request.getHeader(headerType);
            if (ip != null) {
                break;
            }
        }

        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
