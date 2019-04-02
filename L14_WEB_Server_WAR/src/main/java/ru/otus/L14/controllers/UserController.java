package ru.otus.L14.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.L14.domain.User;
import ru.otus.L14.repository.UserRepository;
//import ru.otus.l10.orm.users.MyUser;
//import ru.otus.l11.hibernate.RepositoryImp;


import java.util.List;

@Controller
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping({"/", "/user/list"})
    public String userList(Model model) {
        List<User> users = repository.findAll();
        model.addAttribute("users", users);
        return "userList.html";
    }

    @GetMapping("/user/create")
    public String userCreate(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
        repository.create(user.getName());
        return new RedirectView("/user/list", true);
    }

}