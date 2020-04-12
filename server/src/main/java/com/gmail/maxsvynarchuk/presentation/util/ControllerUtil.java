package com.gmail.maxsvynarchuk.presentation.util;

public class ControllerUtil {
    /**
     * Add next page to redirect
     *
     * @param pageToRedirect page to redirect
     */
    public static String redirectTo(String pageToRedirect) {
        return "redirect:" + pageToRedirect;
    }
}
