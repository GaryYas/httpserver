package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.StudentDao;
import dao.StudentDaoImpl;
import student.Student;

import java.io.IOException;
import java.util.Map;

public class AddHandler implements HttpHandler {
    HandlerUtils handlerUtils = HandlerUtils.getInstance();
    StudentDao studentDao = StudentDaoImpl.getInstance();

    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Got an add request.");
        StringBuilder response = new StringBuilder();
      //  System.out.println(httpExchange.getRequestURI().getQuery());
    //    System.out.println(httpExchange.getRequestURI());
        Map<String, String> params = handlerUtils.queryToMap(httpExchange.getRequestURI().getQuery());
        Student student = new Student(params);
        response.append("<html><body>");
        response.append(studentDao.saveStudent(student) + "<br/>");
        response.append("</body></html>");
        handlerUtils.writeResponse(httpExchange, response.toString());
    }
}
