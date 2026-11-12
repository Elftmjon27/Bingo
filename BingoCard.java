public class BingoCard {
    // stores the card object in a bingo game

    private int[][] nums;
    private boolean[][] tagged;
    private int currentRow = 0;
    private int increment; // increments between columns
    private int dimnesions; // assumes only square cards
    final String middle = " 0";
    private String card = "\n";


    public Card (int d, int inc) {
        dimensions = d;
        increment = inc;
        nums = new int[dimensions][dimensions];
        tagged = new boolean[dimensions][dimensions];

        card += " B    I    N    G    O\n";
        for (int i = 0; i < dimensions; i++) {
            card += rowLine();
        }
        card += "+----+----+----+----+----+\n";
        card += "col:  0    1    2    3    4";
    }

    private String rowLine() {
        String output = "";
        output += "+----+----+----+----+----+\n";
        for (int i = 0; i < dimensions; i++) {
            output += "| ";
            output += rowNumber(i);
            output += " ";
        }
        output += "|  row " + currentRow + "\n";
        currentRow++;
        return output;
    }

    private String rowNumber(int c) {
        String output = "";
        if (c == 2 && currentRow == 2) {

            output += middle;
            return output; // if in the middle, always sets to middle term
        }

        int n = (int) (Math.random()*increment + 1 + c*increment); // setting random number between
        while (avoidDup(c, n)) {
            n = (int) (Math.random()*increment + 1 + c*increment);
        }
        output += "" + n;

        if (n / 10 == 0) {
            output += " "; // adding extra space if number is one digit
        }

        nums[currentRow][c] = n;
        tagged[currentRow][c] = false; // only used in setup, so all tagged is false
        return output;
    }

    private boolean avoidDup(int col, int n) {
        for (int i = 0; i < currentRow; i++) {
            if (n == nums[col][i]) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return card;
    }

    public void printNums() {
        System.out.println("");
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j ++) {
                System.out.print(nums[i][j] + " ");
            }
            if (i != dimensions - 1) {
                System.out.println("\n");
            }
        }
    }
}