package com.reeflog.reeflogapi.webcontroller;


import com.reeflog.reeflogapi.beans.helpers.ResetPasswordForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PasswordRecoverController {



    @GetMapping(value="/web/recoverPasswordMail/{token}")
    public String passwordRecoverForm(@PathVariable String token, Model model) {

        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setToken(token);
        model.addAttribute("resetPasswordForm", resetPasswordForm);
        return "passwordRecoverForm";
    }

}
