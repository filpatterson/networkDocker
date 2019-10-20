import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    /**
     * Function that creates client for connection with server using sockets.
     * @throws InterruptedException
     */
    Client() throws InterruptedException {
        try (
             Socket socket = new Socket("localhost", 3345);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());) {

             System.out.println("Reading/Writing channels are active.\nWaiting for request from client:");

             while (!socket.isOutputShutdown()) {
                 if (reader.ready()) {

                     String clientCommand = reader.readLine();

                     outputStream.writeUTF(clientCommand);
                     outputStream.flush();
                     System.out.println("Client sent message \"" + clientCommand + "\" to server.");
                     Thread.sleep(1000);

                     if (clientCommand.equalsIgnoreCase("quit")) {
                         System.out.println("Client kill connections");
                         Thread.sleep(2000);

                         if (inputStream.available() != 0) {
                             System.out.println("reading...");
                             String in = inputStream.readUTF();
                             System.out.println(in);
                         }
                         break;
                     }
                     System.out.println("Client wrote & start waiting for data from server...");
                     Thread.sleep(2000);

                     if (inputStream.available() != 0) {
                         System.out.println("reading...");
                         String in = inputStream.readUTF();
                         System.out.println(in);
                     }
                 }
             }

             System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
