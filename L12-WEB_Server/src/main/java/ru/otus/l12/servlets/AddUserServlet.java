package ru.otus.l12.servlets;

import com.google.gson.Gson;
import ru.otus.l10.orm.users.User;
import ru.otus.l11.hibernate.FactoryUserRepositoryOfHibernate;
import ru.otus.l11.hibernate.Repository;
import ru.otus.l12.server.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserServlet extends HttpServlet {
    @Override
    protected  void doPost(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        User user = gson.fromJson( request.getReader(),User.class );
        Repository repos = new FactoryUserRepositoryOfHibernate().createRepository();
        UserService userService = new UserService( repos );
        userService.addUsers( user );
    }

}
