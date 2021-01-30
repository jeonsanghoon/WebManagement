package com.mrc.framework.config;

import org.springframework.util.AntPathMatcher;

import java.util.Map;

/**
 * Created by mrc0700@gmail.com on 2020-12-08
 * Discription :
 */
public class CaseInsensitivePathMatcher extends AntPathMatcher {
    @Override
    protected boolean doMatch(String pattern, String path, boolean fullMatch, Map<String, String> uriTemplateVariables) {
        return super.doMatch(pattern.toLowerCase(), path.toLowerCase(), fullMatch, uriTemplateVariables);
    }
}
