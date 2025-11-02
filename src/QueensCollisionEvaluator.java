public class QueensCollisionEvaluator {

    public static int countQueenCollisions(Bitboard queens) {
        int collisions = 0;
        long q = queens.getValue();

        for (int square = 0; square < 64; square++) {
            if ((q & (1L << square)) == 0) continue;

            for (SliderMasks.Direction dir : SliderMasks.Direction.values()) {

                Bitboard blockers = SliderMasks.MASKS[square][dir.ordinal()].and(queens);

                collisions += blockers.count1();
            }
        }

        return collisions / 2;
    }
}