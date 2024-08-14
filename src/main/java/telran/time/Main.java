package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

record MonthYear(int month, int year) {}

public class Main {

    public static void main(String[] args) {
        try {
            MonthYear monthYear = getMonthYear(args);
            printCalendar(monthYear);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDates(monthYear);
    }

    private static void printTitle(MonthYear monthYear) {
        YearMonth yearMonth = YearMonth.of(monthYear.year(), monthYear.month());
        System.out.println("\n" + yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + monthYear.year());
    }

    private static void printWeekDays() {
        for (DayOfWeek day : DayOfWeek.values()) {
            System.out.printf("%s\t", day.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        }
        System.out.println();
    }

    private static void printDates(MonthYear monthYear) {
        YearMonth yearMonth = YearMonth.of(monthYear.year(), monthYear.month());
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        int lastDayOfMonth = yearMonth.lengthOfMonth();

        for (int i = 1; i < firstDayOfWeek; i++) {
            System.out.print("\t");
        }

        for (int day = 1; day <= lastDayOfMonth; day++) {
            System.out.printf("%d\t", day);
            if ((day + firstDayOfWeek - 1) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private static MonthYear getMonthYear(String[] args) throws Exception {
        int month;
        int year;
        
        if (args.length == 0) {
            LocalDate today = LocalDate.now();
            month = today.getMonthValue();
            year = today.getYear();
        } else if (args.length == 2) {
            try {
                month = Integer.parseInt(args[0]);
                year = Integer.parseInt(args[1]);
                if (month < 1 || month > 12) {
                    throw new Exception("Invalid month. Must be between 1 and 12.");
                }
            } catch (NumberFormatException e) {
                throw new Exception("Arguments must be integers.");
            }
        } else {
            throw new Exception("Requires 0 or 2 arguments: month and year.");
        }

        return new MonthYear(month, year);
    }

    private static int getFirstDayOfWeek(MonthYear monthYear) {
        LocalDate firstDay = LocalDate.of(monthYear.year(), monthYear.month(), 1);
        return firstDay.getDayOfWeek().getValue();
    }

    private static int getLastDayOfMonth(MonthYear monthYear) {
        YearMonth yearMonth = YearMonth.of(monthYear.year(), monthYear.month());
        return yearMonth.lengthOfMonth();
    }

    private static int getOffset(int firstWeekDay) {
        return firstWeekDay - 1;
    }
}
