package ru.otus.L14.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.L14.services.UserService;
import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.RepositoryImp;
//import ru.otus.l10.orm.users.MyUser;
//import ru.otus.l11.hibernate.RepositoryImp;


import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping({"/","/user/list"})
    public String userList(Model model) throws ExecutionException, InterruptedException {
        List<MyUser> users = service.getUsers();
        model.addAttribute("users", users);
        return "userList.html";
    }

    @GetMapping("/user/create")
    public String userCreate(Model model) {
        model.addAttribute("user", new MyUser());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute MyUser user) {
        service.saveUserToDB(user);
        return new RedirectView("/user/list", true);
    }

}