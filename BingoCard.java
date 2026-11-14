public class BingoCard {
    // stores the card object in a bingo game

    private int[][] nums; // stores numbers on card
    private boolean[][] tagged; // stores whether numbers have been tagged

    private int currentRow = 0;
    private String card = "\n"; // string version of the card

    private int increment; // increments between columns
    private int dimensions; // assumes only square cards
    final String middle = " 0"; // String for free space, code designed for it to be 2-digits long
    
    final String[] winPatterns = {"Horizontal", "Vertical", "Four-Corners", "X", "Blackout"} // win patterns
    private int patternIndex;

    private int[] usedNums = new int[75]; // stores numbers called, 75 is max size
    private int usedNumsAmount = 0; // number of numbers called
    private String calledNums = "";

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

    private boolean avoidDup(int n) {
        for (int i = 0; i < usedNumsAmount; i++) {
            if (n == usedNums[i]) {
                return true;
            }
        }
        return false;
    }

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

    // defines and returns the win pattern
    public String generateWinPattern() {
        patternIndex = (int) (Math.random()*winPatterns.length());
        return winPatterns[patternIndex];
    }

    // calls a number
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

    // returns whether number n has been called
    public boolean checkNumCalled(int n) {
        for (int i = 0; i < usedNumsAmount) {
            if (n == usedNums[i]) {
                return true;
            }
        }
        return false;
    }

    public String getCalledNums() {
        return calledNums;
    }

    public boolean getTag(int r, int c) {
        return tagged[r][c];
    }
    
    // records space at (r,c) has been tagged
    public void updateTag(int r; int c) {
        tagged[r][c] = true;
    }

    public int getNum(int r, int c) {
        return nums[r][c];
    } 

    public boolean checkWin() {
        // {"Horizontal", "Vertical", "Four-Corners", "X", "Blackout"}
        Stirng win = winPatterns[patternIndex];

        if (win.equals("Horizontal")) {
            return checkHorzontal();
        } else if (wins.equals("Vertical")) {
            return checkVertical();
        } else if (wins.equals("Four-Corners")) {
            return checkCorners();
        } else if (wins.equals("X")) {
            return checkX();
        } else { // here it's blackout
            return checkBlackout();
        }
    }

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

    private boolean checkVertical() {
        booleanl temp;
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

    private boolean checkCorners() {
        return tagged[0][0] && tagged[dimensions - 1][0] && tagged[0][dimensions - 1] && tagged[dimensions - 1]dimensions - 1];
    }

    private boolean checkX() {
        boolean temp = true;
        for (int i = 0; i < dimensions && temp; i++) {
            temp = tagged[i][0 + i] && tagged[i][dimensions - 1 - i];
            // checks cells and goes in by 1 each row
        }
        return temp;
    }

    private boolean checkBlackout() {
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (!tagged[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}