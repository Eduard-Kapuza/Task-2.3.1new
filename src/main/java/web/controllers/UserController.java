package web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Get user or users
    @GetMapping("/")
    public String listUsersByLimit(Model model) {
        List<User> listUser = userService.getListUser();
        model.addAttribute("listUsers", listUser);
        return "users";
    }

    @GetMapping("/{id}")
    public String userById(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        System.out.println(user);
        model.addAttribute("getUserById", user);
        return "userById";
    }

    @GetMapping("/getAmountUsers")
    public String showAmountUsers(ModelMap model) {
        model.addAttribute("messages", userService.showAmountUsers());
        return "getAmountUsers";
    }

    //Create
    @GetMapping("/newUser")
    public String addUser(@ModelAttribute("user") User user) {
        return "/newUser";
    }
    @PostMapping("/")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/newUser";
        }
        userService.saveUser(user);
        return "redirect:/users/";
    }

    //Update(edit)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/edit";
    }

    @PutMapping("/{id}")
    public String upDate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors()){
            return "/edit";
        }
        userService.updateUser(user, id);
        return "redirect:/users/";
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(userService.getUserById(id));
        return "redirect:/users/";
    }
}