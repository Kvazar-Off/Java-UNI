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
   * Порт сервера, к которому собирается
   * подключиться клиентский сокет
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
         * Создайте экземпляр клиентского сокета.
         * Нет необходимости в привязке к определенному порту
         */

        // Получите IP-адрес сервера
        InetAddress IPAddress = InetAddress.getByName(HOST);

        // Создайте соответствующие буферы
        byte[] sendingDataBuffer = new byte[1024];
        byte[] receivingDataBuffer = new byte[1024];

        System.out.print("Enter : ");
        String str = in.nextLine();
        if ("0".equals(str)) {
          exit = true;
        }

        /*
         * Преобразуйте данные в байты
         * и разместите в буферах
         */
        String sentence = str;
        sendingDataBuffer = sentence.getBytes();

        // Создайте UDP-пакет
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
        
        // Отправьте UDP-пакет серверу

        // Получите ответ от сервера, т.е. предложение из заглавных букв
        DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        clientSocket.receive(receivingPacket);

        // Выведите на экране полученные данные
        String receivedData = new String(receivingPacket.getData());
        FileOutputStream(file, receivedData);
        System.out.println("Sent from the server: " + receivedData);

        // Закройте соединение с сервером через сокет
      } catch (SocketException e) {
        e.printStackTrace();
      }
    }
    clientSocket.close();
    in.close();
  }
}