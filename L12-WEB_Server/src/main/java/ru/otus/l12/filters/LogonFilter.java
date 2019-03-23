package ru.otus.l12.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogonFilter implements Filter {
   private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
           context=filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse =(HttpServletResponse) response;

          HttpSession session = httpServletRequest.getSession(false);
          if(session == null){
              httpServletResponse.setStatus( 403 );
          } else {
              chain.doFilter(request, response);
          }
    }

    @Override
    public void destroy() {

    }
}
