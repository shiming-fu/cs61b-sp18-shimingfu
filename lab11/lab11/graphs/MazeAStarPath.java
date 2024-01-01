package lab11.graphs;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    private class Node {
        private int v;

        public Node(int v) {
            this.v = v;
        }
    }

    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return (distTo[o1.v] + h(o1.v)) - (distTo[o2.v] + h(o2.v));
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        pq.add(new Node(s));
        marked[s] = true;
        while (!pq.isEmpty()) {
            Node temp = pq.poll();
            for (int w : maze.adj(temp.v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = temp.v;
                    distTo[w] = distTo[temp.v] + 1;
                    announce();
                    if (w == t) {
                        return;
                    } else {
                        pq.add(new Node(w));
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}