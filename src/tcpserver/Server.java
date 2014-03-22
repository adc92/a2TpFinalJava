package tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    public static void main(String[] args) throws IOException {
        new Server().begin(4444);
    }

    
    ServerSocket serverSocket;

    public void begin(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            System.out.println("Waiting for clients to connect on port " + port + "...");
            new ProtocolThread(serverSocket.accept()).start();
            //Thread.start() calls Thread.run()
        }
    }

    class ProtocolThread extends Thread {

        Socket socket;
        PrintWriter out_socket;
        BufferedReader in_socket;
        Random random;
        int number;
        int num;

        public ProtocolThread(Socket socket) {
            System.out.println("Accepting connection from " + socket.getInetAddress() + "...");
            this.socket = socket;
            random = new Random();
            try {
                out_socket = new PrintWriter(socket.getOutputStream(), true);
                in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void newGame() throws Exception {
            if ("new game".equals(in_socket.readLine())) {
                    number = random.nextInt(5);
                    //test si ca marche
                    out_socket.println("ready");
                    System.out.println(number);
            }else {
                throw new Exception("Wrong client answer");
            }
            
        }
        public void compare(int num){
             if(num==number){
                    String i="0";
                    out_socket.println(i);
                }
                else if(num>number)
                {
                    String i="1";
                    out_socket.println(i);
                }
                else{
                    String i="2";
                    out_socket.println(i);
                }
        }
        @Override
        public void run() {
            try {
                System.out.println("Expecting request from client...");
                //sleep(5000);
                newGame();
                // recevoir réponse du client (premier numéro)
                do{
                String numero = in_socket.readLine();
                System.out.println(numero);
                num = Integer.parseInt(numero);
                compare(num);
                
                }while(number!=num);
                
                
               
                // comparer les deux numéros
                // envoyer plus ou moins
                // recommencer tant que les deux numéros ne sont pas égaux
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("Closing connection.");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
