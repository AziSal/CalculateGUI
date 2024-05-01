package calculategui;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Unique {
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
    // static BigDecimal target = new BigDecimal("12656.67");
    // static BigDecimal target = new BigDecimal("11500");
    static BigDecimal target = new BigDecimal("10733.40");

    public static void main(String[] args) {
        int targetInt = target.multiply(new BigDecimal("100")).intValue();
        int[] minUniqueCount = new int[targetInt + 1];
        List<Map<BigDecimal, Integer>> combinations = new ArrayList<>();
        for (int i = 0; i <= targetInt; i++) {
            minUniqueCount[i] = Integer.MAX_VALUE;
            combinations.add(new HashMap<>());
        }
        minUniqueCount[0] = 0;

        for (BigDecimal number : numbers) {
            int numberInt = number.multiply(new BigDecimal("100")).intValue();
            for (int i = numberInt; i <= targetInt; i++) {
                if (minUniqueCount[i - numberInt] != Integer.MAX_VALUE && 
                    minUniqueCount[i - numberInt] + (combinations.get(i - numberInt).containsKey(number) ? 0 : 1) < minUniqueCount[i]) {
                    minUniqueCount[i] = minUniqueCount[i - numberInt] + (combinations.get(i - numberInt).containsKey(number) ? 0 : 1);
                    Map<BigDecimal, Integer> newCombination = new HashMap<>(combinations.get(i - numberInt));
                    newCombination.put(number, newCombination.getOrDefault(number, 0) + 1);
                    combinations.set(i, newCombination);
                }
            }
        }

        if (minUniqueCount[targetInt] == Integer.MAX_VALUE) {
            System.out.println("No combination can sum to the target");
        } else {
            System.out.println("Minimum count of unique numbers: " + minUniqueCount[targetInt]);
            System.out.println("Solution found:");
            Map<BigDecimal, Integer> combination = combinations.get(targetInt);
            for (Map.Entry<BigDecimal, Integer> entry : combination.entrySet()) {
                System.out.println(entry.getKey() + " used " + entry.getValue() + " times");
            }
        }
    }
}