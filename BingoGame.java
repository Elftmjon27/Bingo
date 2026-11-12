public class BingoGame {
    final int dimensions = 5;
    final int increment = 15;

    public BingoGame () {
        Card c = new BingoCard(dimensions, increment);
        System.out.println(c.toString()); // prints out the bingo card
        // c.printNums();
    }
}