# Ford-Fulkerson-Maximum-Flow

The method **fordfulkerson** computes an integer corresponding to the max flow of the “graph” and the graph itself. It outputs a .txt file nameed fordfulkersonOutput.txt

The method **pathDFS** finds a path through a Depth First Search (DFS) between the nodes “source” and
“destination” in the “graph” through non-zero weight edges. You must return an ArrayList of
Integers with the list of unique nodes belonging to the path found by the DFS. If no path is
found, return an empty ArrayList. The first element in the list must correspond to the “source”
node, the second element in the list must be the second node in the path, and so on until the last
element (i.e., the “destination” node) is stored.

An example of WGraph object can be found in the ff2.txt file. 

An expected output once we execute the **fordfulkerson.java** file with the **ff2.txt** as an input file can be found in the **fordfulkersonOutput.txt** file.
