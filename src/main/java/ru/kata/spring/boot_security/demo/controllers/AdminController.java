package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
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
    public String showUsersTable(Model model, Principal principal) {
        model.addAttribute("us", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());

        return "usersTable";
    }

    @PostMapping("/admin/add")
    @ResponseBody
    public void add(@RequestBody User user, BindingResult bindingResult) {
        User us = user;
        us.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(us);
    }

    @DeleteMapping("/admin/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/admin/edit")
    @ResponseBody
    public void edit(@Valid @RequestBody User user, BindingResult bindingResult) {
        User us = user;

        if (us.getPassword().equals("")) {
            us.setPassword(userService.getUser(user.getId()).getPassword());
        } else {
            us.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userService.changeUser(us);
    }

    @GetMapping("/admin/getUser/{userId}")
    @ResponseBody
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/admin/getRole/{roleId}")
    @ResponseBody
    public List<Role> getRoleById(@PathVariable Long roleId) {
        return roleService.getRolesById(roleId);
    }

}
