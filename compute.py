# def get_letter_level(students,)
import decimal


def get_letter_grade(students, point):
    point = float(point)
    dif_float=float((float(100)-point)/7)
    # dif_str=str(dif_float)
    # dif_int=decimal.Decimal(dif_str)
    # dif=round(dif_int,2)

    # first_level = float(point + 6 * decimal.Decimal(dif_str))
    #     # second_level = float(point + 5 * decimal.Decimal(dif_str))
    #     # third_level = float(point + 4 * decimal.Decimal(dif_str))
    #     # forth_level = float(point + 3 * decimal.Decimal(dif_str))
    #     # fifth_level = float(point + 2 * decimal.Decimal(dif_str))
    #     # sixth_level = float(point + decimal.Decimal(dif_str))

    first_level = float(point + 6 * dif_float)
    second_level = float(point + 5 * dif_float)
    third_level = float(point + 4 * dif_float)
    forth_level = float(point + 3 * dif_float)
    fifth_level = float(point + 2 * dif_float)
    sixth_level = float(point + dif_float)

    for x in students.keys():
        if students[x][8] >= first_level:
            students[x][9] = 'A+'
        elif students[x][8] >=second_level:
            students[x][9] = 'A'
        elif students[x][8]>= third_level:
            students[x][9]='A-'
        elif students[x][8] >= forth_level:
            students[x][9] = 'B+'
        elif students[x][8] >= fifth_level:
            students[x][9] = 'B'
        elif students[x][8]>= sixth_level:
            students[x][9]='B-'
        elif students[x][8]>= point:
            students[x][9]='C'
        else:
            students[x][9]='F'
    return students


def print_a1(order_students,full_mark):
    print('A1 ','grades ','(',full_mark[0],')')
    list=[]

    for key in order_students.keys():
        name=order_students.get(key)[1]+','+order_students.get(key)[2]
        cur_list=(order_students.get(key)[0], name, order_students.get(key)[3])
        list.append(cur_list)
    mx = max((len(str(ele)) for sub in list for ele in sub))
    for row in list:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


def print_a2(order_students,full_mark):
    print('A2 ', 'grades ', '(', full_mark[1], ')')
    list = []
    for key in order_students.keys():
        name = order_students.get(key)[1] + ',' + order_students.get(key)[2]
        cur_list = (order_students.get(key)[0], name, order_students.get(key)[4])
        list.append(cur_list)
    mx = max((len(str(ele)) for sub in list for ele in sub))
    for row in list:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))

def print_project(order_students,full_mark):
    print('PR ', 'grades ', '(', full_mark[2], ')')
    list = []
    for key in order_students.keys():
        name = order_students.get(key)[1] + ',' + order_students.get(key)[2]
        cur_list = (order_students.get(key)[0], name, order_students.get(key)[5])
        list.append(cur_list)
    mx = max((len(str(ele)) for sub in list for ele in sub))
    for row in list:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


def print_test1(order_students,full_mark):
    print('T1 ', 'grades ', '(', full_mark[3], ')')
    list = []
    for key in order_students.keys():
        name = order_students.get(key)[1] + ',' + order_students.get(key)[2]
        cur_list = (order_students.get(key)[0], name, order_students.get(key)[6])
        list.append(cur_list)
    mx = max((len(str(ele)) for sub in list for ele in sub))
    for row in list:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


def print_test2(order_students,full_mark):
    print('T2 ', 'grades ', '(', full_mark[4], ')')
    list = []
    for key in order_students.keys():
        name = order_students.get(key)[1] + ',' + order_students.get(key)[2]
        cur_list = (order_students.get(key)[0], name, order_students.get(key)[7])
        list.append(cur_list)
    mx = max((len(str(ele)) for sub in list for ele in sub))
    for row in list:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


def print_avg_a1(students,full_mark):
    sum_a1 = 0
    for key in students.keys():
        if students.get(key)[3] != " ":
            sum_a1 += students.get(key)[3]

    avg_a1 = float(round(sum_a1 / len(students), 2))
    print("A1 average: ", avg_a1, "/", full_mark[0])


def print_avg_a2(students,full_mark):
    sum_a2 = 0
    for key in students.keys():
        if students.get(key)[4] != " ":
            sum_a2 += students.get(key)[4]
    avg_a2 = float(round(sum_a2 / len(students), 2))
    print("A2 average: ", avg_a2, "/", full_mark[1])


def print_avg_project(students,full_mark):
    sum_project = 0
    for key in students.keys():
        if students.get(key)[5]!= " ":
            sum_project += students.get(key)[5]

    avg_project = float(round(sum_project / len(students), 2))
    print("PR average: ", avg_project, "/", full_mark[2])


def print_avg_test1(students,full_mark):
    sum_test1 = 0
    for key in students.keys():
        if students.get(key)[6] != " ":
            sum_test1 += students.get(key)[6]

    avg_test1 = float(round(sum_test1 / len(students), 2))
    print("T1 average: ", avg_test1, "/", full_mark[3])


def print_avg_test2(students,full_mark):
    sum_test2 = 0
    for key in students.keys():
        if students.get(key)[7] !=" ":
            sum_test2 += students.get(key)[7]
    avg_test2 = float(round(sum_test2 / len(students), 2))
    print("T2 average: ", avg_test2, "/", full_mark[4])


def print_student(order_students):
    list=[]
    list1=['ID', 'LN', 'FN', 'A1', 'A2', 'PR', 'T1', 'T2', 'GR','FL']
    list.append(list1)
    for key in order_students.keys():
        list.append(order_students.get(key))

    mx = max((len(str(ele)) for sub in list for ele in sub))
    for row in list:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))


def print_order_name(list):

    mx = max((len(str(ele)) for sub in list for ele in sub))
    for row in list:
        print(" ".join(["{:<{mx}}".format(ele, mx=mx) for ele in row]))
