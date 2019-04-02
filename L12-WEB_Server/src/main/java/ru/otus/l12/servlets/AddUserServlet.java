package ru.otus.l12.servlets;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.RepositoryImp;
import ru.otus.l12.servieces.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserServlet extends HttpServlet {
    private final RepositoryImp repository;

    public AddUserServlet(RepositoryImp repository){
        this.repository = repository;
    }

    @Override
    protected  void doPost(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        String login = request.getParameter( "login" );
        String password = request.getParameter( "password" );

        MyUser user = new MyUser(login,password);
        UserService userService = new UserService( repository );
        userService.addUsers( user );

        response.sendRedirect( "/users");
        response.setStatus( HttpServletResponse.SC_OK );


    }

}
