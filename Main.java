import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    static ArrayList<ComplexMatrix> list = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- меню ---");
            System.out.println("1 создать матрицу");
            System.out.println("2 показать все");
            System.out.println("3 сложить");
            System.out.println("4 умножить");
            System.out.println("5 разделить");
            System.out.println("6 транспонировать");
            System.out.println("7 определитель");
            System.out.println("0 выход");
            System.out.print("> ");

            int cmd;
            try {
                cmd = Integer.parseInt(in.nextLine().trim());
            } catch (Exception e) {
                System.out.println("не понял, введи число");
                continue;
            }

            try {
                switch (cmd) {
                    case 0:
                        return;
                    case 1:
                        create();
                        break;
                    case 2:
                        showAll();
                        break;
                    case 3:
                    case 4:
                    case 5:
                        doBinary(cmd);
                        break;
                    case 6:
                        ComplexMatrix t = get("какую транспонировать? ").transpose();
                        save(t);
                        break;
                    case 7:
                        ComplexMatrix m = get("для какой считать det? ");
                        System.out.println("det = " + m.det());
                        break;
                    default:
                        System.out.println("нет такого пункта");
                }
            } catch (MatrixException e) {
                System.out.println("ошибка: " + e.getMessage());
            }
        }
    }

    static void create() {
        int rows = askInt("строк: ");
        int cols = askInt("столбцов: ");
        if (rows < 1 || cols < 1) {
            System.out.println("размеры должны быть положительными");
            return;
        }

        ComplexMatrix mat = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println("элемент [" + (i + 1) + "][" + (j + 1) + "]");
                double re = askDouble("  re: ");
                double im = askDouble("  im: ");
                mat.set(i, j, new Complex(re, im));
            }
        }
        list.add(mat);
        System.out.println("сохранил под номером " + list.size());
    }

    static void showAll() {
        if (list.isEmpty()) {
            System.out.println("пока ничего нет");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("№" + (i + 1) + ":");
            System.out.print(list.get(i));
        }
    }

    static void doBinary(int op) throws MatrixException {
        ComplexMatrix a = get("первая матрица: ");
        ComplexMatrix b = get("вторая: ");
        ComplexMatrix res;
        if (op == 3) {
            res = a.add(b);
        } else if (op == 4) {
            res = a.mul(b);
        } else {
            res = a.div(b);
        }
        save(res);
    }

    static void save(ComplexMatrix res) {
        list.add(res);
        System.out.println("результат (№" + list.size() + "):");
        System.out.print(res);
    }

    static ComplexMatrix get(String prompt) {
        while (true) {
            int num = askInt(prompt);
            if (num >= 1 && num <= list.size()) {
                return list.get(num - 1);
            }
            System.out.println("нет матрицы с таким номером (всего " + list.size() + ")");
        }
    }

    static int askInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(in.nextLine().trim());
            } catch (Exception e) {
                System.out.println("нужно целое число");
            }
        }
    }

    static double askDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                String s = in.nextLine().trim().replace(',', '.');
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.println("нужно число");
            }
        }
    }
}