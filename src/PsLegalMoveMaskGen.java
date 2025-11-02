public class PsLegalMoveMaskGen {
    public static Bitboard calcRay(
            Bitboard bb,
            int p,
            int side,
            boolean onlyCaptures,
            SliderMasks.Direction direction,
            boolean bsr
    ) {
        Bitboard blockers = SliderMasks.MASKS[p][direction.ordinal()]
                .and(bb);

        if (blockers.getValue() == 0L) {
            return SliderMasks.MASKS[p][direction.ordinal()];
        }

        int blockingSquare = bsr ? blockers.bsr() : blockers.bsf();

        Bitboard result = SliderMasks.MASKS[p][direction.ordinal()]
                .xor(SliderMasks.MASKS[blockingSquare][direction.ordinal()]);

        result = result.set0(blockingSquare);

        return result;
    }
}
