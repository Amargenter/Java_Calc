import java.util.*;

public class Main {
    static int first, last;

    static LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

    static {
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Здравствуйте. Введите операцию (+,-,/,*) с двумя операндами: ");
        System.out.println("Для выхода введите пустую строку.");
        while (true) {
            String input = scan.nextLine();
            if (input.isEmpty()) break;

            String inputWithoutSpaces = input.replaceAll("\\s", "");

            char[] lettersCharArray = inputWithoutSpaces.toCharArray();

            String[] lettersStringArray = inputWithoutSpaces.split("[+-/*]");

            try {
                if (lettersStringArray.length != 2) {
                    throw new Exception("Неправильный ввод.");
                }
                if (isNumber(lettersStringArray[0]) && isNumber(lettersStringArray[1])) {
                    //Оба числа арабские

                    first = Integer.parseInt(lettersStringArray[0]);
                    last = Integer.parseInt(lettersStringArray[1]);

                    if ((first <= 0 | first > 10) | (last <= 0 | last > 11))
                        throw new Exception("Входные числа должны быть в диапазоне [1:10].");

                    System.out.println(doActions(first, last, getAction(lettersCharArray)));

                } else if (!isNumber(lettersStringArray[0]) && !isNumber(lettersStringArray[1])) {
                    //Оба числа римские

                    first = romanToInt(lettersStringArray[0]);
                    last = romanToInt(lettersStringArray[1]);

                    if ((first <= 0 | first > 10) | (last <= 0 | last > 11))
                        throw new Exception("Входные числа должны быть в диапазоне [1:10].");

                    int r = doActions(first, last, getAction(lettersCharArray));
                    if (r<1) throw new Exception("В римской системе счисления нет отрицательных чисел и нуля.");
                    System.out.println(arabicToRomanConvert(r));
                } else throw new Exception("Используются разные системы счисления.");
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        }
    }

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Integer romanToInt(String s) {

        String sToUpper = s.toUpperCase();

        try {
            switch (sToUpper){
                case "I": return 1;
                case "II": return 2;
                case "III": return 3;
                case "IV": return 4;
                case "V": return 5;
                case "VI": return 6;
                case "VII": return 7;
                case "VIII": return 8;
                case "IX": return 9;
                case "X": return 10;
            }
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Неверный формат данных");
        }
        return -1;
    }

    /**
     * Возвращает символ операции
     **/
    public static char getAction(char[] act) throws Exception {
        ArrayList<Character> actions = new ArrayList<>();
        actions.add('+');
        actions.add('-');
        actions.add('/');
        actions.add('*');

        for (char ch : act) {
            if (actions.contains(ch)) {
                return ch;
            }
        }
        throw new Exception("Неправильный знак операции.");
    }

    /**
     * Выполняет расчеты
     */
    public static int doActions(int first, int last, char action) throws Exception {
        if (action == '+') {
            return first + last;
        }
        if (action == '-') {
            return first - last;
        }
        if (action == '*') {
            return first * last;
        }
        if (action == '/') {
            return first / last;
        }
        throw new Exception("Все плохо.");
    }

    public static String arabicToRomanConvert(int inputNumber, String previousAnswer) {

        String answer = previousAnswer;

        int maxNumber = 0;
        Iterator<Map.Entry<String, Integer>> mapIterator = map.entrySet().iterator();
        Map.Entry<String, Integer> entry = mapIterator.next();
        String key = entry.getKey();

        while (mapIterator.hasNext() && entry.getValue() <= inputNumber) {
            key = entry.getKey();
            maxNumber = entry.getValue();
            entry = mapIterator.next();
        }

        //Выделить целую часть и остаток
        float divided = inputNumber / (float) maxNumber;
        int intPart = (int) divided;

        int floatPart = inputNumber % maxNumber;

        //кол-во повторений intPart = количеству повторений одного символа
        for (int i = 0; i < intPart; i++)
        {
            answer = answer + key;
        }

        if (floatPart != 0) {
            //Теперь остаток не равен 0, поэтому мы снова вызываем функцию.
            return arabicToRomanConvert(floatPart, answer);
        }
        return answer;
    }

    public static String arabicToRomanConvert(int inputNumber) {
        return arabicToRomanConvert(inputNumber, "");
    }
}