package com.example.aop.springaop.filter;

import java.util.UUID;

/*for get different uuid log

import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.aop.springaop.config.UniqueIdFilterConfiguration;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class UniqueIdFilter extends OncePerRequestFilter {

private final String responseHeader;
private final String mdcKey;
private final String requestHeader;

public UniqueIdFilter() {
responseHeader = UniqueIdFilterConfiguration.DEFAULT_HEADER_TOKEN;
mdcKey = UniqueIdFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY;
requestHeader = UniqueIdFilterConfiguration.DEFAULT_HEADER_TOKEN;
}

public UniqueIdFilter(final String responseHeader, final String mdcTokenKey, final String requestHeader) {
this.responseHeader = responseHeader;
this.mdcKey = mdcTokenKey;
this.requestHeader = requestHeader;
}

@Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {
        try {
            final String token = extractToken(request);
            MDC.put(mdcKey, token);
            if (StringUtils.hasText(responseHeader)) {
                response.addHeader(responseHeader, token);
            }
            chain.doFilter(request, response);
        } finally {
            MDC.remove(mdcKey);
        }
    }

private String extractToken(final HttpServletRequest request) {
final String token;
if (StringUtils.hasText(requestHeader) && StringUtils.hasText(request.getHeader(requestHeader))) {
token = request.getHeader(requestHeader);
} else {
token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
}
return token;
}

@Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

}

//--------------------------------------------------------------------------------------------

*/
// For consistent Ip Addresss in a log

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.aop.springaop.config.UniqueIdFilterConfiguration;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class UniqueIdFilter extends OncePerRequestFilter {

	private final String mdcKey;
	private final String mdcKeyIpAdd;

	public UniqueIdFilter() {
		mdcKey = UniqueIdFilterConfiguration.MDC_KEY_ID;
		this.mdcKeyIpAdd = UniqueIdFilterConfiguration.MDC_KEY_IP_ADDRESS;
	}

	public UniqueIdFilter(final String mdcKey, final String mdcKeyIpAdd) {
		this.mdcKey = mdcKey;
		this.mdcKeyIpAdd = mdcKeyIpAdd;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain) throws java.io.IOException, ServletException {
		try {
			MDC.put(mdcKey, UUID.randomUUID().toString());
			MDC.put(mdcKeyIpAdd, request.getRemoteAddr());
			chain.doFilter(request, response);
		} finally {
			MDC.remove(mdcKey);
			MDC.remove(mdcKeyIpAdd);
		}
	}

	@Override
	protected boolean isAsyncDispatch(final HttpServletRequest request) {
		return false;
	}

	@Override
	protected boolean shouldNotFilterErrorDispatch() {
		return false;
	}
}