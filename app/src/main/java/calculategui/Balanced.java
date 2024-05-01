package calculategui;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;

public class Balanced {
    static BigDecimal[] numbers = {
        new BigDecimal("400"),
        new BigDecimal("500"),
        new BigDecimal("800"),
        new BigDecimal("9400"),
        new BigDecimal("10900"),
        new BigDecimal("9200"),
        new BigDecimal("10800"),
        new BigDecimal("266.67"),
        new BigDecimal("316.66"),
        new BigDecimal("410"),
        new BigDecimal("463.33"),
        new BigDecimal("313.33"),
        new BigDecimal("363.33"),
        new BigDecimal("9500"),
        new BigDecimal("12300"),
        new BigDecimal("8000"),
        new BigDecimal("9800"),
        new BigDecimal("8200"),
        new BigDecimal("514.29"),
        new BigDecimal("417.43"),
        new BigDecimal("3300"),
        new BigDecimal("3600"),
        new BigDecimal("3200"),
        new BigDecimal("364.29"),
        new BigDecimal("5800"),
        new BigDecimal("414.29"),
        new BigDecimal("5100"),
        new BigDecimal("6000"),
        new BigDecimal("5200"),
        new BigDecimal("328.57"),
        new BigDecimal("376.19"),
        new BigDecimal("6900"),
        new BigDecimal("7900"),
        new BigDecimal("263.33"),
        new BigDecimal("6700"),
        new BigDecimal("223.33"),
        new BigDecimal("296.67"),
        new BigDecimal("8900"),

    };
    static BigDecimal target = new BigDecimal("10733.40");

    static int[] bestSolution;
    static int minUniqueCount = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[] solution = new int[numbers.length];
        Arrays.sort(numbers, Collections.reverseOrder());  // Larger numbers first

        if (findCombination(solution, 0, BigDecimal.ZERO, 0)) {
            System.out.println("Optimal solution found using the fewest unique numbers:");
            for (int i = 0; i < bestSolution.length; i++) {
                if (bestSolution[i] > 0) {
                    System.out.println(numbers[i] + " used " + bestSolution[i] + " times");
                }
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    private static boolean findCombination(int[] solution, int index, BigDecimal currentSum, int uniqueCount) {
        if (currentSum.compareTo(target) == 0) {
            if (uniqueCount < minUniqueCount) {
                bestSolution = Arrays.copyOf(solution, solution.length);
                minUniqueCount = uniqueCount;
            }
            return true;
        }
        if (index == numbers.length || currentSum.compareTo(target) > 0) {
            return false;
        }

        // Prune if the unique count already exceeds the best found solution's unique count
        if (uniqueCount >= minUniqueCount) {
            return false;
        }

        // Calculate the potential maximum sum available from this point
        BigDecimal potentialSum = currentSum;
        for (int i = index; i < numbers.length; i++) {
            potentialSum = potentialSum.add(numbers[i]);
        }
        if (potentialSum.compareTo(target) < 0) {
            return false;  // Prune the branch if even the maximum possible sum is insufficient
        }

        boolean found = false;
        int maxUse = (target.subtract(currentSum)).divide(numbers[index], RoundingMode.DOWN).intValue();
        for (int i = 0; i <= maxUse; i++) {
            solution[index] = i;
            if (i > 0) {
                // Only increment uniqueCount if we are actually using this number
                found |= findCombination(solution, index + 1, currentSum.add(numbers[index].multiply(BigDecimal.valueOf(i))), uniqueCount + 1);
            } else {
                found |= findCombination(solution, index + 1, currentSum, uniqueCount);
            }
        }
        solution[index] = 0; // Reset the current index for other recursive paths
        return found;
    }
}
