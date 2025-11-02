public class Bitboard {

    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Bitboard(){
        this.value = 0L;
    }

    public Bitboard(long value) {
        this.value = value;
    }

    public Bitboard and(Bitboard other) {
        return new Bitboard(this.value & other.value);
    }

    public Bitboard or(Bitboard other) {
        return new Bitboard(this.value | other.value);
    }

    public Bitboard xor(Bitboard other) {
        return new Bitboard(this.value ^ other.value);
    }

    public Bitboard not() {
        return new Bitboard(~this.value);
    }

    public Bitboard set1(int square) {
        return new Bitboard(value | (1L << square));
    }

    public Bitboard set0(int square) {
        return new Bitboard(value & ~(1L << square));
    }

    public boolean getBit(int square) {
        return (value & (1L << square)) != 0;
    }

    public int count1() {
        return Long.bitCount(value);
    }

//    public void print() {
//        final String RESET = "\u001B[0m";
//        final String BLACK_BG = "\u001B[40m";
//        final String WHITE_BG = "\u001B[47m";
//        final String RED_BG = "\u001B[41m";
//
//        System.out.println("    a   b   c   d   e   f   g   h");
//        System.out.println("  +---+---+---+---+---+---+---+---+");
//
//        for (int y = 0; y < 8; y++) {
//            int rank = 8 - y;
//            System.out.print(rank + " |");
//            for (int x = 0; x < 8; x++) {
//                int square = (7 - y) * 8 + x;
//                boolean hasQueen = getBit(square);
//
//                boolean isWhiteSquare = (x + y) % 2 == 1;
//
//                String bgColor = isWhiteSquare ? WHITE_BG : BLACK_BG;
//
//                if (hasQueen) {
//                    bgColor = RED_BG;
//                    String queenSymbol = " ";
//                    System.out.print(bgColor + " " + queenSymbol + " " + RESET + "|");
//                } else {
//                    System.out.print(bgColor + "   " + RESET + "|");
//                }
//            }
//            System.out.println(" " + rank);
//            System.out.println("  +---+---+---+---+---+---+---+---+");
//        }
//
//        System.out.println("    a   b   c   d   e   f   g   h");
//    }

    public void print() {
        // --- Цвета ФОНА ---
        final String RESET = "\u001B[0m";
        final String BLACK_BG = "\u001B[40m";    // Черный фон
        final String WHITE_BG = "\u001B[107m";   // Яркий Белый фон
        final String RED_BG = "\u001B[41m";      // Красный фон для "ферзя"

        // --- Параметры масштабирования ---
        final int CELL_HEIGHT = 2; // Внутренняя высота ячейки (строк)
        final int CELL_WIDTH = 4;  // Внутренняя ширина ячейки (пробелов)

        // Генерируем "внутренности" ячейки
        final String CELL_SPACES = " ".repeat(CELL_WIDTH); // "    "

        // --- Символы для рисования сетки ---
        final String H_LINE = "─".repeat(CELL_WIDTH); // "────"

        // Собираем полные строки-разделители
        String topBorder = "┌" + (H_LINE + "┬").repeat(7) + H_LINE + "┐";
        String midBorder = "├" + (H_LINE + "┼").repeat(7) + H_LINE + "┤";
        String botBorder = "└" + (H_LINE + "┴").repeat(7) + H_LINE + "┘";

        // Верхние заголовки (отступы подогнаны под сетку)
        System.out.println("     a    b    c    d    e    f    g    h");
        // Печатаем ВЕРХНЮЮ ГРАНИЦУ всей доски
        System.out.println("   " + topBorder);

        for (int y = 0; y < 8; y++) {
            int rank = 8 - y;

            // 1. Печатаем ВНУТРЕННИЕ строки ячейки (2 строки)
            for (int sub_y = 0; sub_y < CELL_HEIGHT; sub_y++) {

                // Печатаем номер ряда (только на первой строке ячейки)
                if (sub_y == 0) {
                    System.out.print(rank + "  │");
                } else {
                    System.out.print("   │"); // Пустой отступ
                }

                // Печатаем 8 ячеек в ряду
                for (int x = 0; x < 8; x++) {
                    int square = (7 - y) * 8 + x;
                    boolean hasQueen = getBit(square); // true, если ячейка "занята"
                    boolean isWhiteSquare = (x + y) % 2 == 1;

                    // Определяем фон
                    String bgColor;
                    if (hasQueen) {
                        bgColor = RED_BG;
                    } else {
                        bgColor = isWhiteSquare ? WHITE_BG : BLACK_BG;
                    }

                    // Печатаем ячейку (4 пробела) и правый разделитель
                    System.out.print(bgColor + CELL_SPACES + RESET + "│");
                }

                // Печатаем номер ряда справа
                if (sub_y == 0) {
                    System.out.println("  " + rank);
                } else {
                    System.out.println();
                }
            }

            // 2. Печатаем НИЖНИЙ РАЗДЕЛИТЕЛЬ ряда
            if (y < 7) {
                System.out.println("   " + midBorder);
            } else {
                // Это был последний ряд, печатаем НИЗ доски
                System.out.println("   " + botBorder);
            }
        }

        // Нижние заголовки
        System.out.println("     a    b    c    d    e    f    g    h");
    }


    private final byte[] BIT_SCAN_TABLE = {
            0, 47, 1, 56, 48, 27, 2, 60,
            57, 49, 41, 37, 28, 16, 3, 61,
            54, 58, 35, 52, 50, 42, 21, 44,
            38, 32, 29, 23, 17, 11, 4, 62,
            46, 55, 26, 59, 40, 36, 15, 53,
            34, 51, 20, 43, 31, 22, 10, 45,
            25, 39, 14, 33, 19, 30, 9, 24,
            13, 18, 8, 12, 7, 6, 5, 63
    };

    public int bsf() {
        long v = value;
        if (v == 0) return -1;
        long isolated = v ^ (v - 1);
        long index = (isolated * 0x03f79d71b4cb0a89L) >>> 58;
        return BIT_SCAN_TABLE[(int) index];
    }

    public int bsr() {
        long v = value;
        if (v == 0) return -1;
        v |= (v >> 1);
        v |= (v >> 2);
        v |= (v >> 4);
        v |= (v >> 8);
        v |= (v >> 16);
        v |= (v >> 32);
        long index = (v * 0x03f79d71b4cb0a89L) >>> 58;
        return BIT_SCAN_TABLE[(int) index];
    }

    public Bitboard move(Move move) {
        int from = move.getFrom();
        int to = move.getTo();

        long newValue = value & ~(1L << from);

        newValue = newValue | (1L << to);

        return new Bitboard(newValue);
    }

    public static String squareToStr(int square) {
        if (square < 0 || square > 63) return "?";
        int file = square % 8;
        int rank = square / 8;
        char fileChar = (char) ('a' + file);
        char rankChar = (char) ('1' + rank);
        return "" + fileChar + rankChar;
    }
}
