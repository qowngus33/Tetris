package blocks;

import java.awt.*;

public abstract class Block {
		
	protected int[][] shape;
	protected Color color;
	public int typeNum;

	public Block() {
		shape = new int[][]{ 
				{1, 1}, 
				{1, 1}
		};
		color = Color.YELLOW;
	}
	
	public int getShape(int x, int y) {
		return shape[y][x];
	}
	
	public Color getColor() {
		return color;
	}
	
	public void rotate() {
		//Rotate the block 90 deg. clockwise
		int m = shape.length;
		int n = shape[0].length;
		int[][] rotated = new int[n][m];
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				rotated[j][m-i-1] = shape[i][j];
			}
		}
		
		this.shape = new int[n][m];
		shape = rotated;
	}
	
	public int height() {
		return shape.length;
	}
	
	public int width() {
		if(shape.length > 0)
			return shape[0].length;
		return 0;
	}
}
