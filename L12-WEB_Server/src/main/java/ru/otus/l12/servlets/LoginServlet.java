package ru.otus.l12.servlets;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.Repository;
import ru.otus.l12.server.UserService;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet  extends HttpServlet {

    private final Repository repository;

    public LoginServlet(Repository repository){
        this.repository = repository;
    }

    @Override
    protected  void doPost(HttpServletRequest request,
                           HttpServletResponse response) throws IOException{
        String name = request.getParameter("login");
        String password = request.getParameter("password");

        MyUser user = new MyUser(name,password);
        UserService userService = new UserService(repository);

        if( userService.authenticate(user.getLogin(),user.getPassword())){
          HttpSession session = request.getSession();
          session.setAttribute("login",user.getLogin() );
          session.setMaxInactiveInterval( 200000 );

          response.addCookie( createCookieForLoginUser(user.getLogin()) );
          response.setContentType( "text/html;charset=utf-8" );
          response.sendRedirect( "logon/admin.html");
          response.setStatus( HttpServletResponse.SC_OK );

        } else {
            PrintWriter out = response.getWriter();
            out.println( "Do not correct login or password" );
        }

    }

    private Cookie createCookieForLoginUser(String login){
        Cookie cookie = new Cookie("login",login );
        cookie.setMaxAge( 200000 );
        return cookie;
    }
}
