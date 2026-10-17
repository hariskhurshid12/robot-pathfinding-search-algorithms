# Robot Pathfinding Using Search Algorithms

**Student:** Haris Muhammad Khurshid  
**Student ID:** E12795713  
**Course:** CSM4880

This Java project compares Breadth-First Search (BFS) and A* search for finding paths through a grid with obstacles. A* is run with Manhattan, Euclidean, and Chebyshev heuristics. The program records expanded nodes and elapsed time, then creates visualizations for the 30% obstacle-density run.

## Run the project

Install a Java Development Kit (JDK), then run these commands from the repository folder:

```powershell
javac -d out *.java
java -cp out path.Main
```

The program prints results for 10%, 30%, and 40% obstacle densities. It saves the four PNG visualizations in the working folder and opens each one in a window, so run it in a desktop environment that can display Java Swing windows.

## Files

- `AStar.java`, `BFS.java`, `GridImage.java`, `Main.java`, and `PathResult.java` — search algorithms, grid visualization, runner, and result class.
- `*_path.png` — example path visualizations.
- `Robot Pathfinding Using Search Algorithms.docx` — final project report.

The supplied ZIP contains these same files and is omitted to avoid duplicating the source and assets.