from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
N = 500

fig = plt.figure(2,figsize=(16,5))

ax1 = fig.add_subplot(133,projection='3d')
ax2 = fig.add_subplot(132,projection='3d')
ax3 = fig.add_subplot(131, projection='3d')

A1 = np.matrix([[5,5,5],[1,1,10],[2,4,2]])
A2 = np.matrix([[9,2,5],[15,7,3],[3,4,12]])
A3 = np.matrix([[5,2,5],[1,3,1],[7,4,9]])

s = np.linspace(0, 2 * np.pi, N)
t = np.linspace(0, np.pi, N)

x = np.outer(np.cos(s), np.sin(t))
y = np.outer(np.sin(s), np.sin(t))
z = np.outer(np.ones_like(s), np.cos(t))

y3=[]
x3=[]
z3=[]
y2=[]
x2=[]
z2=[]
y1=[]
x1=[]
z1=[]

for i in range(N):
    p=np.array(A1 * np.matrix([x[i], y[i], z[i]]))
    x1.append(p[0])
    y1.append(p[1])
    z1.append(p[2])
    q=np.array(A2 * np.matrix([x[i], y[i], z[i]]))
    x2.append(q[0])
    y2.append(q[1])
    z2.append(q[2])
    r=np.array(A3 * np.matrix([x[i], y[i], z[i]]))
    x3.append(r[0])
    y3.append(r[1])
    z3.append(r[2])

ax1.plot_surface(x1, y1, z1,linewidth=0, alpha =0.3)
ax2.plot_surface(x2, y2, z2,linewidth=0, alpha =0.3)
ax3.plot_surface(x3, y3, z3,linewidth=0, alpha =0.3)

plt.show()
