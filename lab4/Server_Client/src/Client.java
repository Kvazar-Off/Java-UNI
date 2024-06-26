import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String serverAddress;
    private static int serverPort;
    private static String clientLogFilePath;

    public static void main(String[] args) {
        loadConfig(); // Загружаем настройки из файла

        if (serverAddress == null || serverPort == 0 || clientLogFilePath == null) {
            System.err.println("Ошибка загрузки настроек. Убедитесь, что файл конфигурации корректен.");
            return;
        }

        try {
            clientSocket = new Socket(serverAddress, serverPort);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            System.out.println("Введите сообщение для сервера (для завершения ввода введите 'exit'):");

            while (true) {
                String clientMessage = reader.readLine();
                if (clientMessage.equalsIgnoreCase("exit")) {
                    break; // Выход из цикла при вводе команды 'exit'
                }

                out.write(clientMessage + "\n");
                out.flush();

                // Чтение ответа от сервера
                String serverResponse = in.readLine();
                System.out.println("Ответ от сервера: " + serverResponse);

                // Запись полученного сообщения в журнал клиента
                writeClientLog(clientMessage, serverResponse);
            }

        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
            }
        }
    }

    private static void loadConfig() {
        try {
            BufferedReader configReader = new BufferedReader(new FileReader("config.txt"));
            String line;
            while ((line = configReader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0].trim()) {
                        case "server_address":
                            serverAddress = parts[1].trim();
                            break;
                        case "server_port":
                            serverPort = Integer.parseInt(parts[1].trim());
                            break;
                        case "client_logfile_path":
                            clientLogFilePath = parts[1].trim();
                            break;
                    }
                }
            }
            configReader.close();
        } catch (IOException e) {
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
        }
    }

    private static void writeClientLog(String clientMessage, String serverResponse) {
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(clientLogFilePath, true))) {
            logWriter.write("Отправлено клиентом: " + clientMessage + "\n");
            logWriter.write("Ответ сервера: " + serverResponse + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка записи в журнал клиента: " + e.getMessage());
        }
    }
}
