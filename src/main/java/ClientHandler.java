import Utils.Record;
import Utils.Utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

public class ClientHandler implements Runnable {

    private static Socket clientDialog;

    /**
     * Constructor that sets client for available thread
     * @param client client who wants to get data from server
     */
    public ClientHandler(Socket client) {
        ClientHandler.clientDialog = client;
    }

    /**
     * Function for setting work with client and getting requests from him
     */
    @Override
    public void run() {

        try {
            DataOutputStream outputStream = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream inputStream = new DataInputStream(clientDialog.getInputStream());
            System.out.println("Channels for input and output are set up");

            while (!clientDialog.isClosed()) {
                System.out.println("Reading data from channel...");

                String queue = inputStream.readUTF();

                System.out.println("ClientDialog message = " + queue);

                if (queue.equalsIgnoreCase("quit")) {
                    System.out.println("Client requested channel closing ...");
                    outputStream.writeUTF("Server reply - " + queue + " OK");
                    Thread.sleep(2000);
                    break;
                }

                if(queue.equalsIgnoreCase("select random")){
                    System.out.println("Writing to output channel");
                    outputStream.writeUTF("Request result = {" + Utility.getRandomRecord() + '}');
                    System.out.println("Data was successfully transmitted");

                }else if(queue.equalsIgnoreCase("select not null id")){
                    System.out.println("Writing to output channel");
                    outputStream.writeUTF("Request result = {\n\t");
                    for(Record currentRecord : Utility.getAllNotNullIdRecords())
                        outputStream.writeUTF(currentRecord.toString() + "\n\t");
                    outputStream.writeUTF("}");
                    System.out.println("Data was successfully transmitted");

                }else if(queue.contains("select")){
                    queue = queue.toLowerCase();
                    queue = queue.trim().replaceAll(" +", " ");
                    String[] input = queue.split(" ");
                    System.out.println("Writing to output channel");
                    outputStream.writeUTF("Server reply - " + Utility.readCommandFromUser(input) + " - OK");
                    System.out.println("Data was successfully transmitted");

                }else{
                    System.out.println("Writing to output channel");
                    outputStream.writeUTF("Request result = failed: no such command, repeat");
                    System.out.println("Data was successfully transmitted");
                }
                outputStream.flush();
            }

            System.out.println("Client disconnected. Close connection.");

            inputStream.close();
            outputStream.close();

            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
