package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import org.junit.Test;

public class PercolationStats {
    private int numtrials;
    private double[] fractions;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IndexOutOfBoundsException();
        }
        numtrials = T;
        int totalSites = N * N;
        for (int i = 0; i < T; i++) {
            int numOpenedSites = 0;
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    numOpenedSites++;
                }
            }
            fractions[i] = numOpenedSites / totalSites;
        }

    } // perform T independent experiments on an N-by-N grid

    public double mean() {
        return StdStats.mean(fractions);
    } // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(fractions);
    } // sample standard deviation of percolation threshold

    public double confidenceLow() {
        double me = mean();
        double sigma = stddev();
        return me - 1.96 * sigma / Math.sqrt(numtrials);
    } // low endpoint of 95% confidence interval

    public double confidenceHigh() {
        double me = mean();
        double sigma = stddev();
        return me - 1.96 * sigma / Math.sqrt(numtrials);
    } // high endpoint of 95% confidence interval

}
