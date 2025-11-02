import java.util.Random;
public class GeneratePosition {
    public static Bitboard generatePosition() {
        Random rand = new Random();
        Bitboard bb = new Bitboard();
        int count = 0;
        while(count < 8) {
            int pos = rand.nextInt(64);
            if(!bb.getBit(pos)){
                bb = bb.set1(pos);
                count++;
            }
        }
        return bb;
    }
}