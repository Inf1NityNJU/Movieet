path = "/Users/Kray/Desktop/PythonHelper/"
# path = "/mydata/krayc/"

f = open(path + "scoreAndReview.txt", "r")
f2 = open(path + "scoreAndReviewResult.txt", "a")
i = 0

f2.write("\n")
writeLines = []

for line in f.readlines():

    line = line.strip()
    if not len(line):
        continue

    if line[-7: ] == "Timeout":
        # i += 1
        #
        # print(i, " : ", line[-17 : -8])
        # count = MovieIMDBReviewCountGetter.getIMDBReviewCount(line[-17 : -8])
        #
        # try:
        #     f2.write(line + count + "\n")
        # except:
        #     pass
        pass
    else:
        writeLines.append(line)
    # time.sleep(5)
for line in writeLines:
    f2.write(line + "\n")