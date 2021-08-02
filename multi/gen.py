import random

"""
Generates a random undirected, weighted graph in edge-list format.

Dependencies:
- The "random" Python library must be imported.
 
Parameters:
v          : int    - number of vertices
e          : int    - number of edges (must satisfy 0 <= e <= v * (v - 1) / 2).
                      This parameter is only used when mode="random" (see below)
min_weight : int    - minimum edge weight
max_weight : int    - maximum edge weight 
seed       : int    - seed for the random number generator
mode       : string - type of graph to generate. There are three possible 
                      values: "random" generates a random graph. "linear" 
                      generates a graph that is a straight line. "binary" 
                      generates a graph that is a binary tree. 
                      (default="random")

Returns a list containing 3-tuples each indicating one edge in the format 
(source,destination,weight).
"""
def generate_random_weighted_graph(v,e,min_weight,max_weight,seed=112263,
                                   mode="random"):
    # initialize lists
    edges = []
    selection = []
    
    if mode == "random":
        # generate all candidate edges
        for i in range(v):
            for j in range(i + 1,v):
                selection.append(len(edges) < e)
                edges.append((i,j, random.randint(min_weight,max_weight)))
        
        # randomize which subset is selected
        n = len(edges)
        for i in range(len(selection)):
            target = random.randint(i,n - 1)
            selection[i],selection[target] = selection[target],selection[i]
        
        # return selected edges
        return [x for i,x in enumerate(edges) if selection[i]]

    if mode == "linear":
        for i in range(v - 1):
            w = random.randint(min_weight,max_weight)
            edges.append((i,i + 1,w))
        return edges

    if mode == "binary":
        i = 1
        while (i << 1) <= v:
            w = random.randint(min_weight,max_weight)
            edges.append((i - 1,(i << 1) - 1,w))
            if (i << 1) + 1 < v:
                w = random.randint(min_weight,max_weight)
                edges.append((i - 1,(i << 1),w))
            i += 1
        return edges

def main():
    MODE = "random"
    with open('test.txt', 'w') as f:
      g = generate_random_weighted_graph(
          v=10,
          e=25,
          min_weight=1,
          max_weight=10,
          seed=11111,
          mode=MODE
      )
      f.write('\n'.join([f'{u} {v}' for u, v, w in g]))
if __name__ == "__main__":
    main()