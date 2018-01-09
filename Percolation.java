
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int row_i, col_j;
	private WeightedQuickUnionUF UF;
	private WeightedQuickUnionUF UF_TOP;
	private boolean [] matrix;
	private int virtual_top, virtual_bottom;
	private int num;
	// create n-by-n grid, with all sites blocked
	
	public Percolation(int n){
		//create n * n + 2 UF
		UF = new WeightedQuickUnionUF(n * n + 2);
		// no bottom virtual point 
		UF_TOP = new WeightedQuickUnionUF(n * n + 1);
		row_i = n;
		col_j = n;
		// last 2 point is virtual_top and virtual_bottom
		virtual_top = n * n;
		virtual_bottom = n * n + 1;
		matrix = new boolean[n * n];
		num = 0;
	}
	
	private void valid(int row, int col){
		if (row <= 0 || row > row_i) throw new IndexOutOfBoundsException("row index out of bound");
		
		if (col <= 0 || col > col_j) throw new IndexOutOfBoundsException("col index out of bound");
	}
	
	// open site (row, col) if it is not open already
	public void open(int row, int col){
		valid(row, col);
		int index = (row - 1) * row_i + col - 1;
		if (!isOpen(row, col)){
			
			matrix[index] = true;
			num +=1;
			// connect with top virtual point
			if (row == 1){
				UF.union(index, virtual_top);
				UF_TOP.union(index, virtual_top);	
			}
			// connect with bottom virtual point
			if (row == row_i){
				UF.union(index, virtual_bottom);
			}
			
			// check the surround point isOpen, if true, union
			int surround;
			// union with up
			if (row > 1){
				surround = index - 1 * row_i;
				if (isOpen(row - 1, col)){
					UF.union(index, surround);
					UF_TOP.union(index, surround);
				}
			}
			// union with down
			if (row < row_i){
				surround = index + 1 * row_i;
				if (isOpen(row + 1, col)){
					UF.union(index, surround);
					UF_TOP.union(index, surround);	
				}
			}
			// union with left
			if (col > 1){
				surround = index - 1;
				if (isOpen(row, col - 1)){
					UF.union(index, surround);
					UF_TOP.union(index, surround);
				}
			}
			// union with right
			if (col < col_j){
				surround = index + 1;
				if (isOpen(row, col + 1)){
					UF.union(index, surround);
					UF_TOP.union(index, surround);
				}
			}
		}
		
	}
	public boolean isOpen(int row, int col){  // is site (row, col) open?
		valid(row, col);
		return matrix[(row-1) * row_i + col - 1];
	}
	
	public boolean isFull(int row, int col){  // is site (row, col) full?
		valid(row, col);
		// avoid BackWash, 
		return UF_TOP.connected((row-1) * row_i + col - 1, virtual_top);
	}
	public   int numberOfOpenSites() {     // number of open sites
		
		return num;
	}
	public boolean percolates() {            // does the system percolate?
		
		return UF.connected(virtual_top, virtual_bottom);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Percolation perc = new Percolation(2);
        perc.open(1, 1);
        perc.open(1, 2);
        perc.open(2, 1);
        System.out.println(perc.percolates());
	}

}
