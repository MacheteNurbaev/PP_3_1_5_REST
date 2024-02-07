package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping(value = "/admin")
    public String showUsersTable(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "usersTable";
    }

    @GetMapping(value = "/admin/goToChangeUser")
    public String goToChangeUser(@RequestParam Long id, Model model) {
        List<User> user = new ArrayList<>();
        user.add(userService.getUser(id));
        model.addAttribute("users", user);
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "changeUser";
    }

    @GetMapping(value = "/admin/goToAddUser")
    public String goToAddUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "addUser";
    }

    @PostMapping("/admin/add")
    public String add(@Valid @ModelAttribute User user, @RequestParam List<Long> roleId, BindingResult bindingResult) {
        User us = user;
        us.setPassword(passwordEncoder.encode(user.getPassword()));
        us.setRoles(roleService.getRolesById(roleId));
        if (bindingResult.hasErrors()) {
            return "addUser";

        }
        userService.addUser(us);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/change")
    public String change(@Valid @ModelAttribute User user, @RequestParam List<Long> roleId, BindingResult bindingResult) {
        User us = user;
        us.setRoles(roleService.getRolesById(roleId));

        if (us.getPassword().equals("")) {
            us.setPassword(userService.getUser(user.getId()).getPassword());
        } else {
            us.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (bindingResult.hasErrors()) {
            return "changeUser";
        }
        userService.changeUser(us);
        return "redirect:/admin";
    }
}
