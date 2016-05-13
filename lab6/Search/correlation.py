import numpy as np
import numpy.linalg as la
import sys
import scipy.sparse.linalg as spl

# arguments passed from Java program
M = int(sys.argv[1])
N = int(sys.argv[2])
k = int(sys.argv[3])
svd =bool(sys.argv[4])
rank = int(sys.argv[5])

# vector of words which are being searched
q = [0 for x in range(M)]

# matrix of frequencies of words from dictionary in files from directory
matrix = [[0 for x in range(N)] for y in range(M)]

# help function to extract a column from matrix
def column(mat, ind):
    return [row[ind] for row in mat]

# help function to check if current correlation value should be inserted to the array
def closer(cor, arr):
    for ind in range(len(arr)):
        if cor > arr[ind][1]:
            return ind
    return -1

# the most important function to find closest vectors ( with strongest correlation) to our qiven q vector
def best(matr, number):

    files = [[0,0] for x in range(number)]
    q1 = q/la.norm(q)

    if(svd):
        A = doSVD(matr,rank)
    else:
        A = matr

    for j in range(len(A[0])):
        dj = column(A, j)
        dj1 = dj/la.norm(dj)
        corj = np.dot(q1,dj1)

        if j < k:
            files[j]=[j, corj]
        else:
            replace = closer(corj,files)
            if replace != -1:
                files[replace] = [j,corj]

    files.sort(key=lambda x: x[1])
    files.reverse()
    return [a for [a,b] in files]


# SVD factorisation to perform low rank approximation
def doSVD(A,rank):
    u,s,v= spl.svds(A,rank)
    s2=s
    for i in range(rank + 1, len(s2)):
        s2[i] = 0
    return (u.dot(np.diag(s2)).dot(v))



fo = open("matrix.txt", "r")
for ln in fo:
    ln = ln.split(" ")
    i=int(ln[0])
    j=int(ln[1])
    val= float(ln[2])
    matrix[i][j]=val
fo.close()

f =open("q.txt","r")
i=0
for ln in f:
    ln = ln.split(" ")
    q[i]= int(ln[0])
    i+=1
f.close()

fm =open("topK.txt","w")
l = best(matrix,k)
for i in range(k):
    fm.write(str(l[i]) + " ")
fm.close()
