import matplotlib.pyplot as plt
import networkx as nx

G = nx.Graph()
m = 0

fo = open("results.txt", "r")

for ln in fo:
    ln = ln.split(" ")
    if m<abs(float(ln[2])): m= abs(float(ln[2]))
fo.seek(0)

for line in fo:
    line = line.split(" ")
    G.add_edge(line[0],line[1],weight= abs(float(line[2])/m))

fo.close()

edges,weights = zip(*nx.get_edge_attributes(G,'weight').items())

pos = nx.spring_layout(G)
nx.draw(G, pos, node_color='g', edgelist=edges, edge_color=weights, width=3.0, edge_cmap=plt.cm.Blues,with_labels=True)
plt.show()
