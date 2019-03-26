package ru.otus.l12.servlets;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.Repository;
import ru.otus.l12.server.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserServlet extends HttpServlet {

     private final Repository repository;

     public UserServlet(Repository repository){
         this.repository = repository;
     }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
       // Gson gson = new Gson();
      //  var names = request.getParameterValues( "name" );
        UserService userService = new UserService( repository );
        List<MyUser> listUser = userService.getUsers( );
        for (var item: listUser) {

        }

        response.setContentType( "text/html;charset=utf-8" );
        PrintWriter out = response.getWriter();
        for (var item:listUser) {
            out.println( "\n" + item.getName() );
        }

       // response.getWriter().println( gson.toJson( listUser ) );
        response.setStatus( HttpServletResponse.SC_OK );
    }
}
