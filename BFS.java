package path;
import java.util.*;

//BFS - uninformed search, explores everything level by level
//guaranteed to find shortest path on unweighted grid
public class BFS {

 static int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}}; // up, down, left, right

 public static PathResult search(boolean[][] grid, int[] start, int[] goal) {

     int rows = grid.length;
     int cols = grid[0].length;

     boolean[][] visited = new boolean[rows][cols];
     int[][][] parent = new int[rows][cols][];  // to reconstruct path
     int[][] visitCount = new int[rows][cols];

     Queue<int[]> queue = new LinkedList<>();
     queue.add(start);
     visited[start[0]][start[1]] = true;

     int nodesExpanded = 0;

     while (!queue.isEmpty()) {
         int[] current = queue.poll();
         nodesExpanded++;
         visitCount[current[0]][current[1]]++;

         // check if we reached the goal
         if (current[0] == goal[0] && current[1] == goal[1]) {
             List<int[]> path = buildPath(parent, start, goal);
             return new PathResult(true, path, nodesExpanded, visitCount);
         }

         for (int[] dir : directions) {
             int nr = current[0] + dir[0];
             int nc = current[1] + dir[1];

             if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                     && !grid[nr][nc] && !visited[nr][nc]) {
                 visited[nr][nc] = true;
                 parent[nr][nc] = current;
                 queue.add(new int[]{nr, nc});
             }
         }
     }

     // no path found
     return new PathResult(false, null, nodesExpanded, visitCount);
 }

 static List<int[]> buildPath(int[][][] parent, int[] start, int[] goal) {
     LinkedList<int[]> path = new LinkedList<>();
     int[] cur = goal;
     while (cur != null) {
         path.addFirst(cur);
         if (cur[0] == start[0] && cur[1] == start[1]) break;
         cur = parent[cur[0]][cur[1]];
     }
     return path;
 }
}
