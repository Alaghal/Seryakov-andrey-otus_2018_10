package ru.otus.l12.servlets;

import com.google.gson.Gson;
import ru.otus.l10.orm.users.User;
import ru.otus.l11.hibernate.FactoryUserRepositoryOfHibernate;
import ru.otus.l11.hibernate.Repository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        var names = request.getParameterValues( "name" );
        Repository repos = new FactoryUserRepositoryOfHibernate().createRepository();
        List<User> listUser = new ArrayList<>();
        for (var name : names) {
            User user = (User) repos.getByValue( "login", name, User.class );
            listUser.add( user );
        }
        response.setContentType( "text/html;charset=utf-8" );
        response.getWriter().println( gson.toJson( listUser ) );
        response.setStatus( HttpServletResponse.SC_OK );
    }
}
