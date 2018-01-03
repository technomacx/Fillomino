/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ahmad
 */
public class Node {


    public int value = 0;
    public boolean visited = false, changeAble = true;
    int x = 0;
    int y = 0;

    public Node(int val, boolean visit, int x, int y) {
        value = val;
        visited = visit;
        this.x = x;
        this.y = y;
    }
}
