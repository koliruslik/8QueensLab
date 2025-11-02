public class BRows {
    public static final long[] ROWS = calcRows();
    public static final long[] INV_ROWS = calcInvRows();

    private static long[] calcRows() {
        long[] rows = new long[8];
        for (int y = 0; y < 8; y++) {
            long bb = 0L;
            for (int x = 0; x < 8; x++) {
                bb |= (1L << (y * 8 + x));
            }
            rows[y] = bb;
        }
        return rows;
    }

    private static long[] calcInvRows() {
        long[] inv = new long[8];
        for (int i = 0; i < 8; i++) {
            inv[i] = ~ROWS[i];
        }
        return inv;
    }
}
