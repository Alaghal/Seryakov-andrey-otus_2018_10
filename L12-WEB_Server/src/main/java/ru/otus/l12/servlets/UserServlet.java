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

        UserService userService = new UserService( repository );

        response.setContentType( "text/html;charset=utf-8" );
        PrintWriter out = response.getWriter();
        List<MyUser> userlist = (List<MyUser>) repository.getAll( MyUser.class);
        StringBuilder resultString = new StringBuilder();
        System.out.println( userlist.size()+"-1234" );
        for (var itrm:userlist) {
            resultString.append(itrm.toString() + "\n") ;
        }

        out.println( resultString.toString() );
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
}
