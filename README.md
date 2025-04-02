
---

## **Server Code Breakdown**
### **1️⃣ Server Setup (Listening for Clients)**
```java
import java.io.*;
import java.net.*;
```
- **Imports necessary Java classes**:
  - `java.io.*` → For handling input and output streams (reading/writing messages).
  - `java.net.*` → For network communication (ServerSocket and Socket classes).

```java
public class TCPServer {
    public static void main(String[] args) {
```
- Defines the **TCPServer** class and the `main` method (entry point of execution).

```java
        int port = 5000; // Define a port number
```
- The server will listen on **port 5000** for incoming client connections.

```java
        try (ServerSocket serverSocket = new ServerSocket(port)) {
```
- Creates a **ServerSocket** that listens on **port 5000**.
- The **`try-with-resources`** ensures the server socket is **automatically closed** when the program terminates.

---

### **2️⃣ Accepting Client Connections**
```java
            System.out.println("Server started. Waiting for clients...");
```
- Prints a message indicating that the server is running and waiting for clients.

```java
            while (true) {
                Socket clientSocket = serverSocket.accept();
```
- **`serverSocket.accept();`** waits for a client to connect.
- When a client connects, it returns a **Socket object** (`clientSocket`) that represents the connection.

```java
                System.out.println("New client connected: " + clientSocket.getInetAddress());
```
- Displays the **IP address** of the connected client.

---

### **3️⃣ Handling Multiple Clients Using Threads**
```java
                new ClientHandler(clientSocket).start();
```
- **Creates a new thread** for each client connection.
- `ClientHandler` is a separate class (defined below) that **manages communication** between the server and each client.

---

## **ClientHandler Class**
- Each client is handled in a **separate thread** to allow **multiple clients to connect at the same time**.

```java
class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
```
- The `ClientHandler` class extends **Thread** to allow **parallel execution**.
- The `clientSocket` stores the **connection** to the client.

---

### **4️⃣ Handling Client Communication**
```java
    public void run() {
```
- The `run()` method is **executed in a separate thread** when `.start()` is called.

```java
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
```
- **`BufferedReader in`** → Reads data sent from the client.
- **`PrintWriter out`** → Sends responses to the client.

---

### **5️⃣ Sending an Initial Response**
```java
            out.println("Connected to the server!");
```
- Sends a **welcome message** to the client.
- The **client receives this message** and prints it.

---

### **6️⃣ Receiving and Responding to Client Messages**
```java
            String message;
            while ((message = in.readLine()) != null) {
```
- Reads **incoming messages** from the client.

```java
                System.out.println("Received from client: " + message);
```
- Prints the received message on the **server console**.

```java
                out.println("Server received: " + message);
```
- Sends a **response** back to the client.

---

### **7️⃣ Closing the Client Connection**
```java
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```
- If an error occurs, it prints an error message.
- **Ensures the client connection is closed** when communication ends.

---

## **Interaction with TCPClient**
### **How the Client Connects to the Server**
#### **Client Code (TCPClient.java)**
```java
Socket socket = new Socket("192.168.1.100", 5000); // Connects to the server
```
- The client attempts to connect to the **server's IP and port**.
- The **server accepts the connection** (`serverSocket.accept()`).

---

### **Initial Communication**
```java
System.out.println("Server: " + in.readLine()); // Reads the welcome message
```
- The client **receives and prints** the `"Connected to the server!"` message sent by the server.

---

### **Sending Messages**
```java
out.println("Hello Server!"); // Client sends a message
```
- The **server reads** this message (`in.readLine()`) and prints:
  ```
  Received from client: Hello Server!
  ```
- Then the server responds:
  ```java
  out.println("Server received: " + message);
  ```
- The client **receives the response** and prints it.

---

### **Closing the Connection**
- If the client sends `"exit"`, it stops sending messages.
- The server detects the closed connection and **closes the socket**.

---

## **Summary of the Interaction**
1. **Server starts** and waits for connections.
2. **Client connects** to the server.
3. **Server sends a welcome message**.
4. **Client sends a message**.
5. **Server reads the message and responds**.
6. Steps **4 & 5 repeat** until the client disconnects.
7. **Server closes the connection** when the client exits.

---

## **Next Steps**
✅ Try running both **server** and **multiple clients** on different PCs.  
✅ Implement **message broadcasting** (so the server can send a message to all connected clients).  
✅ Add **authentication** (e.g., requiring a username before chatting).  





---

## **Client Code Breakdown**
### **1️⃣ Import Necessary Libraries**
```java
import java.io.*;
import java.net.*;
```
- **`java.io.*`** → For handling input and output operations.
- **`java.net.*`** → For network communication (creating sockets for TCP connections).

---

### **2️⃣ Define the Client Class and Main Method**
```java
public class TCPClient {
    public static void main(String[] args) {
```
- The **TCPClient** class contains the `main` method, which runs the client program.

---

### **3️⃣ Define Server IP and Port**
```java
        String serverIP = "192.168.1.100"; // Change to your server's IP
        int port = 5000;
```
- **`serverIP`** → The **IP address** of the server.
- **`port`** → Must **match** the server’s port (`5000` in this case).
- The client will attempt to connect to this **IP & port**.

---

### **4️⃣ Establish a Connection to the Server**
```java
        try (Socket socket = new Socket(serverIP, port);
```
- **Creates a TCP connection** to the server.
- `new Socket(serverIP, port);`:
  - If the **server is running**, it establishes a connection.
  - If the **server is down** or unreachable, it throws an exception.

---

### **5️⃣ Set Up Input and Output Streams**
```java
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
```
- **`in` (BufferedReader)** → Reads messages from the server.
- **`out` (PrintWriter)** → Sends messages to the server.
- **`userInput` (BufferedReader)** → Reads user input from the keyboard.

---

### **6️⃣ Display Connection Message**
```java
            System.out.println("Connected to the server!");
            System.out.println("Server: " + in.readLine());
```
- **Confirms the connection** by printing `"Connected to the server!"`.
- **Receives and prints** the welcome message from the server (`"Connected to the server!"` from `TCPServer`).

---

### **7️⃣ Start Sending Messages to the Server**
```java
            String message;
            while (true) {
                System.out.print("Enter message: ");
                message = userInput.readLine();
```
- Loops indefinitely to **keep sending messages** until the user types `"exit"`.
- **Reads user input** from the console (`userInput.readLine()`).

---

### **8️⃣ Stop Communication if User Enters 'exit'**
```java
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
```
- If the user types `"exit"`, **the loop breaks** and the client disconnects.

---

### **9️⃣ Send Message to the Server**
```java
                out.println(message);
```
- **Sends the user input** to the server via the **output stream** (`out.println()`).

---

### **🔄 10️⃣ Receive and Display the Server's Response**
```java
                System.out.println("Server response: " + in.readLine());
```
- The **server processes the message** and responds (`"Server received: <message>"`).
- The **client prints the response** received from the server.

---

### **11️⃣ Handle Exceptions (If Connection Fails)**
```java
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
- If the server is **not running** or **unreachable**, it prints an error message.
- **Handles network failures** or **connection timeouts**.

---

## **🖥️ Interaction with TCPServer**
| **Step** | **Client (TCPClient)** | **Server (TCPServer)** |
|----------|------------------------|------------------------|
| 1️⃣ | Starts & tries to connect to the server | Starts & waits for client connections |
| 2️⃣ | Connects via `new Socket(serverIP, port)` | Accepts connection via `serverSocket.accept()` |
| 3️⃣ | Reads & prints the welcome message | Sends `"Connected to the server!"` |
| 4️⃣ | Enters a message (`userInput.readLine()`) | Reads message from client (`in.readLine()`) |
| 5️⃣ | Sends message (`out.println(message)`) | Receives & processes the message |
| 6️⃣ | Receives & prints the server’s response | Sends a response (`out.println("Server received: " + message)`) |
| 7️⃣ | Repeats steps 4-6 until `"exit"` is entered | Keeps handling client messages in a loop |
| 8️⃣ | If `"exit"`, client disconnects | Server detects disconnect & closes socket |

---

## **🛠️ How to Test the Code**
### **1️⃣ Running the Server**
On the **server PC**, open a terminal and run:
```sh
javac TCPServer.java
java TCPServer
```
It should display:
```
Server started. Waiting for clients...
```

---

### **2️⃣ Running the Client**
On **a different PC (or same PC with another terminal)**:
```sh
javac TCPClient.java
java TCPClient
```
It should display:
```
Connected to the server!
Server: Connected to the server!
Enter message:
```
Then, you can type a message like:
```
Hello Server
```
And the response will be:
```
Server response: Server received: Hello Server
```

---

## **🌟 Next Steps & Enhancements**
🚀 **What’s next?**  
1️⃣ **Multi-client support:**  
- Right now, the server can **handle multiple clients** (because it uses threads), but clients **cannot talk to each other**.  
- We can **broadcast messages** from one client to all connected clients.

2️⃣ **GUI for Clients:**  
- Instead of using a terminal, create a **Java Swing/JavaFX GUI**.

3️⃣ **Encrypt Messages:**  
- Use **SSL/TLS** for secure communication.

