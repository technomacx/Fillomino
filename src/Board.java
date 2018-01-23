package fillomino;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Board {

    static Scanner in = new Scanner(System.in);
    public Node[][] cells;
    public int width, height;

    public Board() {

    }

    public Board(int w, int h) {
        width = w;
        height = h;
        cells = new Node[height][width];
        int j = 0;
        for (Node[] row : cells) {
            int i = 0;
            for (Node element : row) {
                cells[j][i] = new Node(0, false, i, j);
                i++;
            }
            j++;
        }
    }

    public Board(Board p) {
        height = p.height;
        width = p.width;
        cells = new Node[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                cells[j][i] = new Node(p.cells[j][i].value, p.cells[j][i].visited, i, j);
            }
        }
    }

    public void enterBoard() {
        int col, row, val;
        while (true) {
            System.out.print("enter row number");
            row = in.nextInt();
            if (row < 0) {
                break;
            }
            System.out.print("enter col number");
            col = in.nextInt();
            System.out.print("enter value");
            val = in.nextInt();
            cells[row][col].value = val;
            cells[row][col].changeAble = false;
        }
    }

    public void printBoard() {
        System.out.println("-----------------------------");
        for (Node[] row : cells) {
            for (Node element : row) {
                System.out.print(element.value + "  ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }

    public boolean checkSolve() {
        int i = 0, j = 0;
        for (int q = 0; q < height; q++) {
            for (int w = 0; w < width; w++) {
                cells[q][w].visited = false;
            }
        }
        for (Node[] row : this.cells) {
            i = 0;
            for (Node element : row) {
                if (this.cells[j][i].visited == false) {
                    if (checkCell(j, i) != this.cells[j][i].value) {
                        return false;
                    }
                }
                i++;
            }
            j++;
        }
        return true;
    }

    public int CalcToCompleteZone() {
        int i = 0, j = 0;
        int numCells = 0;
        boolean found = false;
        for (Node[] row : this.cells) {
            i = 0;
            for (Node element : row) {
                for (int q = 0; q < height; q++) {
                    for (int w = 0; w < width; w++) {
                        cells[q][w].visited = false;
                    }
                }
                if (cells[j][i].value != 0) {
                    int Z = cells[j][i].value - checkCell(j, i);
                    if (Z > 0) {
                        if (found) {
                            numCells = Math.min(Z, numCells);
                        } else {
                            numCells = Z;
                            found = true;
                        }
                    }
                }
                i++;
            }
            j++;
        }
        return numCells;
    }

    public int CalceFalseAndEmptyCells() {
        int i = 0, j = 0;
        int numCells = 0;

        for (Node[] row : this.cells) {
            i = 0;
            for (Node element : row) {
                for (int q = 0; q < height; q++) {
                    for (int w = 0; w < width; w++) {
                        cells[q][w].visited = false;
                    }
                }
                if (cells[j][i].value == 0 || (cells[j][i].changeAble == true && checkCell(j, i) != cells[j][i].value)) {
                    numCells++;
                }
                i++;
            }
            j++;
        }
        return numCells;
    }

    public int checkCell(int row, int col) {

        int num = 1;
        cells[row][col].visited = true;
        if (row > 0 && cells[row][col].value == cells[row - 1][col].value && cells[row - 1][col].visited == false)//check top
        {
            num += checkCell(row - 1, col);
        }

        if (row < height - 1 && cells[row][col].value == cells[row + 1][col].value && cells[row + 1][col].visited == false)//check down
        {
            num += checkCell(row + 1, col);
        }

        if (col < width - 1 && cells[row][col].value == cells[row][col + 1].value && cells[row][col + 1].visited == false)//check right
        {
            num += checkCell(row, col + 1);
        }

        if (col > 0 && cells[row][col].value == cells[row][col - 1].value && cells[row][col - 1].visited == false)//check left
        {
            num += checkCell(row, col - 1);
        }
        return num;
    }

    public List<Board> getPossibleNextStep() {
        List list = new LinkedList();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (isPossibleCell(j, i)) {
                    for (Integer q : getNeighbors(j, i)) {
                        Board b = new Board(this);
                        b.cells[i][j].value = q;
                        //System.out.println(q);
                        // b.cells[i][j].changeAble = false;
                        //b.printBoard();
                        list.add(b);
                    }
                }
            }
        }
        return list;
    }

    public boolean isPossibleCell(int x, int y) {
        if (cells[y][x].value != 0) {
            return false;
        }
        if (x > 0 && cells[y][x - 1].value != 0) {
            return true;
        }
        if (y > 0 && cells[y - 1][x].value != 0) {
            return true;
        }
        if (x < width - 1 && cells[y][x + 1].value != 0) {
            return true;
        }
        if (y < height - 1 && cells[y + 1][x].value != 0) {
            return true;
        }
        return false;
    }

    public List<Integer> getNeighbors(int x, int y) {
        List<Integer> n = new LinkedList<>();
        if (x > 0 && cells[y][x - 1].value != 0 && !n.contains(cells[y][x - 1].value)) {
            n.add(cells[y][x - 1].value);
        }
        if (y > 0 && cells[y - 1][x].value != 0 && !n.contains(cells[y - 1][x].value)) {
            n.add(cells[y - 1][x].value);
        }
        if (x < width - 1 && cells[y][x + 1].value != 0 && !n.contains(cells[y][x + 1].value)) {
            n.add(cells[y][x + 1].value);
        }
        if (y < height - 1 && cells[y + 1][x].value != 0 && !n.contains(cells[y + 1][x].value)) {
            n.add(cells[y + 1][x].value);
        }
        return n;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Arrays.deepHashCode(this.cells);
        hash = 31 * hash + this.width;
        hash = 31 * hash + this.height;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Board other = (Board) obj;
        if (!Arrays.deepEquals(this.cells, other.cells)) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        return true;
    }

}
