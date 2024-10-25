
/**
 * @author Qusay H. Mahmoud
 */

import java.io.*;
import java.net.*;

public class MathServer {

  ServerSocket hi;
  Socket client;
  DataInputStream br;
  DataOutputStream dos;

  public static int add(int a, int b) {
    return a + b;
  }

  public static int binomialCoefficient(int n, int k) {
    if (k == 0 || k == n) {
      return 1;
    }
    return binomialCoefficient(n - 1, k - 1) + binomialCoefficient(n - 1, k);
  }

  public static void main(String argv[]) throws Exception {
    new MathServer();
  }

  public MathServer() throws Exception {
    hi = new ServerSocket(3500);
    System.out.println("Server Listening on port 3500....");
    client = hi.accept();
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
  }
}
