public class BingoCard {
    // stores the card object in a bingo game

    private int[][] nums; // stores numbers on card
    private boolean[][] tagged; // stores whether numbers have been tagged

    private int currentRow = 0;
    private String card = "\n"; // string version of the card

    private int increment; // increments between columns
    private int dimensions; // assumes only square cards
    final String middle = " 0"; // String for free space, code designed for it to be 2-digits long

    private int[] usedNums = new int[75]; // stores numbers called, 75 is max size
    private int usedNumsAmount = 0; // number of numbers called
    private String calledNums = "";

    // used to color numbers, show that numbers have been tagged on card
    final String reset = "\u001B[0m"; // resets color scheme
    final String highlight = "\u001B[42;30m"; // color scheme = black text w/ green highlight

    // creates bingo card of dimensions d x d with increments of 1 - inc w/ + inc each column
    public BingoCard (int d, int inc) {
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

    // creating board row by row
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

    // chooses distinct number to be in cell based mostly off column (only uses row to check if middle)
    private String rowNumber(int c) {
        String output = "";
        if (c == 2 && currentRow == 2) {

            output += middle;
            return output; // if in the middle, always sets to middle term
        }

        int n = (int) (Math.random()*increment + 1 + c*increment); // setting random number between
        while (avoidDup(c, n)) { // ensures all numbers distinct
            n = (int) (Math.random()*increment + 1 + c*increment);
        }
        output += "" + n;

        if (n / 10 == 0) {
            output += " "; // adding extra space if number is one digit
        }

        nums[currentRow][c] = n;
        tagged[currentRow][c] = false; // this is only used in setup, so all tagged is false
        return output;
    }

    // checks whether a number is already on the card
    private boolean avoidDup(int col, int n) {
        for (int i = 0; i < currentRow; i++) {
            if (n == nums[i][col]) {
                return true;
            }
        }
        return false;
    }

    // checks whether a number has already been called
    private boolean avoidDup(int n) {
        for (int i = 0; i < usedNumsAmount; i++) {
            if (n == usedNums[i]) {
                return true;
            }
        }
        return false;
    }

    // returns the string version of the card
    public String toString() {
        return card;
    }

    // prints the numbers on the bingo card without card design
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

    // calls and returns a distinct number
    public int callNum() {
        int n = (int) (Math.random()*74 + 1); // 1 - 75 inclusively

        while (avoidDup(n)) {
            n = (int) (Math.random()*74 + 1); // 1 - 75 inclusively
        }

        usedNums[usedNumsAmount] = n; // records n in list of used numbers
        if (usedNumsAmount != 0) { // adds comma except for first number
            calledNums += ", ";
        }
        calledNums += n;
        usedNumsAmount++; // increases known size

        return n;
    }

    // returns the number in (r,c) on the card
    public int getNum(int r, int c) {
        return nums[r][c];
    } 

    // returns whether number n has been called
    public boolean checkNumCalled(int n) {
        for (int i = 0; i < usedNumsAmount; i++) {
            if (n == usedNums[i]) {
                return true;
            }
        }
        return false;
    }

    // returns the string of called numbers
    public String getCalledNums() {
        return calledNums;
    }

    // returns whether space (r,c) has been tagged or not
    public boolean getTag(int r, int c) {
        return tagged[r][c];
    }
    
    // tags a space, returns true if has tagged and false if already tagged
    public boolean tagSpace(int r, int c) {
        if (tagged[r][c]) {
            return false;
        }

        tagged[r][c] = true;
        int n = getNum(r,c);
        int digitLength = 2;
        int index = card.indexOf(" " + n + " ") + 1;


        if (n / 10 == 0) { // if one digit
            digitLength--;
        }

        card = card.substring(0,index) + highlight + card.substring(index, index + digitLength) + reset + card.substring(index + digitLength);
        System.out.println(card);
        return true;
    }

    // checks if the player has won
    public boolean checkWin() {
        return checkHorizontal() || checkVertical() || checkDiagonals();
    }

    // checks if player has complete row
    private boolean checkHorizontal() {
        boolean temp;
        for (int i = 0; i < dimensions; i++) {
            temp = true;
            for (int j = 0; j < dimensions && temp; j++) {
                if (!tagged[i][j]) {
                    temp = false;
                }
            }

            if (temp) {
                return true;
            }
        }
        return false;
    }

    // checks if player has complete column
    private boolean checkVertical() {
        boolean temp;
        for (int i = 0; i < dimensions; i++) {
            temp = true;
            for (int j = 0; j < dimensions && temp; j++) {
                if (!tagged[j][i]) {
                    temp = false;
                }
            }

            if (temp) {
                return true;
            }
        }
        return false;
    }

    // checks if player has complete diagonal
    private boolean checkDiagonals() {
        boolean temp1 = true; // left down diagonal
        boolean temp2 = true; // right up diagonal
        for (int i = 0; i < dimensions && (temp1 || temp2); i++) {
            if (temp1) {
                temp1 = tagged[i][0 + i];
            }
            if (temp2) {
                temp2 = tagged[i][dimensions - 1 - i];
            }
        }
        return temp1 || temp2;
    }
}