///**
// * Вариант 10
// * Дана последовательность целых чисел. Программа должна сообщить каких чисел
// * встретилось больше: «чётных и положительных» и «нечётных и положительных», либо
// * их поровну.
// *
// * Исключения:000011100
// * Больше, чем некоторое число
// * В массиве число элементов больше указанного
// * Меньше, чем некоторое число
// */
//
//import java.util.Scanner;
//
//// Интерфейс для работы с последовательностью целых чисел
//interface NumberSequence {
//    static final int MAX_ALLOWED_VALUE = 100;
//    static final int MIN_ALLOWED_VALUE = -5;
//    static final int MAX_ARRAY_LENGTH = 10;
//    int[] getSequence() throws MaximumLengthException, MinimumValueException, MaximumValueException;
//
//}
//
//// Интерфейс для выполнения задачи
//interface TaskSolver {
//    void solveTask(int[] sequence);
//}
//
//// Исключение, выбрасываемое при вводе числа, которое больше, чем заданное число
//class MaximumValueException extends Exception {
//    public MaximumValueException(String message) {super(message);
//    }
//}
//
//// Исключение, выбрасываемое при превышении максимального числа элементов в последовательности
//class MaximumLengthException extends Exception {
//    public MaximumLengthException(String message) {super(message);
//    }
//}
//
//// Исключение, выбрасываемое при вводе числа, которое меньше, чем заданное число
//class MinimumValueException extends Exception {
//    public MinimumValueException(String message) {super(message);}
//}
//
//// Основной класс, реализующий интерфейсы
//class SequenceProcessor implements NumberSequence, TaskSolver {
//    private int[] sequence;
//
//    @Override
//    public int[] getSequence() throws MaximumLengthException, MinimumValueException, MaximumValueException {
//        Scanner scanner = new Scanner(System.in);
//
//            System.out.print("Введите количество элементов последовательности: ");
//            int size = scanner.nextInt();
//
//            if (size <= 0 || size > NumberSequence.MAX_ARRAY_LENGTH) {
//                throw new MaximumLengthException("Количество элементов должно быть больше 0 и не превышать "
//                        + NumberSequence.MAX_ARRAY_LENGTH);
//            }
//
//            sequence = new int[size];
//
//            System.out.println("Введите элементы последовательности:");
//
//            for (int i = 0; i < size; i++) {
//                System.out.print("Элемент " + (i + 1) + ": ");
//                int num = scanner.nextInt();
//
//                if (num > NumberSequence.MAX_ALLOWED_VALUE) {
//                    throw new MaximumValueException("Введено число больше максимально допустимого значения: "
//                            + NumberSequence.MAX_ALLOWED_VALUE);
//                } else if (num < NumberSequence.MIN_ALLOWED_VALUE) {
//                    throw new MinimumValueException("Введено число меньше минимально допустимого значения: "
//                            + NumberSequence.MIN_ALLOWED_VALUE);
//                }
//
//                sequence[i] = num;
//            }
//
//            scanner.close();
//            return sequence;
//    }
//
//    @Override
//    public void solveTask(int[] sequence)  {
//
//        int evenPositiveCount = 0;
//        int oddPositiveCount = 0;
//
//        for (int num : sequence) {
//            if (num > 0) {
//                if (num % 2 == 0) {
//                    evenPositiveCount++;
//                } else {
//                    oddPositiveCount++;
//                }
//            }
//        }
//
//        if (evenPositiveCount > oddPositiveCount) {
//            System.out.println("Четных и положительных чисел больше");
//        } else if (evenPositiveCount < oddPositiveCount) {
//            System.out.println("Нечетных и положительных чисел больше");
//        } else {
//            System.out.println("Четных и нечетных положительных чисел поровну");
//        }
//    }
//}
//
//
//public class Lab2 {
//    public static void main(String[] args) {
//        try {
//            SequenceProcessor processor = new SequenceProcessor();
//            int[] sequence = processor.getSequence();
//            processor.solveTask(sequence);
//        } catch (MaximumValueException | MinimumValueException | MaximumLengthException e) {
//            System.out.println("Ошибка: " + e);
//        }
//    }
//}
//
