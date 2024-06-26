/**
 * Вариант 10
 * Дана последовательность целых чисел. Программа должна сообщить каких чисел
 * встретилось больше: «чётных и положительных» и «нечётных и положительных», либо
 * их поровну.
 *
 * явная реализация событий:
 * 1. Равенство указанного объекта некоторому значению
 * 2. Обращение к потоку вывода с консоли
 * 3. Изменение указанной переменной
 *
 * 1. Реализовать ввод данных с консоли и из указанного файла ( с консоли вы вводите путь к файлу, где записаны
 * последовательность sequence и путь к файлу из пункта 2).
 * 2. Все операции вывода на консоль должны дублироваться выводом в указанный файл
 * «Журнала» (путь к нему задать  из файла , который упомянут в Пункте 1).
 * 3. При написании необходимого класса следует пользоваться одним  способом
 * генерации, перехвата и обработки событий, а именно: явная реализация события.
 * 4. Показ использования функций класса обеспечить созданием объектов класса в
 * функции public static void main(String[] args) и вызовом функций этих объектов. При показе
 * следует в череде примеров предъявлять факты генерации, перехвата и обработки
 * требуемых событий. Включить в отчёт исходные данные тестов использования функций
 * класса и соответствующие им результаты вывода на консоль и в файл «Журнала».
 */


//Импорт пакета для работы с паттерном класса Observable и интерфейса Observer.
import java.util.*;
//Импорт пакета для работы с тектовыми файлами.
import java.io.*;

// Интерфейс, реализующий явное событие "Обращение к потоку вывода с консоли"
interface ConsoleInputEvent {
    void onConsoleInputEvent(String message, File outputFile);
}

// Интерфейс, реализующий явное событие "равенство указанного объекта некоторому значению"
interface EqualityEvent {
    void onEqualityEvent(String message, File outputFile);
}

// Интерфейс, реализующий явное событие "Обращение к потоку вывода с консоли"
interface ConsoleOutputEvent {
    void onConsoleOutputEvent(String message, File outputFile);
}

// Интерфейс, реализующий явное событие "Изменение указанной переменной"
interface VariableChangeEvent {
    void onVariableChangeEvent(String message, File outputFile);
}

//Класс-источник события "Изменение указанной переменной"
class VariableChangeEventSource {
    VariableChangeEvent event;
    VariableChangeEventSource(VariableChangeEvent event) {this.event = event;}
    void generateEvent(String message, File outputFile) {
        event.onVariableChangeEvent(message, outputFile);
    }
}

//Класс-приемник события "Изменение указанной переменной"
class VariableChangeEventReceiver implements VariableChangeEvent {
    public void onVariableChangeEvent(String message, File outputFile) {
        System.out.println("Variable Change Receiver Event: " + message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write("Variable Change Receiver Event: " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

//Класс-источник события "Обращение к потоку вывода с консоли"
class ConsoleOutputEventSource {
    ConsoleOutputEvent event;
    ConsoleOutputEventSource(ConsoleOutputEvent event){this.event = event;}
    void generateEvent(String message, File outputFile) {
        event.onConsoleOutputEvent(message, outputFile);
    }
}

//Класс-приемник события "Обращение к потоку вывода с консоли"
class ConsoleOutputEventReceiver implements ConsoleOutputEvent {
    public void onConsoleOutputEvent(String message, File outputFile) {
        System.out.println("Output Console Stream Event: " + message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write("Output Console Stream Event: " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

//Класс-источник события "равенство указанного объекта некоторому значению"
class EqualityEventSource {
    EqualityEvent event;
    EqualityEventSource(EqualityEvent event) {this.event = event; }
    void generateEvent(String message, File outputFile) {
        event.onEqualityEvent(message, outputFile);
    }
}

//Класс-приемник события "равенство указанного объекта некоторому значению"
class EqualityEventReceiver implements EqualityEvent {
    @Override
    public void onEqualityEvent(String message, File outputFile) {
        System.out.println("Function Equality Event: " + message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write("Function Equality Event: " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

class NumberInputHandler {
    VariableChangeEventSource variableChangeEventSource;
    ConsoleOutputEventSource consoleOutputEventSource;
    EqualityEventSource equalityEventSource;

    File inputFile;
    File outputFile;

    int numberOfPositiveOddNumbers;
    int numberOfPositiveEvenNumbers;

    NumberInputHandler(VariableChangeEventSource variableChangeSource,
                       ConsoleOutputEventSource consoleOutputEventSource,
                       EqualityEventSource equalityEventSource)
    {
        this.variableChangeEventSource = variableChangeSource;
        this.consoleOutputEventSource = consoleOutputEventSource;
        this.equalityEventSource = equalityEventSource;
    }

    public void initializeInputFile() {
        Scanner scanner = new Scanner(System.in);

        while (inputFile == null || !inputFile.exists()) {
            System.out.print("Введите путь до файла, где находится путь до Журнала и заданная последовательность: ");
            String filePath = scanner.nextLine();
            inputFile = new File(filePath);

            if (!inputFile.exists()) {
                System.err.println("Файл не существует. Попробуйте ещё раз.");
            }
        }
    }


    public void initializeOutputFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = reader.readLine();
            outputFile = new File(line);
            if (!outputFile.exists()) {
                System.out.println("Не удалось найти Журнал.");
            } else {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
                    writer.newLine();
                    writer.write("------Start of new log------");
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Ошибка чтения файла: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public void handleNumberInput() {
        numberOfPositiveOddNumbers = 0;
        numberOfPositiveEvenNumbers = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String firstLine = reader.readLine();
            String numbersString = reader.readLine();
            String[] arguments = numbersString.split(" ");

            for (String argument : arguments) {
                processArgument(argument);
                equalityEventSource.generateEvent("processArgument evaluated", outputFile);
            }

        } catch (IOException exception) {
            System.err.println("Ошибка чтения файла: " + exception.getMessage());
        }

        printResult();
    }

    private void processArgument(String argument) {
        int currentNumber = Integer.parseInt(argument);
        if (currentNumber % 2 == 0 && currentNumber > 0) {
            ++numberOfPositiveEvenNumbers;
            variableChangeEventSource.generateEvent("incremented number of positive elements", outputFile);
        } else if (currentNumber > 0) {
            ++numberOfPositiveOddNumbers;
        }
    }

    private void printResult() {
        if (numberOfPositiveOddNumbers > numberOfPositiveEvenNumbers) {
            System.out.println("Нечётных положительных чисел больше");
            consoleOutputEventSource.generateEvent("More odd numbers", outputFile);
        } else if (numberOfPositiveOddNumbers < numberOfPositiveEvenNumbers) {
            System.out.println("Чётных положительных чисел больше");
            consoleOutputEventSource.generateEvent("More even numbers", outputFile);
        } else {
            System.out.println("Нечётных положительных и чётных положительных чисел поровну");
            consoleOutputEventSource.generateEvent("Same amount of even and odd numbers", outputFile);
        }
    }
}

public class Lab3 {
    public static void main(String[] args) {
        VariableChangeEventReceiver variableChangeReceiver = new VariableChangeEventReceiver();
        VariableChangeEventSource variableChangeSource = new VariableChangeEventSource(variableChangeReceiver);

        ConsoleOutputEventReceiver consoleOutputEventReceiver = new ConsoleOutputEventReceiver();
        ConsoleOutputEventSource consoleOutputEventSource = new ConsoleOutputEventSource(consoleOutputEventReceiver);

        EqualityEventReceiver functionEvaluationReceiver = new EqualityEventReceiver();
        EqualityEventSource functionEvaluationSource = new EqualityEventSource(functionEvaluationReceiver);

        NumberInputHandler numberInputHandler =
                new NumberInputHandler(variableChangeSource, consoleOutputEventSource, functionEvaluationSource);

        numberInputHandler.initializeInputFile();
        numberInputHandler.initializeOutputFile();
        numberInputHandler.handleNumberInput();
    }
}

