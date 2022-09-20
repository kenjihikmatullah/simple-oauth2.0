package com.khash.simpleoauth20.controller.auth;

import com.khash.simpleoauth20.model.google.GoogleUserDetail;
import com.khash.simpleoauth20.service.google.GoogleGetAccessTokenService;
import com.khash.simpleoauth20.service.google.GoogleGetUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class GoogleOAuth2Controller {

    private static final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";

    @Autowired
    Environment env;

    @Autowired
    GoogleGetAccessTokenService getAccessTokenService;

    @Autowired
    GoogleGetUserDetailService getUserDetailService;

    @GetMapping("/login/oauth2/google")
    public RedirectView login(RedirectAttributes attributes) {
        String clientId = env.getProperty("oauth2.asClient.google.clientId");

        attributes.addAttribute("client_id", clientId);
        attributes.addAttribute("response_type", "code");
        attributes.addAttribute("scope", "openid profile");
        attributes.addAttribute("state", "hehehe"); // Need to generate string, only recognized by our server, indicating current context/state
        attributes.addAttribute("redirect_uri", REDIRECT_URI);

        return new RedirectView("https://accounts.google.com/o/oauth2/v2/auth");
    }

    @GetMapping("/login/oauth2/code/google")
    private ModelAndView handleAuthCode(@RequestParam(name = "code") String authCode, @RequestParam String state, ModelMap model) {
        System.out.println("Authorization Code: " + authCode);
        System.out.println("State: " + state);

        String accessToken = getAccessTokenService.execute(authCode);
        GoogleUserDetail userDetailResponse = getUserDetailService.execute(accessToken);

        model.addAttribute("name", userDetailResponse.name);

        return new ModelAndView("home");
    }
}
