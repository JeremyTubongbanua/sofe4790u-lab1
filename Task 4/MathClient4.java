/**
 * @author Qusay H. Mahmoud
 */

 import java.io.*;
 import java.net.*;
 
 public class MathClient4 {
    public static void main(String argv[]) throws Exception {
      DataInputStream br;
      DataOutputStream dos;
 
      Socket echo = new Socket("localhost", 3500);
      br = new DataInputStream(echo.getInputStream());
      // check if received "Server is busy"
      if (br.readLine().equalsIgnoreCase("Server is busy")) {
        System.out.println("Server is busy... Try again later.");
        return;
      } else {
        System.out.println("Connected to server...");
      }
      dos = new DataOutputStream(echo.getOutputStream());
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      int n = -1;
      int k = -1;
      System.out.println("Enter n: ");
      n = Integer.parseInt(in.readLine());
      System.out.println("Enter k: ");
      k = Integer.parseInt(in.readLine());
      dos.writeInt(n);
      dos.writeInt(k);
      String response = br.readLine();
      System.out.println("Server says: " + response);
      echo.close();
    }
 }
 