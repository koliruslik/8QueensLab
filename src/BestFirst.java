import java.util.*;

public class BestFirst {
    private PriorityQueue<BFSNode> openSet;
    private Map<Bitboard, Integer> visited;

    private int nodesVisited = 0;
    private int solutionDepth = -1;
    private long elapsedTimeMs = 0;
    private boolean verbose = false;

    public BestFirst() {
        openSet = new PriorityQueue<>(Comparator.comparingInt(BFSNode::getPriority));
        visited = new HashMap<>();
    }

    public void search(Bitboard startBoard) {
        search(startBoard, false);
    }

    public void search(Bitboard startBoard, boolean verbose) {
        this.verbose = verbose;
        long startTime = System.nanoTime();

        BFSNode startNode = new BFSNode(null, startBoard, 0, null);
        openSet.clear();
        visited.clear();

        openSet.add(startNode);
        visited.put(startBoard, 0);

        while (!openSet.isEmpty()) {
            BFSNode currentNode = openSet.poll();
            nodesVisited++;

            if (verbose) {
                if (currentNode.getMove() != null) {
                    System.out.printf("Chosen move at iteration %d: Q: %s -> %s %d Collisions%n",
                            nodesVisited,
                            Bitboard.squareToStr(currentNode.getMove().getFrom()),
                            Bitboard.squareToStr(currentNode.getMove().getTo()),
                            currentNode.getMove().getCollisions()
                    );
                } else {
                    System.out.println("Starting position");
                }
            }

            Bitboard currentBoard = currentNode.getBoard();
            int currentDepth = currentNode.getDepth();

            if (isGoal(currentBoard)) {
                solutionDepth = currentDepth;
                long endTime = System.nanoTime();
                elapsedTimeMs = (endTime - startTime) / 1_000_000;

                currentBoard.print();

                printSolutionPath(currentNode);

                System.out.println("=== Goal is achieved ===");
                System.out.println("Nodes visited: " + nodesVisited);
                System.out.println("Solution depth: " + solutionDepth);
                System.out.printf("Elapsed time: %.2f ms%n", elapsedTimeMs + 0.0);
                return;
            }

            if (verbose) {
                System.out.printf("Depth: %d, Priority: %d%n", currentDepth, currentNode.getPriority());
                //currentBoard.print();
            }

            List<Move> nextMoves = QueenMoveGen.generateAllQueenMoves(currentBoard);

            for (Move move : nextMoves) {
                Bitboard newBoard = currentBoard.move(move);
                int newDepth = currentDepth + 1;

                if (!visited.containsKey(newBoard) || newDepth < visited.get(newBoard)) {
                    visited.put(newBoard, newDepth);
                    openSet.add(new BFSNode(move, newBoard, newDepth, currentNode));
                }
            }
        }

        long endTime = System.nanoTime();
        elapsedTimeMs = (endTime - startTime) / 1_000_000;

        System.out.println("=== Solution not found ===");
        System.out.println("Nodes visited: " + nodesVisited);
        System.out.printf("Elapsed time: %.2f ms%n", elapsedTimeMs + 0.0);
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
            System.out.printf("Q: %s -> %s%n", Bitboard.squareToStr(move.getFrom()), Bitboard.squareToStr(move.getTo()));
        }
    }
}
