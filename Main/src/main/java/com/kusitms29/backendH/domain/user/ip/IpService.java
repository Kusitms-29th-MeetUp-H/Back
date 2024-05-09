package com.kusitms29.backendH.domain.user.ip;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

        // IPv6 주소를 IPv4 주소로 변환
        if (ip != null && ip.indexOf(':') != -1) {
            try {
                InetAddress inetAddress = InetAddress.getByName(ip);
                if (inetAddress instanceof Inet4Address) {
                    ip = inetAddress.getHostAddress();
                }
            } catch (UnknownHostException e) {
                // 변환 실패 시 원래의 IP 주소 반환
            }
        }

        return ip;
    }
}