package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int size;
    private int bottom;
    private int top;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufExcludeBottom;
    private int numOfOpenSites = 0;
    private int[][] surroundings = new int[][] {{0, 1 }, {0, -1 }, {1, 0 }, {-1, 0 } };

    private int xytoID(int row, int col) {
        return row * size + col + 1;
    }

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        grid = new boolean[N][N];
        size = N;
        top = 0;
        bottom = N * N + 1;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufExcludeBottom = new WeightedQuickUnionUF(N * N + 1);
    } // create N-by-N grid, with all sites initially blocked

    private void validate(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numOfOpenSites += 1;
        }
        if (row == 0) {
            uf.union(xytoID(row, col), top);
            ufExcludeBottom.union(xytoID(row, col), top);
        }
        if (row == size - 1) {
            uf.union(xytoID(row, col), bottom);
        }
        for (int[] surrounding : surroundings) {
            int adjacentrow = row + surrounding[0];
            int adjacentcol = col + surrounding[1];
            if (adjacentrow > 0 && adjacentrow < size) {
                if (adjacentcol > 0 && adjacentcol < size) {
                    uf.union(xytoID(row, col), xytoID(adjacentrow, adjacentcol));
                    ufExcludeBottom.union(xytoID(row, col), xytoID(adjacentrow, adjacentcol));
                }
            }
        }
    } // open the site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    } // is the site (row, col) open?

    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufExcludeBottom.connected(xytoID(row, col),top);
    } // is the site (row, col) full?

    public int numberOfOpenSites() {
        return numOfOpenSites;
    } // number of open sites

    public boolean percolates() {
        return uf.connected(top, bottom);
    } // does the system percolate?

    public static void main(String[] args) {

    }
}
