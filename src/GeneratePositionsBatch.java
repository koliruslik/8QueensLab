import java.util.ArrayList;
import java.util.List;

public class GeneratePositionsBatch {
    public static List<Bitboard> generatePositions(int count) {
        List<Bitboard> positions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            positions.add(GeneratePosition.generatePosition());
        }
        return positions;
    }
}