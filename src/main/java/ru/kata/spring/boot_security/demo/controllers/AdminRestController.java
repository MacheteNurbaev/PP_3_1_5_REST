package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping(value = "/admin/user")
    public User getUserByEmailForAdmin(Principal principal) {
        return userService.findByEmail(principal.getName());
    }

    @GetMapping(value = "/admin/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/admin/allRoles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }


    @PostMapping("/admin/add")
    public void add(@RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/admin/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/admin/edit")
    public void edit(@RequestBody User user) {
        userService.changeUser(user);
    }

    @GetMapping("/admin/getUser/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/admin/getRole/{roleId}")
    public List<Role> getRoleById(@PathVariable Long roleId) {
        return roleService.getRolesById(roleId);
    }

}
