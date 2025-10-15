import java.util.ArrayList;

public class CaveExplorer {
  public static final char[][] CAVE = {
    "####################".toCharArray(),
    "#       #      #  T#".toCharArray(),
    "# ####### ##  ### ##".toCharArray(),
    "#       #          #".toCharArray(),
    "####################".toCharArray()
  };

    public static void main(String[] args) {
        char[][] caveLayout = copyLayout(CAVE);
        exploreCave(caveLayout, new Location(1, 1));
    }

    public static char[][] copyLayout(char[][] layout) {
        //TODO: Implement a method to copy over the original layout
        char[][] copied = new char[layout.length][];
        for(int i = 0; i < layout.length; i++){
            copied[i] = new char[layout[i].length];
            for(int j = 0; j < layout[i].length; j++){
                copied[i][j] = layout[i][j];
            }
        }
        return copied;
    }

    public static void displayCave(char[][] layout) {
        //TODO: Implement a method to print the 2D array representing the cave
        for(int i = 0; i < layout.length; i++){
            for(int j = 0; j < layout[i].length; j++){
                System.out.print(layout[i][j]);
            }
            System.out.println();
        }
    }

    public static void exploreCave(char[][] layout, Location start) {
        if (findTreasure(layout, start)) {
            System.out.println("Treasure found!");
        } else {
            System.out.println("No path to the treasure.");
        }
        displayCave(layout);
    }

    public static boolean findTreasure(char[][] layout, Location start) {
        PathStack stack = new PathStackLL();
        PathStack path = new PathStackLL(); 

        stack.push(start);

        while (!stack.isEmpty()) {
            Location current = stack.pop();
            //TODO: Push the current location to path 

            if (layout[current.row()][current.col()] == 'T') {
                // TODO: Mark the correct path with '.'
                path.push(current);
                // mark the path
                while(!path.isEmpty()){
                    Location step = path.pop();
                    if(layout[step.row()][step.col()] == 'X'){
                        layout[step.row()][step.col()] = '.';
                    }
                }
                return true;
            }

            if (layout[current.row()][current.col()] == ' ') {
                //TODO: Mark the layout with the appropriate char 
                layout[current.row()][current.col()] = 'X';
                path.push(current);

                int row = current.row();
                int col = current.col();

                boolean moved = false;
                if(layout[row+1][col] == ' ' || layout[row+1][col] == 'T'){
                    stack.push(new Location(row+1, col));
                    moved = true;
                }
                if(layout[row - 1][col] == ' ' || layout[row - 1][col] == 'T'){
                    stack.push(new Location(row - 1, col));
                    moved = true;
                }
                if(layout[row][col + 1] == ' ' || layout[row][col+1] == 'T'){
                    stack.push(new Location(row, col+1));
                    moved = true;
                }
                if(layout[row][col - 1] == ' ' || layout[row][col - 1] == 'T'){
                    stack.push(new Location(row, col - 1));
                        moved = true;
                }
                //backtrack if met deadend
                if(!moved){
                    layout[row][col] = ' ';
                    path.pop();
                }

            }

            // TODO: Implement movement logic - hint look at the four directions 
            //       that the explorer can move to.
        }
        return false;
    }
}

// Interface for stack operations
interface PathStack {
    void push(Location loc);
    Location pop();
    boolean isEmpty();
}

// TODO: Implement PathStackLL using a linked list that implements PathStack interface
class PathStackLL implements PathStack{
    private Node top;

    private class Node{
        Location data;
        Node next;

        public Node(Location data){
            this.data = data;
            this.next = null;
        }
    }
    public void push(Location lo){ //add to front
        Node newNode = new Node(lo);
        newNode.next = top;
        top = newNode;
    }
    public Location pop(){//remove and get the top node
        if(isEmpty()){
            return null;
        }
        Location location = top.data;
        top = top.next;
        return location;
    }
    public boolean isEmpty(){
        return top == null;
    }
}
class Location {
    private int row, col;
    public Location(int r, int c) { 
        this.row = r; 
        this.col = c; 
    }
    public int row() { 
        return row; 
    }
    public int col() { 
        return col; 
    }
}