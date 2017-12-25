import numpy as np
from sklearn import tree

TRAIN_FILE_NAME = '1505760800_9146957_train_drugs.data'
TEST_FILE_NAME = '1505760800_9191542_test.data'
RESULT_FILE_NAME = 'resultpython.txt'


def arrToVect(arr):
    vector = [0] * 100000
    for i in arr:
        vector[int(i) - 1] = 1
    return vector


def createModel(filename):
    X = []
    Y = []
    print("Reading Training")
    with open(filename, 'r') as f:
        data = f.readlines()
        for line in data:
            arr = line.split()
            Y.append(int(arr[0]))
            # X.append(arr)
            X.append(arrToVect(arr[1:]))
    print("Building Tree")
    clf = tree.DecisionTreeClassifier()
    clf = clf.fit(X, Y)
    print("Tree Successful\n")
    return clf


def testSet(filename):
    print("Reading Test Data\n")
    tSet = []
    with open(filename, 'r') as f:
        data = f.readlines()
        for line in data:
            arr = line.split()
            tSet.append(arrToVect(arr))
    # tSet = np.array(tSet)
    return tSet


def listToFile(itemList, filename):
    with open(filename, 'w') as f:
        for item in itemList:
            f.write(str(item) + '\n')
    return


clfPredictor = createModel(TRAIN_FILE_NAME)
answer = []
testSetList = testSet(TEST_FILE_NAME)
output = clfPredictor.predict(testSetList)

listToFile(output, RESULT_FILE_NAME)
