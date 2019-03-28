package ru.otus.l12.servlets;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.Repository;
import ru.otus.l12.OtusHelperTemplate.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {
    TemplateProcessor templateProcessor;
    private static final String USERS_PAGE_TEMPLATE = "Users.html";
    private static final String USERS_PARAMETR_FOR_HTML = "users";

    private final Repository repository;

    public UserServlet(Repository repository, TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
        this.repository = repository;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        List<MyUser> users = (List<MyUser>) repository.getAll( MyUser.class );
        StringBuilder resultString = new StringBuilder();

        for (var itrm : users) {
            resultString.append( "<p> " + itrm.toString() + " </p>" );
        }

        Map<String, Object> userMap = new HashMap<>();
        userMap.put( USERS_PARAMETR_FOR_HTML, resultString.toString() );

        response.setContentType( "text/html;charset=utf-8" );
        response.getWriter().println( templateProcessor.getPage( USERS_PAGE_TEMPLATE, userMap ) );
        response.setStatus( HttpServletResponse.SC_OK );
    }
}
