package com.lumit.shop.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final AuthorizationChecker authorizationChecker;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestContext) {
        boolean isAuthorized = authorizationChecker.check(requestContext.getRequest(), authentication.get());
        return new AuthorizationDecision(isAuthorized);
    }
}