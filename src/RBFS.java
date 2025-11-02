import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RBFS {
    private int nodesVisited = 0;
    private int solutionDepth = -1;
    private long elapsedTimeMs = 0;

    private BFSNode solutionNode = null;
    private boolean verbose = false;

    public void search(Bitboard startBoard) {
        search(startBoard, false);
    }

    public void search(Bitboard startBoard, boolean verbose) {
        this.verbose = verbose;
        long startTime = System.nanoTime();

        BFSNode startNode = new BFSNode(null, startBoard, 0, null);
        float f_limit = Float.POSITIVE_INFINITY;

        float result = rbfs(startNode, f_limit);

        long endTime = System.nanoTime();
        elapsedTimeMs += (endTime - startTime) / 1_000_000;

        if (solutionNode != null) {
            solutionDepth = solutionNode.getDepth();
            solutionNode.getBoard().print();
            printSolutionPath(solutionNode);

            System.out.println("=== Goal is achieved ===");
            System.out.println("Nodes visited: " + nodesVisited);
            System.out.println("Solution depth: " + solutionDepth);
            System.out.printf("Elapsed time: %.2f ms%n", elapsedTimeMs + 0.0);
        } else {
            System.out.println("=== Solution not found ===");
            System.out.println("Nodes visited: " + nodesVisited);
            System.out.printf("Elapsed time: %.2f ms%n", elapsedTimeMs + 0.0);
        }
    }

    private float rbfs(BFSNode node, float f_limit) {
        nodesVisited++;

        if (verbose) {
            System.out.printf("Visited node at depth %d with priority %d, f_limit=%.2f%n",
                    node.getDepth(), node.getPriority(), f_limit);
        }

        if(isGoal(node.getBoard())) {
            solutionNode = node;
            return -1;
        }

        List<BFSNode> successors = new ArrayList<>();
        List<Move> moves = QueenMoveGen.generateAllQueenMoves(node.getBoard());

        for(Move move : moves) {
            Bitboard newBoard = node.getBoard().move(move);
            BFSNode child = new BFSNode(move, newBoard, node.getDepth() + 1, node);
            child.setPriority(child.getPriority() + move.getCollisions());
            successors.add(child);
        }

        if(successors.isEmpty()) {
            return Float.POSITIVE_INFINITY;
        }

        successors.sort(Comparator.comparingInt(BFSNode::getPriority));

        while (true) {
            BFSNode best = successors.get(0);

            if(best.getPriority() > f_limit) {
                return best.getPriority();
            }

            float alternative = successors.size() > 1 ? successors.get(1).getPriority() : Float.POSITIVE_INFINITY;

            float result = rbfs(best, Math.min(f_limit, alternative));
            if(result == -1){
                return -1;
            }

            best.setPriority((int) result);

            successors.sort(Comparator.comparingInt(BFSNode::getPriority));
        }
    }

    private boolean isGoal(Bitboard board) {
        return QueensCollisionEvaluator.countQueenCollisions(board) == 0;
    }

    private void printSolutionPath(BFSNode node) {
        List<Move> movesPath = new ArrayList<>();
        BFSNode current = node;

        while (current != null && current.getMove() != null) {
            movesPath.add(current.getMove());
            current = current.getParent();
        }

        Collections.reverse(movesPath);

        System.out.println("=== Solution moves ===");
        for (Move move : movesPath) {
            System.out.printf("Q: %s -> %s\n", Bitboard.squareToStr(move.getFrom()), Bitboard.squareToStr(move.getTo()));
        }
    }
}
