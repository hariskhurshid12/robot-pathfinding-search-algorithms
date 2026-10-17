package path;
import java.util.List;
import java.util.Random;

// CSM4880 Term Project - Robot Pathfinding
// Haris Muhammad Khurshid - E12795713

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Robot Pathfinding - CSM4880 Term Project ===");
        System.out.println("Student: Haris Muhammad Khurshid | E12795713\n");

        // I'm testing 3 different densities like the professor said
        double[] densities = {0.10, 0.30, 0.40};
        int gridSize = 70; 
        long seed = 2342; // fixed seed so both algorithms get the same grid

        for (double d : densities) {
            System.out.println("--- Obstacle Density: " + (int)(d * 100) + "% ---");

            // generate the grid
            boolean[][] grid = generateGrid(gridSize, gridSize, d, seed);
            int[] start = {1, 1};
            int[] goal  = {gridSize - 2, gridSize - 2};

            // run BFS
            long t1 = System.currentTimeMillis();
            PathResult bfsResult = BFS.search(grid, start, goal);
            long bfsTime = System.currentTimeMillis() - t1;

            // run A* with manhattan
            long t2 = System.currentTimeMillis();
            PathResult manhattanResult = AStar.search(grid, start, goal, "manhattan");
            long manhattanTime = System.currentTimeMillis() - t2;

            // run A* with euclidean
            long t3 = System.currentTimeMillis();
            PathResult euclideanResult = AStar.search(grid, start, goal, "euclidean");
            long euclideanTime = System.currentTimeMillis() - t3;

            // run A* with chebyshev
            long t4 = System.currentTimeMillis();
            PathResult chebyshevResult = AStar.search(grid, start, goal, "chebyshev");
            long chebyshevTime = System.currentTimeMillis() - t4;

            // print results
            printResult("BFS",            bfsResult,       bfsTime);
            printResult("A* Manhattan",   manhattanResult, manhattanTime);
            printResult("A* Euclidean",   euclideanResult, euclideanTime);
            printResult("A* Chebyshev",   chebyshevResult, chebyshevTime);
            System.out.println();

            // save image for 30% density
            if (d == 0.30 && bfsResult.found) {
                GridImage.save(grid, bfsResult.path,       start, goal, bfsResult.visitCount,       "bfs_path.png");
                GridImage.save(grid, manhattanResult.path, start, goal, manhattanResult.visitCount, "manhattan_path.png");
                GridImage.save(grid, euclideanResult.path, start, goal, euclideanResult.visitCount, "euclidean_path.png");
                GridImage.save(grid, chebyshevResult.path, start, goal, chebyshevResult.visitCount, "chebyshev_path.png");
                System.out.println("Images saved for 30% density run.\n");
            }
        }
    }

    static void printResult(String name, PathResult r, long ms) {
        if (r.found) {
            System.out.printf("  %-16s | nodes: %5d | path: %3d steps | time: %d ms%n",
                    name, r.nodesExpanded, r.path.size() - 1, ms);
        } else {
            System.out.printf("  %-16s | NO PATH FOUND%n", name);
        }
    }

    // generates a random grid - true means obstacle
    static boolean[][] generateGrid(int rows, int cols, double density, long seed) {
        boolean[][] grid = new boolean[rows][cols];
        Random rand = new Random(seed);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // border walls
                if (r == 0 || r == rows-1 || c == 0 || c == cols-1) {
                    grid[r][c] = true;
                } else if (rand.nextDouble() < density) {
                    grid[r][c] = true;
                }
            }
        }

        // make sure start and goal are clear
        grid[1][1] = false;
        grid[rows-2][cols-2] = false;

        return grid;
    }
}
