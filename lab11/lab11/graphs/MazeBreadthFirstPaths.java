package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /*
     * Inherits public fields:
     * public int[] distTo;
     * public int[] edgeTo;
     * public boolean[] marked;
     */
    private int s; // start point
    private int t; // target point
    private Queue<Integer> fringe;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        fringe = new LinkedList<>();
        fringe.add(s);
        marked[s] = true;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        while (!fringe.isEmpty()) {
            int v = fringe.poll();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    announce();
                    if (w == t) {
                        return;
                    } else {
                        fringe.add(w);
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}
