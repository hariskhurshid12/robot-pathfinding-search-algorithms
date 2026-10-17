package path;
import java.util.*;

//A* search - informed search using heuristic to guide towards goal
//supports manhattan, euclidean, and chebyshev heuristics
public class AStar {

 static int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};

 public static PathResult search(boolean[][] grid, int[] start, int[] goal, String heuristic) {

     int rows = grid.length;
     int cols = grid[0].length;

     double[][] gScore = new double[rows][cols]; // cost from start
     int[][][] parent  = new int[rows][cols][];
     int[][] visitCount = new int[rows][cols];
     boolean[][] closed = new boolean[rows][cols];

     // fill gScore with infinity
     for (double[] row : gScore) Arrays.fill(row, Double.MAX_VALUE);
     gScore[start[0]][start[1]] = 0;

     // priority queue sorted by f = g + h
     // each entry is {fScore, gScore, row, col}
     // I'm using gScore as tiebreaker to avoid comparison errors
     PriorityQueue<double[]> openSet = new PriorityQueue<>(
         (a, b) -> {
             if (a[0] != b[0]) return Double.compare(a[0], b[0]);
             return Double.compare(a[1], b[1]); // tiebreak by g
         }
     );

     double h0 = getHeuristic(start[0], start[1], goal[0], goal[1], heuristic);
     openSet.add(new double[]{h0, 0, start[0], start[1]});

     int nodesExpanded = 0;

     while (!openSet.isEmpty()) {
         double[] entry = openSet.poll();
         int r = (int) entry[2];
         int c = (int) entry[3];

         if (closed[r][c]) continue;
         closed[r][c] = true;
         nodesExpanded++;
         visitCount[r][c]++;

         if (r == goal[0] && c == goal[1]) {
             List<int[]> path = BFS.buildPath(parent, start, goal);
             return new PathResult(true, path, nodesExpanded, visitCount);
         }

         for (int[] dir : directions) {
             int nr = r + dir[0];
             int nc = c + dir[1];

             if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
             if (grid[nr][nc] || closed[nr][nc]) continue;

             double newG = gScore[r][c] + 1;
             if (newG < gScore[nr][nc]) {
                 gScore[nr][nc] = newG;
                 parent[nr][nc] = new int[]{r, c};
                 double h = getHeuristic(nr, nc, goal[0], goal[1], heuristic);
                 openSet.add(new double[]{newG + h, newG, nr, nc});
             }
         }
     }

     return new PathResult(false, null, nodesExpanded, visitCount);
 }

 static double getHeuristic(int r, int c, int gr, int gc, String type) {
     int dr = Math.abs(r - gr);
     int dc = Math.abs(c - gc);

     switch (type) {
         case "manhattan":
             // admissible for 4-directional movement
             return dr + dc;
         case "euclidean":
             // straight-line distance - admissible but may explore more nodes
             return Math.sqrt(dr * dr + dc * dc);
         case "chebyshev":
             // max of row/col difference - designed for 8-dir, included for comparison
             return Math.max(dr, dc);
         default:
             return 0;
     }
 }
}
