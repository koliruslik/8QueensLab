import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bitboard queens = GeneratePosition.generatePosition();
        System.out.println("=== Start position ===");
        queens.print();

        BestFirst bestFirst = new BestFirst();
        RBFS rbfsSolver = new RBFS();

        System.out.println("Starting Recursive Best First Search...");
        rbfsSolver.search(queens, false);

        System.out.println();
        queens.print();
        System.out.println("Starting Best First Search...");
        bestFirst.search(queens, true);
    }


}
