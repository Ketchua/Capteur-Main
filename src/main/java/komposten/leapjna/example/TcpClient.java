package komposten.leapjna.example;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class TcpClient {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private volatile byte x = 0;
    private volatile byte y = 0;
    private volatile byte v = 0;
    private volatile boolean running; // Flag to control the thread
    private volatile int danger = 0;

    public TcpClient() {
        super();
    }

    /**
    * Thread-safe setter for x, y, and v.
    */
    public synchronized void setValues(byte x, byte y, byte v) {
        this.x = x;
        this.y = y;
        this.v = v;
        System.out.println("Values updated: x=" + x + ", y=" + y + ", v=" + v);
    }

    public synchronized void setDanger(int danger) {
        this.danger = danger;
    }




    public void connect(String host, int port) {
        if(socket != null) {
            System.out.println("Already connected to server.");
            return;
        }
        try {
            socket = new Socket();
            running = true; // Start the thread
            System.out.println("Connect to " + host + ":" + port);
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            // socket.bind(socketAddress);
            socket.connect(socketAddress, 5000);

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

                    // Start a thread to call send every 100ms
        new Thread(() -> {
            while (running && socket.isConnected()) {
                try {
                    // Example values for x, y, v
                    send();
                    Thread.sleep(100); // 100ms delay
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted: " + e.getMessage());
                    break;
                }
            }
        }).start();

        } catch (NumberFormatException exc) {
            System.out.println("Invalid port format : " + exc.getMessage());
        } catch (Exception exc) {
            System.out.println("Cannot connect to server " + exc.getMessage());
        }
    }

    public boolean isConnected() {
        return socket!= null && socket.isConnected();
    }

    private void send() {
        try {
            //TODO Utiliser danger
            byte[] frame = new byte[3];
            synchronized (this) {
                frame[0] = x;
                frame[1] = y;
                frame[2] = v;
            }
            dataOutputStream.write(frame, 0, frame.length);
        } catch (IOException exc) {
            System.out.println("Failed to send data : " + exc.getMessage());
        }
    }

    public void disconnect() {
        try {
            running = false; // Stop the thread
            if (socket != null && !socket.isClosed()) {
                socket.close();
                socket = null;
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
                dataOutputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }

            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.out.println("Error while disconnecting: " + e.getMessage());
        }
    }
}