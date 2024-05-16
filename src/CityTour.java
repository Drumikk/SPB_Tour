import java.util.*;

public class CityTour {

    static class Attraction {
        String name;
        double time;
        int importance;

        public Attraction(String name, double time, int importance) {
            this.name = name;
            this.time = time;
            this.importance = importance;
        }
    }

    public static void main(String[] args) {
        List<Attraction> attractions = Arrays.asList(
                new Attraction("Исаакиевский собор", 5, 10),
                new Attraction("Эрмитаж", 8, 11),
                new Attraction("Кунсткамера", 3.5, 4),
                new Attraction("Петропавловская крепость", 10, 7),
                new Attraction("Ленинградский зоопарк", 9, 15),
                new Attraction("Медный всадник", 1, 17),
                new Attraction("Казанский собор", 4, 3),
                new Attraction("Спас на Крови", 2, 9),
                new Attraction("Зимний дворец Петра I", 7, 12),
                new Attraction("Зоологический музей", 5.5, 6),
                new Attraction("Музей обороны и блокады Ленинграда", 2, 19),
                new Attraction("Русский музей", 5, 8),
                new Attraction("Навестить друзей", 12, 20),
                new Attraction("Музей восковых фигур", 2, 13),
                new Attraction("Литературно-мемориальный музей Ф.М. Достоевского", 4, 2),
                new Attraction("Екатерининский дворец", 1.5, 5),
                new Attraction("Петербургский музей кукол", 1, 14),
                new Attraction("Музей микроминиатюр 'Русский Левша'", 3, 18),
                new Attraction("Всероссийский музей А.С. Пушкина и филиалы", 6, 1),
                new Attraction("Музей современного искусства Эрарта", 7, 16)
        );

        //Вычитаем время на сон
        int availableTime = 48 - 16;

        List<Attraction> optimalTour = findOptimalTour(attractions, availableTime);
        System.out.println("Оптимальный маршрут:");
        for (Attraction attraction : optimalTour) {
            System.out.println(attraction.name + " (Время: " + attraction.time + "ч., Важность: " + attraction.importance + ")");
        }
    }

    public static List<Attraction> findOptimalTour(List<Attraction> attractions, int availableTime) {
        int n = attractions.size();
        int[] times = new int[n];
        int[] importances = new int[n];

        for (int i = 0; i < n; i++) {
            times[i] = (int) Math.ceil(attractions.get(i).time);
            importances[i] = attractions.get(i).importance;
        }

        int[][] dp = new int[n + 1][availableTime + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= availableTime; w++) {
                if (times[i - 1] <= w) {
                    dp[i][w] = Math.max(importances[i - 1] + dp[i - 1][w - times[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        List<Attraction> selectedAttractions = new ArrayList<>();
        int w = availableTime;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selectedAttractions.add(attractions.get(i - 1));
                w -= times[i - 1];
            }
        }

        return selectedAttractions;
    }
}
