package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.StudentDao;
import dao.StudentDaoImpl;
import student.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class GetHandler implements HttpHandler {
    HandlerUtils handlerUtils = HandlerUtils.getInstance();
    StudentDao studentDao = StudentDaoImpl.getInstance();
    public Properties pr ;
    public void handle(HttpExchange httpExchange) throws IOException {

        HashMap<Integer,Integer> st = new HashMap<Integer,Integer>();
      int size = st.size();
            Integer jp = (Integer)st.values().toArray()[0];
        for(Map.Entry<Integer,Integer> ent : st.entrySet()){
            if(size==1)
               ;
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = DriverManager.getConnection("1","2","3");
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT id, first, last, age FROM Employees";
                ResultSet rs = stmt.executeQuery(sql);
            }
            catch (Exception ex){

            }

        }

        System.out.println("Got a get request.");
        StringBuilder response = new StringBuilder();
        Map<String,String> parms = handlerUtils.queryToMap(httpExchange.getRequestURI().getQuery());
        response.append("<html><body>");
        response.append(studentDao.getStudent(parms.get(Student.ID_MEMBER)) + "<br/>");
        response.append("</body></html>");
        handlerUtils.writeResponse(httpExchange, response.toString());

    }
}
