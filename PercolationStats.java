import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
	private int exptimes;
	private double[] thresholds;
	private double mean, stddev;
	
	public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
	{
		if (n <=0 || trials <=0) throw new IllegalArgumentException("Illeagal Argument");
		exptimes = trials;
		thresholds = new double[exptimes];
		for (int i = 0 ; i < trials; i++){
			int opened = 0;
			Percolation perc = new Percolation(n);
			while (!perc.percolates()){
				int x = StdRandom.uniform(n) + 1;
				int y = StdRandom.uniform(n) + 1;
				if (!perc.isOpen(x, y)){
					perc.open(x, y);
					opened++;
				}
			}
			thresholds[i] = (double) opened / (n * n);
		}
		mean = StdStats.mean(thresholds);
		stddev = StdStats.stddev(thresholds);
	}
	public double mean()                          // sample mean of percolation threshold
	{
		return mean;
	}
	public double stddev()                        // sample standard deviation of percolation threshold
	{
		return stddev;
	}
	public double confidenceLo()                  // low  endpoint of 95% confidence interval
	{
		double diff = (1.96 * stddev) / Math.sqrt(exptimes);
		return mean - diff;
	}
	public double confidenceHi()                  // high endpoint of 95% confidence interval
	{
		double diff = (1.96 * stddev) / Math.sqrt(exptimes);
		return mean + diff;
	}
	public static void main(String[] args) {
        int length = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolations = new PercolationStats(length, trials);
        StdOut.println("mean                    = " + percolations.mean());
        StdOut.println("stddev                  = " + percolations.stddev());
        StdOut.println("95% confidence interval = "
                           + percolations.confidenceLo() + ", "
                           + percolations.confidenceHi());
	

	}

}
