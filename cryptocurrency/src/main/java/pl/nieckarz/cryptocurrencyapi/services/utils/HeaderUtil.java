package pl.nieckarz.cryptocurrencyapi.services.utils;


import jakarta.servlet.http.HttpServletRequest;

public class HeaderUtil {

    private static final String SIGNING_KEY = "qwertyuiopasdfghjklzxcvbnm123456";

    public static String getHeaderValue(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }
}
