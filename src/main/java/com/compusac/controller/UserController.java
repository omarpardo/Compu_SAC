package com.compusac.controller;

import com.compusac.models.entity.Person;
import com.compusac.models.entity.Usuario;
import com.compusac.models.service.*;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UserController {

    private final IUserService userService;

    private final IPersonService personService;

    
    

    public UserController(IUserService userService, IPersonService personService, 
            IProductService productoService) {
        this.userService = userService;
        this.personService = personService;
    } 

    @GetMapping("/registro")
    public String create() {
        return "register";
    }

    @PostMapping("/save")
    public String save(Person person, Usuario user) {
        Long idPersona = personService.guardar(person).getId();
        user.setPerson(idPersona);
        user.setUserName(person.getEmail());
        // user.setUserPass(passEncode.encode(user.getUserPass()));

        if (user.getRol().equals("null")) {
            user.setRol(0);
        }

        userService.guardar(user);

        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        Optional<Usuario> user = userService.findByUserName(usuario.getUserName());
        if (user.isPresent()) {
            session.setAttribute("idusuario", user.get().getId());
            Person p = personService.findById(user.get().getPerson());
            session.setAttribute("userName", p.getName());
            session.setAttribute("lastName", p.getLastName());
            session.setAttribute("email", p.getEmail());
            session.setAttribute("telephone", p.getTelephone());
            if (user.get().getRol() != 0) {
                session.setAttribute("rol", user.get().getRol());
            }

        } else {
            session.removeAttribute("idusuario");
            session.removeAttribute("userName");
        }

        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.removeAttribute("idusuario");
        session.removeAttribute("userName");
        session.removeAttribute("cart_products");
        session.removeAttribute("rol");

        return "login";
    }

}

