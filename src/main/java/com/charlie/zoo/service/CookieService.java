package com.charlie.zoo.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

public interface CookieService {
    String checkCookie(String id, HttpServletResponse httpServletResponse, Model model);
    String createNewCookieId(HttpServletResponse httpServletResponse, Model model);
}

