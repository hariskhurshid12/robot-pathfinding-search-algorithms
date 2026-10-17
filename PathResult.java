package path;
import java.util.List;

//just a simple class to hold the result of a search
public class PathResult {
 boolean found;
 List<int[]> path;
 int nodesExpanded;
 int[][] visitCount; // how many times each cell was visited (for heatmap)

 public PathResult(boolean found, List<int[]> path, int nodesExpanded, int[][] visitCount) {
     this.found = found;
     this.path = path;
     this.nodesExpanded = nodesExpanded;
     this.visitCount = visitCount;
 }
}
