package ru.otus.l12.servlets;

import com.google.gson.Gson;
import ru.otus.l10.orm.users.User;
import ru.otus.l11.hibernate.FactoryRepositories;
import ru.otus.l11.hibernate.FactoryUserRepositoryOfHibernate;
import ru.otus.l11.hibernate.Repository;
import ru.otus.l12.server.UserService;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet  extends HttpServlet {

    @Override
    protected  void doPost(HttpServletRequest request,
                           HttpServletResponse response) throws IOException{

        Gson gson = new Gson();
        User user = gson.fromJson( request.getReader(),User.class );
        FactoryRepositories factory = new FactoryUserRepositoryOfHibernate();
        Repository repository = factory.createRepository();
        UserService userService = new UserService(repository);
        if( userService.authenticate(user.getLogin(),user.getPassword())){
          HttpSession session = request.getSession();
          session.setAttribute("login",user.getLogin() );
          session.setMaxInactiveInterval( 200000 );

          Cookie cookie = new Cookie("login", user.getLogin());
          cookie.setMaxAge( 200000 );
          response.addCookie( cookie );
        } else {
            PrintWriter out = response.getWriter();
            out.println( "Do not correct login or password" );
        }



    }
}
