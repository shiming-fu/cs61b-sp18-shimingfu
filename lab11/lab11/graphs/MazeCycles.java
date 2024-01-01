package lab11.graphs;

import java.util.Random;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /*
     * Inherits public fields:
     * public int[] distTo;
     * public int[] edgeTo;
     * public boolean[] marked;
     */

    public MazeCycles(Maze m) {
        super(m);
    }

    private int[] camefrom;
    private boolean foundcircle = false;

    private void dfs(int v) {
        for (int w : maze.adj(v)) {
            if (foundcircle) {
                return;
            }
            if (!marked[w]) {
                marked[w] = true;
                camefrom[w] = v;
                dfs(w);
            } else if (v != camefrom[w]) {
                camefrom[w] = v;
                int cur = v;
                while (cur != w) {
                    cur = camefrom[cur];
                    edgeTo[cur] = camefrom[cur];
                }
                foundcircle = true;
                return;
            }

        }
    }

    @Override
    public void solve() {
        camefrom = new int[maze.V()];
        /* Set point where circle search starts */
        Random rand = new Random();
        int startX = rand.nextInt(maze.N());
        int startY = rand.nextInt(maze.N());
        int s = maze.xyTo1D(startX, startY);
        marked[s] = true;
        camefrom[s] = s;

        dfs(s);
        announce(); // Render the results of DFS
    }

    // Helper methods go here
}
