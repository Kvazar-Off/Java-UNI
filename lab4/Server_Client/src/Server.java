import java.io.*;
import java.net.*;

public class Server {
    private static final int TIME_SEND_SLEEP = 100;
    private ServerSocket servSocket;
    public static int PORT;
    BufferedWriter writer;
    private String journalFileName;
    private int id = 0;

    public static void main(String[] args) {
        Server server = new Server();
        server.go();
    }

    public Server() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Введите ПОРТ: ");
            servSocket = new ServerSocket(Integer.parseInt(in.readLine()));

            System.out.print("Введите путь к журналу сервера: "); // D:\KAI\JAVA\labs\lab4\Server_Client\journal_server.txt
            journalFileName = in.readLine();

        } catch (IOException e) {
            System.err.println("Не удаётся открыть сокет для сервера: " + e.toString());
        }
    }

    public void writeToJournal(String entry) {
        try (FileWriter writer = new FileWriter(journalFileName, true)) {
            writer.write(entry + "\n");
        } catch (IOException e) {
            System.err.println("Error occurred while writing to the server journal: " + e.getMessage());
        }
    }

    public void go() {
        class Listener implements Runnable {
            Socket socket;
            BufferedReader in;
            BufferedWriter out;
            int listId = 0;

            public Listener(Socket aSocket, int id) {
                socket = aSocket;
                listId = id;
            }

            public void run() {
                try {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        StringBuilder expression = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            String operandStr = line.substring(0, line.length() - 1);
                            Boolean num = true;
                            try {
                                Double.parseDouble(operandStr);
                            } catch (NumberFormatException e) {
                                num = false;
                            }
                            System.out.println("Ожидание строки: ");
                            writeToJournal("Ожидание строки клиента № " + listId + ": ");
                            if (line.endsWith("=") && num) {
                                expression.append(line);
                                System.out.println("Выражение получено: " + line);
                                writeToJournal("Выражение клиента № " + listId + " получено: " + line);
                                double result = calculateExpression(expression.toString());
                                writeToJournal("Результат клиента № " + listId + ": " + result);
                                out.write("Результат = " + result + "\n");
                                out.flush();
                                expression.setLength(0);
                            } else if((line.endsWith("-") || line.endsWith("+")) && num){
                                expression.append(line);
                                writeToJournal("Выражение клиента № " + listId + " принято:" + line);
                                out.write("Выражение клиента принято\n" );
                                out.flush();
                            }
                            else{
                                writeToJournal("Неверный ввод клиента № " + listId + ":" + line);
                                out.write("Неверный ввод. Повторите процедуру.\n" );
                                out.flush();
                            }
                        }
                    } finally {
                        in.close();
                        out.close();
                    }
                } catch (IOException e) {
                    System.err.println("Исключение: " + e.toString());
                    --id;
                }
            }
        }

        System.out.println("Сервер запущен...");
        while (true) {
            try {
                Socket socket = servSocket.accept();
                Listener listener = new Listener(socket, ++id);
                Thread thread = new Thread(listener);
                thread.start();
            } catch (IOException e) {
                System.err.println("Исключение: " + e.toString());
                --id;
            }
        }
    }

    public double calculateExpression(String expression) throws ArithmeticException, NumberFormatException {
        String removedLastChar = expression.substring(0, expression.length() - 1);
        String[] tokens = removedLastChar.split("[\\+\\-]");

        double result = Double.parseDouble(tokens[0]);

        int operatorIndex = tokens[0].length();
        for (int i = 1; i < tokens.length; i++) {
            char operator = removedLastChar.charAt(operatorIndex);
            operatorIndex += tokens[i].length() + 1;

            double operand = Double.parseDouble(tokens[i]);

            switch (operator) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
            }
        }

        return result;
    }
}
