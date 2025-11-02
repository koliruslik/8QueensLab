public class BColumns {
    public static final long[] COLUMNS = calcColumns();
    public static final long[] INV_COLUMNS = calcInvColumns();

    private static long[] calcColumns() {
        long[] cols = new long[8];
        for (int x = 0; x < 8; x++) {
            long bb = 0L;
            for (int y = 0; y < 8; y++) {
                bb |= (1L << (y * 8 + x));
            }
            cols[x] = bb;
        }
        return cols;
    }

    private static long[] calcInvColumns() {
        long[] inv = new long[8];
        for (int i = 0; i < 8; i++) {
            inv[i] = ~COLUMNS[i];
        }
        return inv;
    }
}
