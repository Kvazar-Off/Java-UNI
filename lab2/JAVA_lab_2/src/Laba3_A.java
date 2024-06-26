package gorshkovano.laba3;

//Импорт пакета для работы с паттерном класса Observable и интерфейса Observer.
import java.util.*;
//Импорт пакета для работы с тектовыми файлами.
import java.io.*;

//Импорт пакетов для настройки корректного отображения кириллических символов из пакета java.io в NetBeans.
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

//Классы наблюдаемых объектов.
class isInputConsole extends Observable
{
    void notifyObs()
    {
        setChanged();
        notifyObservers("console input");
    }
}

class isOutputConsole extends Observable
{
    void notifyObs()
    {
        setChanged();
        notifyObservers("console output");
    }
}

class isEqualityObject extends Observable
{
    void notifyObs()
    {
        setChanged();
        notifyObservers("equality object");
    }
}

//Классы обозревателей.
class WatcherInput implements Observer
{
   public void update(Observable obs, Object arg) 
   {
       System.out.println ("Received " + arg);
   }
}
class WatcherOutput implements Observer
{
    public void update(Observable obs, Object arg) 
   {
       System.out.println ("received " + arg);
   }
}
class WatcherEquality implements Observer
{
    public void update(Observable obs, Object arg) 
   {
       System.out.println ("received " + arg);
   }
}

class getSolution
{
    //Переменные файлов для исходных данных и логирования в "журнал".
    private File f_src;
    private File f_log;
    //Переменные для решения задачи.
    private int sumEvenNegativeNumbers = 0;
    private int sumOddPositiveNumbers = 0;
    //Обозреватели.
    private WatcherInput wi;
    private WatcherOutput wo;
    private WatcherEquality we;
    //Наблюдаемые объекты.
    private isInputConsole bwi;
    private isOutputConsole bwo;
    private isEqualityObject bwe;
    
    //Метод для создания наблюдаемых объектов и их обозревателей.
    public void createObjects()
    {
        //Создать объект приемника.
        wi = new WatcherInput();
        wo = new WatcherOutput();
        we = new WatcherEquality();
        //Создать объект источника.
        bwi = new isInputConsole();
        bwo = new isOutputConsole();
        bwe = new isEqualityObject();
        //Привязать источник к приемнику.
        bwi.addObserver(wi);
        bwo.addObserver(wo);
        bwe.addObserver(we);
    }
    //Метод для инициализации всех необходимых файлов, а также обработки файла с исходными данными.
    public void startProcessing()
    {
        //Создание файла для логирования.
        bwo.notifyObs();
        System.out.print("I/O: Enter the path of the log data file: ");
        this.f_log = this.filesProcessing(this.f_log);
        //Если файл для логирования уже существует, то к нему будет дозаписана строка /*New log to the same file:*/, а также новые логи. 
        if (this.f_log.length() != 0) 
        {
            try 
            {
                PrintWriter pw = new PrintWriter(new FileWriter(this.f_log, true));
                pw.println();
                pw.println("/*New log to the same file:*/");
                pw.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        log("O: Enter the path of the log data file: " + this.f_log);
        log("O: File created successfully: " + this.f_log.getAbsolutePath());
        
        //Чтение файла с исходными данными.
        bwo.notifyObs();
        System.out.print("I/O: Enter the path of the source data file: ");
        this.f_src = this.filesProcessing(this.f_src);
        log("I/O: Enter the path of the source data file: " + this.f_src);
        log("I/O: File created successfully: " + this.f_src.getAbsolutePath());
        
        //Чтение файла с исходными данными.
        StringBuilder sb_data = new StringBuilder();
        if (this.f_src.exists())
        {
            try (BufferedReader br_data = new BufferedReader(new FileReader(this.f_src.getAbsoluteFile())))
            {
                try
                {
                    String my_data;
                    while ((my_data = br_data.readLine()) != null)
                    {
                        sb_data.append(my_data);
                        sb_data.append("\n");
                    }
                }
                finally
                {
                    br_data.close();
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException();
            }
        }
        //Если файл с исходными данными пуст, то он будет удален, а пользователю будет предложено ввести данные самому.
        if (sb_data.toString().isEmpty())
        {
            this.f_src.delete();
            //Оповещения об обращениях к потоку ввода/вывода.
            bwo.notifyObs();
            log("O: Error: Source data file is empty.");
            System.out.println("O: Error: Source data file is empty.");
            bwo.notifyObs();
            log("O: File " + this.f_src.getAbsolutePath() + " was deleted.");
            System.out.println("O: File " + this.f_src.getAbsolutePath() + " was deleted.");
            bwo.notifyObs();
            log("O: Please enter the source data:");
            System.out.println("O: Please enter the source data:");
             
            Scanner sc_data = new Scanner(System.in);
            bwo.notifyObs();
            System.out.println("O: Enter numbers. Type 'end' to finish:");
            log("O: Enter numbers. Type 'end' to finish:");
            //Ввод чисел до ключевого слова "end" независимо от регистра его букв.
            while (true) 
            {
                bwo.notifyObs();
                System.out.print("I: ");
                String input_number = sc_data.nextLine();
                bwi.notifyObs();
                log("I: " + input_number);
                if (input_number.equalsIgnoreCase("end")) 
                {
                    System.out.println();
                    break;
                }
                try 
                {
                    int number = Integer.parseInt(input_number);
                    sb_data.append(number).append(" ");
                } 
                catch (NumberFormatException e) 
                {
                    //Оповещения об обращениях к потоку ввода/вывода.
                    bwo.notifyObs();
                    log("O: Invalid input. Please enter a number or 'end' to finish.");
                    System.out.println("O: Invalid input. Please enter a number or 'end' to finish.");
                }
            }
        }
        numberProcessing(sb_data.toString().split("\\s+|\n"));
    }
    
    //Метод для создания/чтения новых/уже существующих файлов.
    private File filesProcessing(File cur_file)
    {
        String filePath;
        Scanner sc = new Scanner(System.in);
        while (true) 
        {
            String input = sc.nextLine();
            bwi.notifyObs();
            if (!input.isEmpty()) 
            {
                filePath = input;
                cur_file = new File(filePath);
                try 
                {
                    if (!cur_file.exists()) 
                    {
                        cur_file.createNewFile();
                        bwo.notifyObs();
                        System.out.println("O: File created successfully: " + cur_file.getAbsolutePath());
                    }
                } 
                catch (IOException e) 
                {
                    throw new RuntimeException();                            
                }
                return cur_file;
            } 
            else 
            {
                //Оповещения об обращениях к потоку ввода/вывода.
                bwo.notifyObs();
                log("O: The file path was not retrieved correctly. Re-enter: ");
                System.out.print("O: The file path was not retrieved correctly. Re-enter: ");
            }
        }
    }
    
    //Метод логирования выводимых в консоль операций в "журнал".
    private void log(String message) 
    {
        try
        {

            PrintWriter pw = new PrintWriter(new FileWriter(this.f_log.getAbsoluteFile(), true));
            pw.println(message);
            pw.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }   
    
    //Основной метод для решения задачи.
    public void numberProcessing(String[] data) 
    {
        bwo.notifyObs();
        System.out.println("O: Entered numbers: " + Arrays.toString(data));
        log("O: Entered numbers: " + Arrays.toString(data));
        
        for (String number : data) 
        {
            try
            {
                int num = Integer.parseInt(number);
                if (num < 0 && num % 2 == 0) 
                {
                    bwo.notifyObs();
                    sumEvenNegativeNumbers += num;
                    System.out.println("O: Even negative number found: " + num);
                    log("O: Even negative number found: " + num);
                } 
                if (num > 0 && num % 2 == 1) 
                {
                    bwo.notifyObs();
                    sumOddPositiveNumbers += num;
                    System.out.println("O: Odd positive number found: " + num);
                    log("O: Odd positive number found: " + num);
                }
                if (num == 0)
                {
                    bwo.notifyObs();
                    System.out.println("O: Special number found: " + num);
                    log("O: Special number found: " + num);
                }
            }
            catch (NumberFormatException e)
            {
                bwo.notifyObs();
                System.out.println("Unhandled exception happened.");
                log("O: Unhandled exception happened.");
                System.exit(1);
            }
        }
        bwo.notifyObs();
        System.out.println("O: Sum of even negatives: " + sumEvenNegativeNumbers);
        log("O: Sum of even negatives: " + sumEvenNegativeNumbers);
        bwo.notifyObs();
        System.out.println("O: Sum of odd positives: " + sumOddPositiveNumbers);
        log("O: Sum of odd positives: " + sumOddPositiveNumbers);
    }
}

public class Laba3_A
{  
    public static void main(String[] args) 
    {
        //Установка кодировки для вывода.
        try 
        {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } 
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
        getSolution sol = new getSolution();
        sol.createObjects();
        sol.startProcessing();
    }
}