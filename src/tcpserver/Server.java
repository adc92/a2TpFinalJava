package tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class Server {

    public static void main(String[] args) throws IOException {
        new Server().begin(4444);
    }
    //DataBase database = new DataBase();
    ArrayList<String> namesListOfTheaterPlays = new ArrayList<String>();
    ServerSocket serverSocket;

    public void begin(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            System.out.println("Waiting for clients to connect on port "
                    + port + "...");
            new ProtocolThread(serverSocket.accept()).start();
            //Thread.start() calls Thread.run()
        }
    }

    class ProtocolThread extends Thread {

        Socket socket;
        PrintWriter out_socket;
        BufferedReader in_socket;
        String theaterPlay;

        public ProtocolThread(Socket socket) {
            System.out.println("Accepting connection from "
                    + socket.getInetAddress() + "...");
            this.socket = socket;
            try {
                out_socket = new PrintWriter(socket.getOutputStream(), true);
                in_socket = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                System.out.println("Expecting request from client...");
                //sleep(5000);  
                Piece Scapin = new Piece("Blanche Neige", 200);
                Piece Arthur = new Piece("Le Roi Arthur", 3);
                Piece Babar = new Piece("Babar, le dernier éléphant", 300);
                Piece Alain = new Piece("Alain et les martiens", 1500);


                Scapin.achatPlace(20);
                System.out.println(Scapin.getNombrePlace());
                namesListOfTheaterPlays.add(Scapin.getNom());
                namesListOfTheaterPlays.add(Arthur.getNom());
                namesListOfTheaterPlays.add(Babar.getNom());
                namesListOfTheaterPlays.add(Alain.getNom());
                System.out.println(namesListOfTheaterPlays);

                theaterPlay = "";
                for (int i = 0; i < namesListOfTheaterPlays.size(); i++) {
                    theaterPlay += namesListOfTheaterPlays.get(i)
                            + (i == namesListOfTheaterPlays.size() - 1 ? "" : ";");
                }
                System.out.println(theaterPlay);
                out_socket.println(theaterPlay);

                //Tableau contenant le nom des pièces et le nombre de places
                String[][] table = {{"Blanche Neige", "200"},
                                    {"Le Roi Arthur", "3"},
                                    {"Babar, le dernier éléphant", "300"},
                                    {"Alain et les martiens", "1500"}};

                boolean reservationInProgress = true;
                while (reservationInProgress) {
                    //Recuperer le nombre de place et la piece
                    String j = in_socket.readLine();
                    System.out.println(j);
                    String values[] = j.split(";");
                    String placesNumber = values[0];
                    String theaterPlayName = values[1];
                    System.out.println("Nombre de place : " + placesNumber
                            + "|| Nom de la piece : " + theaterPlayName);
                    System.out.println(table[1][0]);
                    //Decrementer et tester 
                    for (int i = 0; i < table.length; i++) {
                        if (table[i][0].equals(theaterPlayName)) {
                            table[i][1] = Integer.toString(Integer.parseInt(
                                    table[i][1]) - Integer.parseInt(placesNumber));
                            System.out.println("Le nombre de place restant est : " 
                                    + table[i][1]);
                            if (Integer.parseInt(table[i][1]) < 0) {
                                out_socket.println("Désolé, il n'y a plus assez "
                                        + "de place(s) pour ce spectacle.");
                                table[i][1] = Integer.toString(Integer.parseInt(
                                    table[i][1]) + Integer.parseInt(placesNumber));
                            } else if (Integer.parseInt(table[i][1]) == 0) {
                                out_socket.println("Vous avez pris la(les) " 
                                        + placesNumber 
                                        + " dernière(s) place(s) ! Well done !");
                            } else {
                                out_socket.println("Votre réservation de " 
                                        + placesNumber +" place(s) "
                                        + "pour le spectacle " + theaterPlayName 
                                        + " a bien été prise en compte. " 
                                        + table[i][1] 
                                        + " place(s) encore disponible(s).");
                            }
                        } else {
                        }
                    }
                }

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
