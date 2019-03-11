package ru.otus.l12.servlets;

import com.google.gson.Gson;
import ru.otus.l11.hibernate.UserRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
         Gson gson = new Gson();
         var names = request.getParameter( "name" );
         UserRepository repos= new UserRepository();

         response.setContentType("text/html;charset=utf-8");
         response.setStatus(HttpServletResponse.SC_OK);
    }
}
