package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.utils.EncryptedPasswordUtils;
import com.reeflog.reeflogapi.beans.helpers.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping(value = "/api/addNewMember")
    public Member addNewMember(@RequestBody SignUpForm signUpForm) throws RuntimeException {
        if (!signUpForm.getPassword().equals(signUpForm.getRepassword()))
            throw new RuntimeException(("You must confirm your password"));
        Member member = memberRepository.findByEmail(signUpForm.getEmail());
        Member newMember = new Member();
        if (member != null) {
            throw new RuntimeException("Utilisateur déjà enregistré !");
        } else {
            newMember.setFirstName(signUpForm.getFirstName());
            newMember.setLastName(signUpForm.getLastName());
            newMember.setNickname(signUpForm.getNickName());
            newMember.setEmail(signUpForm.getEmail());
            String encodedPassword = EncryptedPasswordUtils.encryptePassword(signUpForm.getPassword());
            newMember.setPassword(encodedPassword);
            memberRepository.save(newMember);
        }
        return newMember;
    }


    @GetMapping(value = "/api/deleteMember/{id}")
    public Member deleteMember(@RequestHeader("Authorization") String token, @PathVariable int id) {
        Member memberToDelete = memberRepository.findById(id);
        memberRepository.delete(memberToDelete);
        return memberToDelete;
    }


}