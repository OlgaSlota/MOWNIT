from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
N = 500

fig = plt.figure(2,figsize=(8, 8))

ax1 = fig.add_subplot(313,projection='3d',title= 'sphere * V')
ax2 = fig.add_subplot(322,projection='3d',title= 'sphere * SV')
ax3 = fig.add_subplot(331, projection='3d',title ='sphere * USV')

A1 = np.matrix([[5,3,5],[15,1,10],[2,4,2]])

s = np.linspace(0, 2 * np.pi, N)
t = np.linspace(0, np.pi, N)

x = np.outer(np.cos(s), np.sin(t))
y = np.outer(np.sin(s), np.sin(t))
z = np.outer(np.ones_like(s), np.cos(t))

y1=[]
x1=[]
z1=[]

y2=[]
x2=[]
z2=[]

y3=[]
x3=[]
z3=[]

u1,s1,v1 = np.linalg.svd(A1)
sv = np.diag(s1)*v1
for i in range(N):
    p=np.array(v1 * np.matrix([x[i], y[i], z[i]]))
    x1.append(p[0])
    y1.append(p[1])
    z1.append(p[2])
    q=np.array(sv * np.matrix([x[i], y[i], z[i]]))
    x2.append(q[0])
    y2.append(q[1])
    z2.append(q[2])
    r=np.array(A1 * np.matrix([x[i], y[i], z[i]]))
    x3.append(r[0])
    y3.append(r[1])
    z3.append(r[2])

ax1.plot_surface(x1, y1, z1)
ax2.plot_surface(x2, y2, z2)
ax3.plot_surface(x3, y3, z3)

plt.show()
