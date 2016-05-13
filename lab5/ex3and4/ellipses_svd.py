from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
import numpy.linalg as alg
N = 100
fig = plt.figure(2,figsize=(16,5))

ax1 = fig.add_subplot(133,projection='3d',title= 'A1')
ax2 = fig.add_subplot(132,projection='3d',title= 'A2')
ax3 = fig.add_subplot(131, projection='3d',title ='A3')

A1 = np.matrix([[2,3,5],[1,5,1],[3,1,2]])
A2 = np.matrix([[49,2,5],[5,7,3],[3,4,2]])
A3 = np.matrix([[5,2,2],[1,3,1],[7,4,1]])

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

u1,s1,v1 = alg.svd(A1)
u2,s2,v2 = alg.svd(A2)
u3,s3,v3 = alg.svd(A3)

for i in range(3):
    semi1=(s1[i] * u1[i]).reshape(3, 1)
    ax1.quiver(0,0,0, semi1[0], semi1[1], semi1[2], color='r', length =15, arrow_length_ratio=0)
    semi2=(s2[i] * u2[i]).reshape(3, 1)
    ax2.quiver(0,0,0, semi2[0], semi2[1], semi2[2], color='r', length =15,arrow_length_ratio=0)
    semi3=(s3[i] * u3[i]).reshape(3, 1)
    ax3.quiver(0,0,0,semi3[0], semi3[1], semi3[2], color='r', length =15,arrow_length_ratio=0)

print('\nMatrix A2 singular values : max=', s2[0], ' min=',s2[2], ' ratio=', s2[0]/s2[2])
ax1.plot_surface(x1, y1, z1, color='y',linewidth=0, alpha =0.3)
ax2.plot_surface(x2, y2, z2,color='y',linewidth=0, alpha = 0.3)
ax3.plot_surface(x3, y3, z3,color='y',linewidth=0, alpha = 0.3)

plt.show()
