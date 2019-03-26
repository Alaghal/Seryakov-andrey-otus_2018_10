package ru.otus.l12.servlets;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.FactoryUserRepositoryOfHibernate;
import ru.otus.l11.hibernate.Repository;
import ru.otus.l12.server.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AddUserServlet extends HttpServlet {
    private final Repository repository;

    public AddUserServlet(Repository repository){
        this.repository = repository;
    }

    @Override
    protected  void doPost(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
      //  Gson gson = new Gson();
     //   MyUser user = gson.fromJson( request.getReader(),MyUser.class );
        String login = request.getParameter( "login" );
        String password = request.getParameter( "password" );
        MyUser user = new MyUser(login,password);
        UserService userService = new UserService( repository );
        userService.addUsers( user );
        PrintWriter out = response.getWriter();
        MyUser myUser = userService.getUserForLogin( user.getLogin() );
        List<MyUser> myUserList = userService.getUsers();

        out.println( "MyUser with login "+user.getLogin()+" " +user.getId() +" added");


    }

}
