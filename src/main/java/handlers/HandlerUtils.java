package handlers;

import com.sun.net.httpserver.HttpExchange;
import dao.StudentDao;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HandlerUtils {

    private static class InstanceHolder {
        private static final HandlerUtils instance = new HandlerUtils();
    }

    public static HandlerUtils getInstance() {
        return InstanceHolder.instance;
    }

    public void writeResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }


}
