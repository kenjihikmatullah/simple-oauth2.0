package com.khash.simpleoauth20.controller.auth;

import com.khash.simpleoauth20.model.github.UserDetail;
import com.khash.simpleoauth20.service.github.GetAccessTokenService;
import com.khash.simpleoauth20.service.github.GetUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class GitHubOAuth2Controller {

    private static final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/github";

    @Autowired
    Environment env;

    @Autowired
    GetAccessTokenService getAccessTokenService;

    @Autowired
    GetUserDetailService getUserDetailService;

    @GetMapping("/login/oauth2/{server}")
    public RedirectView login(@PathVariable String server, RedirectAttributes attributes) {

        if (server.equalsIgnoreCase("github")) {
            String clientId = env.getProperty("oauth2.asClient.github.clientId");

            attributes.addAttribute("client_id", clientId);
            attributes.addAttribute("redirect_uri", REDIRECT_URI);

            return new RedirectView("https://github.com/login/oauth/authorize");

        } else {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/login/oauth2/code/github")
    private ModelAndView handleAuthCode(@RequestParam(name = "code") String authCode, ModelMap model) {
        System.out.println("Authorization Code: " + authCode);

        String accessToken = getAccessTokenService.execute(authCode);
        UserDetail userDetailResponse = getUserDetailService.execute(accessToken);

        model.addAttribute("name", userDetailResponse.name);
        model.addAttribute("email", userDetailResponse.email);

        return new ModelAndView("home");
    }
}
