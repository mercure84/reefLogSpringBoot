package com.reeflog.reeflogapi.webcontroller;


import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.helpers.PasswordRecover;
import com.reeflog.reeflogapi.beans.helpers.ResetPasswordForm;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.repository.PasswordRecoverRepository;
import com.reeflog.reeflogapi.utils.EncryptedPasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PasswordRecoverController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));

    @Autowired
    PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    MemberRepository memberRepository;


    @GetMapping(value = "/web/recoverPasswordMail")
    public String passwordRecoverForm(String token, String error, Model model) {

        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setToken(token);
        String message = "";
        if (error != null && error.equals("true")) {
            message = "Impossible de mettre à jour votre mot de passe, veuillez recommencer";
        }

        model.addAttribute("message", message);
        model.addAttribute("resetPasswordForm", resetPasswordForm);
        logger.info("[WEB] Page reset password envoyée");
        return "passwordRecoverForm";
    }

    @PostMapping(value = "/web/recoverPasswordMail")
    public String registerPasswordRecoverForm(@ModelAttribute ResetPasswordForm resetPasswordForm, Model model) {

        try {
            boolean isPasswordValid = resetPasswordForm.getPassword().equals(resetPasswordForm.getRepassword()) && resetPasswordForm.getPassword().length() > 5;
            //FIXME : mettre aussi un check sur la validité du lien de réinitialisation du password concernant la date de création
            if (isPasswordValid) {
                PasswordRecover passwordRecover = passwordRecoverRepository.findByUrlToken(resetPasswordForm.getToken());
                Member member = passwordRecover.getMember();
                String encodedPassword = EncryptedPasswordUtils.encryptePassword(resetPasswordForm.getPassword());
                member.setPassword(encodedPassword);
                memberRepository.save(member);
                passwordRecoverRepository.delete(passwordRecover);
                logger.info("le mot de passe du membre " + member.getEmail() + " a été mis à jour suite à sa réinitialisation");
                return "redirect:/web/changedPassword";
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }

        return ("redirect:/web/recoverPasswordMail?token=" + resetPasswordForm.getToken()) + "&error=true";

    }

    @GetMapping(value = "/web/changedPassword")
    public String changedPassword() {
        return "changedPassword";
    }


}
