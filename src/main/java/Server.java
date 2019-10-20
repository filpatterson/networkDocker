import Utils.Record;
import Utils.Utility;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Utils.Utility.recordsDatabase;

public class Server {
    /**
     * Set of available pools for handling requests from users
     */
    private static ExecutorService serverExecutor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        List<String> listRoutes = new ArrayList<>();
        listRoutes.add(Utility.defaultLink + Utility.homeRoute);
        Utility.getAccessToken();
        Utility.getLinksFromServer(listRoutes);

        while(Thread.activeCount() > 2) {}
        Utility.addDataFromServer();

        for(Record currentRecord : recordsDatabase) {
            System.out.println(currentRecord);
        }

        try {
            ServerSocket server = new ServerSocket(3345);
            System.out.println("Server is starting up, launch console reader to input your requests");

            while(!server.isClosed()) {
                Socket client = server.accept();
                serverExecutor.execute(new ClientHandler(client));
                System.out.println("Connection accepted");
            }
            serverExecutor.shutdown();
        } catch (IOException e) {
            System.out.println("Error in setting server: " + e);
        }
    }
}