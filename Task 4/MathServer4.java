
/**
 * @author Qusay H. Mahmoud
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class MathServer4 {

  private static int threadsRunning;
  private final static int MAX_THREADS = 2;

  public static class MathThread implements Runnable {
    Socket client;

    public MathThread(Socket client) {
      this.client = client;
    }

    @Override
    public void run() {
      try {
        System.out.println("IP: " + client.getInetAddress().getHostAddress());
        System.out.println("Port: " + client.getPort());
        DataInputStream br;
        DataOutputStream dos;
        br = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
        int x = br.readInt();
        System.out.println("I got: " + x);
        int y = br.readInt();
        System.out.println("I got: " + y);
        // int ans = add(x,y);
        if (x <= y) {
          System.out.println("Invalid input: x must be greater than y");
          dos.writeBytes("the sum is: " + -1 + "\n");
        } else {
          int ans = binomialCoefficient(x, y);
          System.out.println("I am sending the answer...");
          dos.writeBytes("the sum is: " + ans + "\n");
        }
      } catch (Exception e) {
        System.out.println("Error: " + e);
      } finally {
        try {
          client.close();
          threadsRunning--;
          System.out.println("Thread is closing... Now, there are " + threadsRunning + " threads running");
        } catch (Exception e) {
          System.out.println("Error closing client socket");
        }
      }
    }
  }

  public static int binomialCoefficient(int n, int k) {
    if (k == 0 || k == n) {
      return 1;
    }
    return binomialCoefficient(n - 1, k - 1) + binomialCoefficient(n - 1, k);
  }

  public static void main(String argv[]) throws Exception {
    ServerSocket hi = new ServerSocket(3500);
    System.out.println("Server Listening on port 3500....");

    while (threadsRunning <= MAX_THREADS) {
      Socket client = hi.accept();
      threadsRunning++;
      if(threadsRunning > MAX_THREADS) {
        System.out.println("Max threads reached. Closing connection...");
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeBytes("Server is busy\n");
        client.close();
        threadsRunning--;
        continue;
      } else {
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeBytes("Server is ready\n");
      }
      System.out.println("Staring thread... Now, there are " + threadsRunning + " threads running");
      new Thread(new MathThread(client)).start();
    }

    hi.close();
  }

}
