import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        String serverIP = "192.168.100.10"; // Change to your server's IP
        int port = 5000;

        try (Socket socket = new Socket(serverIP, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server!");
            System.out.println("Server: " + in.readLine());

            String message;
            while (true) {
                System.out.print("Enter message: ");
                message = userInput.readLine();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                out.println(message);
                System.out.println("Server response: " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
