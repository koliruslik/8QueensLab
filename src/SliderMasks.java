public class SliderMasks {

    public enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST,
        NORTH_WEST,
        NORTH_EAST,
        SOUTH_WEST,
        SOUTH_EAST
    }

    public static final Bitboard[][] MASKS = calcMasks();

    private static Bitboard[][] calcMasks() {
        Bitboard[][] masks = new Bitboard[64][8];
        for (int square = 0; square < 64; square++) {
            for (int dir = 0; dir < 8; dir++) {
                masks[square][dir] = calcMask(square, Direction.values()[dir]);
            }
        }
        return masks;
    }

    private static Bitboard calcMask(int p, Direction direction) {
        Bitboard mask = new Bitboard(0L);

        int x = p % 8;
        int y = p / 8;

        while (true) {
            switch (direction) {
                case NORTH -> y++;
                case SOUTH -> y--;
                case WEST -> x--;
                case EAST -> x++;
                case NORTH_WEST -> { y++; x--; }
                case NORTH_EAST -> { y++; x++; }
                case SOUTH_WEST -> { y--; x--; }
                case SOUTH_EAST -> { y--; x++; }
            }

            if (x < 0 || x > 7 || y < 0 || y > 7)
                break;

            mask = mask.set1(y * 8 + x);
        }

        return mask;
    }
}