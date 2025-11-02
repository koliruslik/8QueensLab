import java.util.List;

public class SearchRunner {
    public static void main(String[] args) {
        BestFirst bestFirstSolver = new BestFirst();
        RBFS rbfsSolver = new RBFS();

        List<Bitboard> startBoards = GeneratePositionsBatch.generatePositions(20);

        System.out.println("=== BestFirst Results ===");
        System.out.printf("%-10s %-15s %-15s %-20s%n", "Ітерації", "К-сть гл. кутів", "Всього вузлів", "Всього вузлів у пам’яті");

        for (Bitboard board : startBoards) {
            bestFirstSolver.search(board, false);

            int iterations = bestFirstSolver.getNodesVisited();
            int solutionDepth = bestFirstSolver.getSolutionDepth();
            int totalNodes = bestFirstSolver.getTotalVisitedNodes();
            int maxNodesInMemory = bestFirstSolver.getMaxNodesInMemory();

            System.out.printf("%-10d %-15d %-15d %-20d%n", iterations, solutionDepth, totalNodes, maxNodesInMemory);
        }

        System.out.println("\n=== RBFS Results ===");
        System.out.printf("%-10s %-15s %-15s %-20s%n", "Ітерації", "К-сть гл. кутів", "Всього вузлів", "Всього вузлів у пам’яті");

        for (Bitboard board : startBoards) {
            rbfsSolver.search(board, false);

            int iterations = rbfsSolver.getNodesVisited();
            int solutionDepth = rbfsSolver.getSolutionDepth();
            int totalNodes = rbfsSolver.getTotalVisitedNodes();
            int maxNodesInMemory = rbfsSolver.getMaxNodesInMemory();

            System.out.printf("%-10d %-15d %-15d %-20d%n", iterations, solutionDepth, totalNodes, maxNodesInMemory);
        }
    }
}
