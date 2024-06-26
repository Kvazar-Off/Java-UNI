import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;

public class client {
  /*
   * ���� �������, � �������� ����������
   * ������������ ���������� �����
   */
  public static Scanner in = new Scanner(System.in);
  public static int PORT;
  public static String HOST;

  static void FileOutputStream(File outputfile, String text) {
    try {
      if (!outputfile.exists())
        outputfile.createNewFile();
      PrintWriter printer = new PrintWriter(new FileWriter(outputfile, true));
      try {
        printer.println(text);
      } finally {
        printer.close();
      }
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  public static void main(String[] args) throws IOException {
    System.out.println("Enter journal path : ");
    String path = in.nextLine();
    File file = new File(path);
    if (!file.exists())
    {
      System.out.println("Wrong file path");
      return;
    }
    System.out.println("Enter port number : ");
    PORT = Integer.parseInt(in.nextLine());
    System.out.println("Enter host name : ");
    HOST = in.nextLine();
    DatagramSocket clientSocket = new DatagramSocket();
    boolean exit = false;
    while (!exit) {
      try {
        /*
         * �������� ��������� ����������� ������.
         * ��� ������������� � �������� � ������������� �����
         */

        // �������� IP-����� �������
        InetAddress IPAddress = InetAddress.getByName(HOST);

        // �������� ��������������� ������
        byte[] sendingDataBuffer = new byte[1024];
        byte[] receivingDataBuffer = new byte[1024];

        System.out.print("Enter : ");
        String str = in.nextLine();
        if ("0".equals(str)) {
          exit = true;
        }

        /*
         * ������������ ������ � �����
         * � ���������� � �������
         */
        String sentence = str;
        sendingDataBuffer = sentence.getBytes();

        // �������� UDP-�����
        try 
        {
          DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress,
          PORT);
          clientSocket.send(sendingPacket);
        }
        catch (IOException e)
        {
          exit = true;
          throw new IOException();
        }
        
        // ��������� UDP-����� �������

        // �������� ����� �� �������, �.�. ����������� �� ��������� ����
        DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        clientSocket.receive(receivingPacket);

        // �������� �� ������ ���������� ������
        String receivedData = new String(receivingPacket.getData());
        FileOutputStream(file, receivedData);
        System.out.println("Sent from the server: " + receivedData);

        // �������� ���������� � �������� ����� �����
      } catch (SocketException e) {
        e.printStackTrace();
      }
    }
    clientSocket.close();
    in.close();
  }
}