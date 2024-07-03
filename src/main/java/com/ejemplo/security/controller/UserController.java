package com.ejemplo.security.controller;

import com.ejemplo.security.model.OurUser;
import com.ejemplo.security.repository.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    private OurUserRepository ourUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String goHome() {
        return "index";
    }

    @GetMapping("/home")
    public String dashboard() {
        return "home";
    }


    @PostMapping("/user/save")
    public ResponseEntity<Object> guardar(@RequestBody OurUser ourUser){
        ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
        OurUser result = ourUserRepository.save(ourUser);
        if (result.getId() >0){
            return ResponseEntity.ok("Usuario Guardado");
        }
        return ResponseEntity.status(404).body("Error usuario no guardado" );

    }

    /*@GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUser(){
        return ResponseEntity.ok(ourUserRepository.findAll());
    }*/

    @GetMapping({"/usuario"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAllUser(Model model){
        model.addAttribute("usuarios", ourUserRepository.findAll());
        return "usuario";
}

        @GetMapping("/user/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getMyDetails() {
        return ResponseEntity.ok(ourUserRepository.findByEmail(getLoggedInUserDetails().getUsername()));
    }

    public UserDetails getLoggedInUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

}
