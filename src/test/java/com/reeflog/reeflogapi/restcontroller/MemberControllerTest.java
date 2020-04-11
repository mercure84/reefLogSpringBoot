package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.helpers.SignUpForm;
import com.reeflog.reeflogapi.exceptions.MemberException;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import com.reeflog.reeflogapi.utils.BeanValidator;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MemberControllerTest {

    @InjectMocks
    MemberController memberController;

    @Mock
    MemberRepository memberRepository;

    @Mock
    BeanValidator beanValidator;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    // test validant qu'un membre ne peut pas s'enregistrer avec une adresse déjà présente en base
    @Test(expected = MemberException.class)
    public void addNewMemberTestEmailAlreadyExists() throws Exception {
        SignUpForm newMember = new SignUpForm();
        newMember.setUserName("testeurFou222");
        newMember.setPassword("test123");
        newMember.setRepassword("test123");
        newMember.setEmail("test@test.test");
            when(!beanValidator.isSignupFormValide(newMember)).thenReturn(true);
            when(memberRepository.findByEmail(newMember.getEmail())).thenReturn(new Member());
            memberController.addNewMember(newMember);
    }

    //idem pour le username
    @Test(expected = MemberException.class)
    public void addNewMemberTestUsernameAlreadyExists() throws Exception{
        SignUpForm newMember = new SignUpForm();
        newMember.setUserName("testeurFou222");
        newMember.setPassword("test123");
        newMember.setRepassword("test123");
        newMember.setEmail("test@test.test");
        when(!beanValidator.isSignupFormValide(newMember)).thenReturn(true);
        when(memberRepository.findByUserName(newMember.getUserName())).thenReturn(new Member());
        memberController.addNewMember(newMember);
        }


        //test de l'update sur un Member au statut "bloqué"
    @Test (expected = MemberException.class)
    public void updateMemberWhoIsBlocked() throws Exception {
        Member member = new Member();
        member.setUserName("testeurFou222");
        member.setPassword("test123");
        member.setEmail("test@test.test");
        member.setMemberStatus(Member.MemberStatus.BLOCKED);
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setEmail("testeurFoy@gmail.fr");
        signUpForm.setUserName("nouveauUsername");
        signUpForm.setPassword("password1");
        signUpForm.setRepassword("password1");
        String token = "fakeBearerToken";
        when(memberRepository.findById(signUpForm.getIdToUpdate())).thenReturn(member);
        when(jwtTokenUtil.validateCustomTokenForMember(token, member)).thenReturn(true);
        memberController.updateMember(token,signUpForm);


    }


}