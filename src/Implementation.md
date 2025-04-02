**Project Overview**
You will create a TCP Server running on one PC.

Multiple TCP Clients will run on different PCs and connect to the server.

The server will handle multiple clients using multithreading.

Clients can send messages/requests, and the server will respond.

**Implementation Plan**
Setup a TCP Server

Use ServerSocket to listen for incoming connections.

Use Socket to communicate with connected clients.

Implement multithreading so the server can handle multiple clients concurrently.

Develop TCP Clients

Use Socket to connect to the server.

Implement input/output streams to send and receive data.

Enable Multi-Client Communication

Each client runs in a separate thread.

Server identifies clients (e.g., by IP/ID).

Server broadcasts messages or handles requests separately.

Test Across Multiple PCs

Ensure all devices are on the same network.

Use the server’s IP address to allow clients to connect remotely.