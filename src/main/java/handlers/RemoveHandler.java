package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.StudentDao;
import dao.StudentDaoImpl;
import student.Student;

import java.io.IOException;
import java.util.Map;

public class RemoveHandler implements HttpHandler {

    HandlerUtils handlerUtils = HandlerUtils.getInstance();
    StudentDao studentDao = StudentDaoImpl.getInstance();

    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Got a remove request.");
        StringBuilder response = new StringBuilder();
        Map<String,String> params = handlerUtils.queryToMap(httpExchange.getRequestURI().getQuery());
        response.append("<html><body>");
        response.append(studentDao.removeStudent(params.get(Student.ID_MEMBER)) + "<br/>");
        response.append("</body></html>");
        handlerUtils.writeResponse(httpExchange, response.toString());
    }
}
