import com.sun.net.httpserver.HttpServer;
import handlers.AddHandler;
import handlers.GetHandler;
import handlers.RemoveHandler;
import java.net.InetSocketAddress;

public class MainServer {
    public static void main(String[] args) throws Exception{
        InitServer initServer = new InitServer();
        initServer.initServer();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/students/get", new GetHandler());
        server.createContext("/students/add", new AddHandler());
        server.createContext("/students/remove", new RemoveHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("The server is running");
    }
}
