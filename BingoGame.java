import java.util.Scanner;
public class BingoGame {
    final int dimensions = 5;
    final int increment = 15;

    public BingoGame () {
        BingoCard c = new BingoCard(dimensions, increment);
        System.out.println(c.toString()); // prints out the bingo card
        // c.printNums();

        options(new Scanner (System.in));
    }

    private void options(Scanner sc) {
        System.out.println("What do you want to do?");
        System.out.println("You can call number, mark a number, or shout bingo\n");
        
        int input = getChoice(sc);

        if (input == 0) { // call number
            c.callNum();
            System.out.println("\n" + c.getCalledNums() + "\n");
        } else if (input == 1) { // marks number if it has been called or unmarked
            int r = getUserInt("Enter row: ");
            int c = getUserInt("Enter column: ");
            int n = c.getNum(r, c);

            if (c.checkNumCalled(n)) {
                if (c.getTag(r,c)) {
                    System.out.println(n + "has already been marked\n");
                } else {
                    System.out.println("You have marked " + n + "\n");
                }
            } else {
                System.out.println(n + " hasn't been called\n")
            }
        } else {

        }
        options();
    }

    private int getChoice(Scanner sc) {
        String input = sc.nextLine().toLowerCase();

        while (!input.contains("call") && !input.contains("mark") && !input.contains("bingo")) {
            input = sc.nextLine().toLowerCase();
        }
        
        if (input.contains("call")) {
            return 0;
        } else if (input.contains("mark")) {
            return 1;
        } else {
            return 2;
        }
    }

    private int getUserInt(String text) {
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