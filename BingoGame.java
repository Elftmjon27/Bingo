import java.util.Scanner;
public class BingoGame {
    final int dimensions = 5;
    final int increment = 15;
    BingoCard card;

    public BingoGame () {
        card = new BingoCard(dimensions, increment);
        System.out.println(card.toString() + "\n"); // prints out the bingo card
        options(new Scanner(System.in));
    }

    private void options(Scanner sc) {
        System.out.println("What do you want to do?");
        System.out.println("You can call number, mark a number, view card, or shout bingo\n");
        
        int input = getChoice(sc);

        if (input == 0) { // call number
            card.callNum();
            System.out.println("\n" + card.getCalledNums() + "\n");
        } else if (input == 1) { // marks number if it has been called or unmarked
            int r = getUserInt(sc, "Enter row: ");
            int c = getUserInt(sc, "Enter column: ");
            int n = card.getNum(r, c);

            if (card.checkNumCalled(n)) {
                if (card.tagSpace(r, c)) {
                    System.out.println("\nYou have marked " + n + "\n");
                } else {
                    System.out.println(n + "has already been marked\n");
                }
            } else {
                System.out.println(n + " hasn't been called\n");
            }
        } else if (input == 2) { // view card
            System.out.println(card.toString() + "\n");
        } else { // shout bingo
            if (card.checkWin()) {
                System.out.println("Congratulations! You have won");
                return;
            } else {
                System.out.println("You haven't won yet\n");
            }
        }
        options(sc);
    }

    private int getChoice(Scanner sc) {
        String input = sc.nextLine().toLowerCase();

        while (!input.contains("call") && !input.contains("mark") && !input.contains("bingo") && !input.contains("card") && !input.contains("view")) {
            input = sc.nextLine().toLowerCase();
        }
        
        if (input.contains("call")) {
            return 0;
        } else if (input.contains("mark")) {
            return 1;
        } else if (input.contains("bingo")) {
            return 3; // bingo sholud be last action, but easier to do one .contains rather than maximum two .contains
        } else {
            return 2;
        }
    }

    private int getUserInt(Scanner sc, String text) {
        int n;
        while (true) {
            System.out.print(text);
            try {
                n = Integer.parseInt(sc.nextLine());

                if (n >= 0 && n <= 4) {
                    return n;
                }
            }
            catch (NumberFormatException e) {}
        }
    } 
}