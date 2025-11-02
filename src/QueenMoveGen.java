import java.util.ArrayList;
import java.util.List;

public class QueenMoveGen {

    public static List<Move> generateAllQueenMoves(Bitboard queens) {
        List<Move> moves = new ArrayList<>();

        Bitboard temp = new Bitboard(queens.getValue());
        while (temp.getValue() != 0L) {
            int from = temp.bsf();
            temp = temp.set0(from);

            List<Move> queenMoves = generateQueenMoves(queens, from);
            moves.addAll(queenMoves);
        }

        return moves;
    }

    public static List<Move> generateQueenMoves(Bitboard queens, int from) {
        List<Move> moves = new ArrayList<>();

        for (SliderMasks.Direction dir : SliderMasks.Direction.values()) {
            boolean bsr = switch (dir) {
                case NORTH, NORTH_EAST, NORTH_WEST, EAST -> false;
                default -> true;
            };

            Bitboard ray = PsLegalMoveMaskGen.calcRay(queens, from, 0, false, dir, bsr);

            long rayVal = ray.getValue();
            while (rayVal != 0) {
                int to = Long.numberOfTrailingZeros(rayVal);
                rayVal &= rayVal - 1;

                Move move = new Move(from, to);
                Bitboard newQueens = queens.move(move);
                int c =  QueensCollisionEvaluator.countQueenCollisions(newQueens);
                move.setCollisions(c);

                moves.add(move);
            }
        }

        return moves;
    }

}
