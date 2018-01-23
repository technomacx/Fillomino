package fillomino;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Fillomino {

    static Scanner in;
    static int z = 0;
    static Set<Board> DFSChecked = new HashSet<>();
    static Set<Board> BFSchecked = new HashSet<>();
    static Queue<Board> BFSqueue = new LinkedList<>();

    public static void main(String[] args) {
        in = new Scanner(System.in);
        System.out.print("width=");
        int width = in.nextInt();
        System.out.print("height=");
        int height = in.nextInt();
        Board b = new Board(width, height);
        b.enterBoard();
        b.printBoard();
        //DFS(b);
        //BFS(b);
        A_Star(b);
    }

    public static boolean DFS(Board b) {
        DFSChecked.add(b);
        //  b.printBoard();
        if (b.checkSolve()) {
            System.out.println("*****************the solve is :*************");
            b.printBoard();
            return true;
        } else {
            for (Board nextStep : b.getPossibleNextStep()) {
                if (!DFSChecked.contains(nextStep)) {
                    if (DFS(nextStep)) {
                        return true;
                    }
                }
            }
           System.out.println(z++);
            return false;
        }

    }

    public static boolean BFS(Board B) {

        BFSqueue.offer(B);
        while (!BFSqueue.isEmpty()) {
            System.out.println(z++);
            Board b = BFSqueue.poll();
            if (BFSchecked.contains(b)) {
                continue;
            }
            BFSchecked.add(b);

            if (b.checkSolve()) {
                System.out.println("*****************the solve is :*************");
                b.printBoard();
                return true;
            } else {
                //b.printBoard();
                for (Board nextStep : b.getPossibleNextStep()) {
                    BFSqueue.offer(nextStep);
                }
            }
        }
        System.out.println("fail");
        return false;
    }

    public static void A_Star(Board b) {
        PriorityQueue<Board> queue = new PriorityQueue<>((Board i, Board j) -> {
            if (i.CalceFalseAndEmptyCells() + i.CalcToCompleteZone() > j.CalceFalseAndEmptyCells() + j.CalcToCompleteZone()) {
                return 1;
            } else if (i.CalceFalseAndEmptyCells() + i.CalcToCompleteZone() < j.CalceFalseAndEmptyCells() + j.CalcToCompleteZone()) {
                return -1;
            } else {
                return 0;
            }
        });

        Set<Board> explored = new HashSet<>();

        queue.add(b);
        do {
            System.out.println(z++);
            Board current = queue.poll();
            explored.add(current);

            if (current.checkSolve()) {
                System.out.println("*****************the solve is :*************");
                current.printBoard();
                return;
            }
            for (Board child : current.getPossibleNextStep()) {
                if (!queue.contains(child) && !explored.contains(child)) {
                    queue.add(child);
                }
            }
        } while (!queue.isEmpty());
        System.out.println("there is not any solve");
    }

}
