import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        double[] inputValues = EquationSolver.getInputValues();
        EquationSolver equationSolver = new EquationSolver();
        ArrayList<double[]> solutions = equationSolver.solveEquations(inputValues[0], inputValues[1], inputValues[2]);
        int[] testIndices = EquationSolver.getTestIndices(solutions.size());

        equationSolver.printAllValues(solutions);
        equationSolver.printValueAtIndex(solutions, testIndices);

        equationSolver.printExtrema(solutions);

        scanner.close();

    }
    @Test
    public void testEquationSolver() {
        double expectedX = 4.529999999999946;
        double expectedY1 = -1.4856638780163505;
        double expectedY2 = 0.03289850255805718;
        double error = 0.001;

        EquationSolver equationSolver = new EquationSolver();
        ArrayList<double[]> solutions = equationSolver.solveEquations(2, 5, 0.005);

        boolean passed = false;

        for (double[] solution : solutions) {
            double x = solution[0];
            double y1 = solution[1];
            double y2 = solution[2];

            if (Math.abs(x - expectedX) < error && Math.abs(y1 - expectedY1) < error && Math.abs(y2 - expectedY2) < error) {
                passed = true;
                break;
            }
        }

        assertTrue(passed);
    }

}


class EquationSolver {
    private static Scanner scanner = new Scanner(System.in);

    public ArrayList<double[]> solveEquations(double startX, double endX, double deltaX) {
        ArrayList<double[]> solutions = new ArrayList<>();

        while (startX <= endX) {
            double y1 = Math.sin(startX) * Math.log(startX);
            double y2 = Math.pow(Math.cos(startX), 2);

            double[] solution = {startX, y1, y2};
            solutions.add(solution);

            startX += deltaX;
        }

        return solutions;
    }

    public void printAllValues(ArrayList<double[]> solutions) {
        System.out.println("Бажаєте вивести всі значення? (так/ні)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("так")) {
            for (double[] solution : solutions) {
                double x = solution[0];
                double y1 = solution[1];
                double y2 = solution[2];
                System.out.println("x = " + x + ", y1 = " + y1 + ", y2 = " + y2);
            }
        } else {
            System.out.println("Значення не виведені.");
        }
    }

    public void printValueAtIndex(ArrayList<double[]> solutions, int[] indices) {
        for (int index : indices) {
            if (index < solutions.size()) {
                double[] solution = solutions.get(index);
                double x = solution[0];
                double y1 = solution[1];
                double y2 = solution[2];
                System.out.println("Індекс: " + index + ", x = " + x + ", y1 = " + y1 + ", y2 = " + y2);
            } else {
                System.out.println("Індекс " + index + " виходить за межі.");
            }
        }
    }

    public double[] findExtrema(ArrayList<double[]> solutions) {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        for (double[] solution : solutions) {
            double y1 = solution[1];

            if (y1 > max) {
                max = y1;
            }

            if (y1 < min) {
                min = y1;
            }
        }

        return new double[]{max, min};
    }

    public void printExtrema(ArrayList<double[]> solutions) {
        double[] extrema = findExtrema(solutions);
        double max = extrema[0];
        double min = extrema[1];

        System.out.println("Максимальне значення y1: " + max);
        System.out.println("Мінімальне значення y1: " + min);
    }

//    public void testEquationSolver(ArrayList<double[]> solutions) {
//        double expectedX = 4.529999999999946;
//        double expectedY1 = -1.4856638780163505;
//        double expectedY2 = 0.03289850255805718;
//        double error = 0.001;
//
//        boolean passed = false;
//
//        if (passed) {
//            System.out.println("Тест пройшов успішно.");
//        } else {
//            System.out.println("Тест не пройшов.");
//        }
//    }


    public static double[] getInputValues() {
        double[] values = new double[3];
        System.out.println("Введіть початкове значення x:");
        values[0] = scanner.nextDouble();

        System.out.println("Введіть кінцеве значення x:");
        values[1] = scanner.nextDouble();

        System.out.println("Введіть крок:");
        values[2] = scanner.nextDouble();

        return values;
    }

    public static int[] getTestIndices(int size) {
        System.out.println("Введіть індекси для тесту (розділені пробілом):");
        scanner.nextLine(); // Очищення буфера
        String indicesInput = scanner.nextLine();
        String[] indices = indicesInput.split(" ");
        int[] testIndices = new int[indices.length];
        for (int i = 0; i < indices.length; i++) {
            testIndices[i] = Integer.parseInt(indices[i]);
        }

        return testIndices;
    }
}
