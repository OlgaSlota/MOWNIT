from scipy.sparse.linalg import eigs
from scipy.sparse import csr_matrix
import numpy as np
import networkx as nx
import warnings

G= nx.read_edgelist("directed.txt")

X = nx.adjacency_matrix(G)

cx = csr_matrix(X)
cx.sort_indices()

row_sums = np.squeeze(np.asarray(cx.sum(axis=1),dtype=np.float64))
rows,cols = cx.nonzero()
data = np.array( [ (x/row_sums[rows[ii]]) for ii,x in   enumerate(cx.data)] )
A = csr_matrix((data,cx.indices,cx.indptr),shape=cx.shape)


w,v = eigs(A, k=100)

msg="Casting complex values to real discards the imaginary part"
warnings.filterwarnings("ignore", message=msg)

w=w.astype(float)
v=v.astype(float)

print("max eigenvalue: ")
print(w.max())

m = 0
max= w[m]
for i in range(w.size):
    if w[i]>max:
        max=w[m]
        m= i
print("and corresponding eigenvector:")
print( v.transpose()[m])
