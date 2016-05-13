from scipy.sparse.linalg import eigs
from scipy.sparse import csr_matrix
import numpy as np
import networkx as nx
import warnings
import random
import matplotlib.pyplot as p
import math
G= nx.read_edgelist("directed.txt")
c = 0.85
X = nx.adjacency_matrix(G)
N= X.size
cx = csr_matrix(X)
cx.sort_indices()

row_sums = np.squeeze(np.asarray(cx.sum(axis=1),dtype=np.float64))
rows,cols = cx.nonzero()
data = np.array( [ (x/row_sums[rows[ii]]) for ii,x in   enumerate(cx.data)] )
A = csr_matrix((data,cx.indices,cx.indptr),shape=cx.shape)

home = [0 for i in range(N)]
for i in range(int(N/1000)):
    home[random.randint(0,N-1)]=1

E = csr_matrix((home,cx.indices,cx.indptr),shape=cx.shape)

w,v = eigs((A+E).multiply(c), k=50)

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
r = v.transpose()[m]

for ind in range(len(r)):
    r[ind]=math.sqrt(math.sqrt(math.fabs(r[ind])))


print( r)
p.hist(np.multiply(r,100),bins=5000)
p.show()