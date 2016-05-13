import matplotlib.pyplot as plt
import numpy.linalg as lin
from mpl_toolkits.mplot3d import Axes3D
import numpy as np

# Create a sphere
r = 1
pi = np.pi
cos = np.cos
sin = np.sin
t, s = np.mgrid[0.0:pi:100j, 0.0:2.0 * pi:200j] # fill 200 values for s and 100 for t
x = r*sin(t) * cos(s)
y = r*sin(t) * sin(s)
z = r*cos(t)
fig = plt.figure()
ax = fig.add_subplot(111, projection= '3d')

ax.plot_surface(x, y, z)

plt.show()