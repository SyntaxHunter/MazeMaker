import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//select a random cell to start
		Cell startCell = maze.getCell((int) (randGen.nextDouble() * height), (int) (randGen.nextDouble() * width));
		
		//call selectNextPath method with the randomly selected cell
		selectNextPath(startCell);
		
		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		// mark current cell as visited
		currentCell.setBeenVisited(true);
		
		// check for unvisited neighbors
		ArrayList<Cell> unvisitedNeighbors = getUnvisitedNeighbors(currentCell);
		
		// if has unvisited neighbors,
		if(unvisitedNeighbors.size() != 0){
			
			// select one at random.
			Cell randCell = unvisitedNeighbors.get((int) (randGen.nextDouble() * unvisitedNeighbors.size()));
			
			// push it to the stack
			uncheckedCells.push(randCell);
			
			// remove the wall between the two cells
			removeWalls(currentCell, randCell);
			
			// make the new cell the current cell and mark it as visited
			currentCell = randCell;
			randCell.setBeenVisited(true);
			
			//call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
		
		// if all neighbors are visited
		if(unvisitedNeighbors.size() == 0){
			
			//if the stack is not empty
			if(!uncheckedCells.empty()){
				
				// pop a cell from the stack
				// make that the current cell
				currentCell = uncheckedCells.pop();
				
				//call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	private static void removeWalls(Cell c1, Cell c2) {
		if(c1.getX() == c2.getX() && c1.getY() > c2.getY()){
			c1.setNorthWall(false);
			c2.setSouthWall(false);
		} else if (c1.getX() == c2.getX() && c1.getY() < c2.getY()) {
			c1.setSouthWall(false);
			c2.setNorthWall(false);
		} else if (c1.getY() == c2.getY() && c1.getX() > c2.getX()) {
			c1.setWestWall(false);
			c2.setEastWall(false);
		} else if (c1.getY() == c2.getY() && c1.getX() < c2.getX()) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		} else {
			System.out.println("Error");
		}
	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unvisitedCells = new ArrayList<Cell>();
		
		if(c.getX() != 0 && !maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited())
			unvisitedCells.add(maze.getCell(c.getX() - 1, c.getY()));
		
		if(c.getY() != 0 && !maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited())
			unvisitedCells.add(maze.getCell(c.getX(), c.getY() - 1));
		
		if(c.getX() != width - 1 && !maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited())
			unvisitedCells.add(maze.getCell(c.getX() + 1, c.getY()));
		
		if(c.getY() != height - 1 && !maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited())
			unvisitedCells.add(maze.getCell(c.getX(), c.getY() + 1));
			
		return unvisitedCells;
	}
}