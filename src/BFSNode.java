public class BFSNode {
    private Move move;
    private Bitboard bb;
    private int depth;
    private int priority;
    private BFSNode parent;

    public BFSNode(Move move, Bitboard bb, int depth, BFSNode parent) {
        this.move = move;
        this.bb = bb;
        this.depth = depth;
        this.parent = parent;
        this.priority = depth + (this.move != null ? this.move.getCollisions() : 0);
    }


    public Move getMove() {
        return move;
    }

    public Bitboard getBoard() {
        return bb;
    }

    public int getDepth() {
        return depth;
    }

    public int getPriority() {
        return priority;
    }

    public BFSNode getParent() {
        return parent;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
