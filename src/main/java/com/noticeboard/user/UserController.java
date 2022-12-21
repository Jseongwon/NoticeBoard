package com.noticeboard.user;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.noticeboard.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup_form";
        }

        try {
            // TODO: 사용자를 만든다. CSR에서 pbkdf2 사용하기
            this.userService.create(userCreateForm.getName(),
                userCreateForm.getEmailAddress(),
                userCreateForm.getPassword(),
                userCreateForm.getSalt());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    String login(ApplicationUser applicationUser) {
        return "login_form";
    }

    @PostMapping("/login")
    String login(@Valid ApplicationUser applicationUser, BindingResult bindingResult) {
        System.out.println("Login");
        if(bindingResult.hasErrors()) {
            return "login_form";
        }

        try {
            // 1. 사용자 ID로 조회한다.
            User user = this.userService.findByName(applicationUser.getUsername());

            // 2. 사용자 정보와 앱 사용자의 비밀번호가 일치하는지 확인한다.
            if(user.getPassword().equals(applicationUser.getPassword())) {
                System.out.println("Main");
                return "redirect:/";
            }
            System.out.println("Login");
            return "login_form";
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("loginFailed", "사용자ID 또는 비밀번호를 확인해 주세요.");
            return "login_form";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("loginFailed", e.getMessage());
            return "login_form";
        }
    }

    @ResponseBody
    @PostMapping("/login/salt")
    String salt(@RequestBody String data) {
        System.out.println("Login/Salt");
        JsonElement element = new Gson().fromJson(data, JsonElement.class);
        String name = element.getAsJsonObject().get("name").getAsString();

        User user = this.userService.findByName(name);
        System.out.println(String.format("{ \"salt\": \"%s\"}", user.getSalt()));

        return String.format("{ \"salt\": \"%s\"}", user.getSalt());
    }
}
