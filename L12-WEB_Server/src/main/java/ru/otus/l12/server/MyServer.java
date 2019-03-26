package ru.otus.l12.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.FactoryRepositories;
import ru.otus.l11.hibernate.FactoryUserRepositoryOfHibernate;
import ru.otus.l11.hibernate.Repository;
import ru.otus.l12.filters.LogonFilter;
import ru.otus.l12.servlets.AddUserServlet;
import ru.otus.l12.servlets.LoginServlet;
import ru.otus.l12.servlets.UserServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MyServer implements ServerInterface {
    private final  int PORT;
    private final  String PUBLIC_HTML;
    private final Server server;

  public MyServer(int port, String pathHMTMDirectory){
      this.PORT=port;
      this.PUBLIC_HTML=pathHMTMDirectory;
      server=new Server( port );
  }


    public void start() throws  Exception {
        resourcesExample();
        MyUser user =new MyUser("admin","qwerty");
        FactoryRepositories factory = new FactoryUserRepositoryOfHibernate();
        Repository repository = factory.createRepository();
        UserService userService = new UserService(repository);
        userService.addUsers( user );

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addFilter( new FilterHolder( new LogonFilter() ),"/users",null );
        context.addFilter( new FilterHolder( new LogonFilter() ),"/addUser.html",null );
        context.addFilter( new FilterHolder( new LogonFilter() ),"/admin.html",null );
        context.addServlet( new ServletHolder( new LoginServlet(repository) ),"/Logon" );
        context.addServlet( new ServletHolder(new UserServlet(repository)), "/users");
        context.addServlet( new ServletHolder(new AddUserServlet(repository)), "/addUser");



        server.setHandler(new HandlerList(resourceHandler,context));

        server.start();
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

    private void resourcesExample() {
        URL url = MyServer.class.getResource(PUBLIC_HTML + "/index.html"); //local path starts with '/'
        System.out.println(url);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
