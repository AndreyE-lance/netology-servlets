package netology.servlets.servlet;

import netology.servlets.service.PostService;
import netology.servlets.controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String PTH = "/netology_servlets_war_exploded/api/posts";
    private static final String PTH_ID = "/netology_servlets_war_exploded/api/posts/\\d+";

    @Override
    public void init() {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(netology.servlets.Config.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            var path = req.getRequestURI();
            if (path.equals(PTH)) {
                controller.all(resp);
                return;
            }
            if (path.matches(PTH_ID)) {
                var id = Integer.parseInt(path.split("/")[4]);
                controller.getById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            var path = req.getRequestURI();
            if (path.equals(PTH)) {
                controller.save(req.getReader(), resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            var path = req.getRequestURI();
            if (path.matches(PTH_ID)) {
                var id = Integer.parseInt(path.split("/")[4]);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    /*@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals("GET") && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }
            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.getById(id, resp);
                return;
            }
            if (method.equals("POST") && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }*/
}

