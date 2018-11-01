import collections
import copy
import operator
import decimal
from compute import get_letter_grade
from compute import print_a1
from compute import print_a2
from compute import print_avg_a1
from compute import print_avg_a2
from compute import print_avg_project
from compute import print_project
from compute import print_test1
from compute import print_test2
from compute import print_avg_test1
from compute import print_avg_test2
from compute import print_student
from compute import print_order_name


def get_basic_information(ID, LN, FN, A1=" ", A2=" ", PR=" ", T1=" ", T2=" ", GR=" ", FL='F'):
    person=[ID, LN, FN, A1, A2, PR, T1, T2, GR, FL]
    return person


students={}
full_mark={}
with open('class.txt') as f:
    for line2 in f:
        line1=line2.strip( )
        line=line1.split("|")
        student=[]
        person=get_basic_information(line[0],line[1],line[2])
        (key,value)=(line[0],person)
        students[int(key)] = value
        keys=students.keys()

with open('a1.txt') as g:
    for line1 in g:
        ID=0
        a1=0
        line2 = line1.strip( )
        line = line2.split("|")
        if len(line) == 1:
            full_a1=int(line2)
            full_mark[0]=full_a1
        else:
            ID = int(line[0])
            students.get(ID)[3]=int(line[1])

with open('a2.txt') as g:
    for line1 in g:
        ID=0
        a2=0
        line2 = line1.strip( )
        line = line2.split("|")

        if len(line) == 1:
            full_a2 = int(line2)
            full_mark[1]=full_a2

        else:
            ID = int(line[0])
            a2=int(line[1])
            students.get(ID)[4]=a2


with open('project.txt') as g:
    for line1 in g:
        line2 = line1.strip()
        line = line2.split("|")
        ID=0
        pr=0

        if len(line) == 1:
            full_project = int(line2)
            full_mark[2]=full_project

        else:
            ID = int(line[0])
            pr = int(line[1])
            students.get(ID)[5]=pr

with open('test1.txt') as g:
    for line1 in g:
        ID=0
        test1=0
        line2 = line1.strip( )
        line = line2.split("|")

        if len(line) == 1:
            full_test1 = int(line2)
            full_mark[3]=full_test1

        else:
            ID = int(line[0])
            test1=int(line[1])
            students.get(ID)[6]=test1

with open('test2.txt') as g:
    for line1 in g:
        ID=0
        test2=0
        line2 = line1.strip( )
        line = line2.split("|")

        if len(line) == 1:
            full_test2 = int(line2)
            full_mark[4]=full_test2

        else:
            ID = int(line[0])
            test2 = int(line[1])
            students.get(ID)[7] = test2

# c= 2.675
# b= str(c)
# a=decimal.Decimal(b)
# print(round(a,2))


for key in students.keys():
    # print(students.get(key)[8])
    if students.get(key)[3] != " ":
        a1_grade=7.5*float(students.get(key)[3])/full_mark[0]
    else:
        a1_grade=0

    if students.get(key)[4] != " ":
        a2_grade=7.5*float(students.get(key)[4])/full_mark[1]
    else:
        a2_grade=0

    if students.get(key)[5] != " ":
        pr_grade=25 * float(students.get(key)[5]) / full_mark[2]
    else:
        pr_grade=0

    if  students.get(key)[6] != " ":
        test1_grade=30 * float(students.get(key)[6]) / full_mark[3]
    else:
        test1_grade=0

    if students.get(key)[7] != " ":
        test2_grade=30*float(students.get(key)[7])/full_mark[4]
    else:
        test2_grade=0



    cur_float = a1_grade+ a2_grade + pr_grade +test1_grade + test2_grade
    cur_str=str(cur_float)
    cur_int=decimal.Decimal(cur_str)
    students.get(key)[8]= (round(cur_int,2))

get_letter_grade(students,50)
order_students=collections.OrderedDict(sorted(students.items()))


def main():
    task = '0'

    while task != '6':
        print('\n')
        print("1> Display individual component")
        print("2> Display component average")
        print("3> Display Standard Report")
        print("4> Sort by alternate column")
        print("5> Change Pass/Fail point")
        print("6> Exit")
        print('\n')
        task = input("Please enter your task (1-6) : ")
        if task == '1':
            component = input("please enter your component (A1, A2, PR, T1, T2) : ")
            flag = component.lower()== 'a1' or component.lower()== 'a2' or component.lower()=='pr' or component.lower()=='t1' or component.lower()=='t2'
            while flag == False:
                print("Error input, input should be one of (A1, A2, PR, T1, T2).")
                component = input("please enter your component (A1, A2, PR, T1, T2) : ")
                flag = component.lower() == 'a1' or component.lower() == 'a2' or component.lower() == 'pr' or component.lower() == 't1' or component.lower() == 't2'
            if component.lower() == 'a1':
                print_a1(order_students,full_mark)

            elif component.lower() == 'a2':
                print_a2(order_students,full_mark)

            elif component.lower() == 'pr':
                print_project(order_students,full_mark)

            elif component.lower() == 't1':
                print_test1(order_students,full_mark)

            else :
                print_test2(order_students,full_mark)

        elif task == '2':
            component = input("please enter your component (A1, A2, PR, T1, T2) : ")
            flag = component.lower()== 'a1' or component.lower()== 'a2' or component.lower()=='pr' or component.lower()=='t1' or component.lower()=='t2'
            while flag==False:
                print("Error input, input should be one of (A1, A2, PR, T1, T2).")
                component = input("please enter your component(A1, A2, PR, T1, T2) : ")
                flag = component.lower() == 'a1' or component.lower() == 'a2' or component.lower() == 'pr' or component.lower() == 't1' or component.lower() == 't2'

            if component.lower() == 'a1':
                print_avg_a1(order_students, full_mark)

            elif component.lower() == 'a2':
                print_avg_a2(order_students, full_mark)

            elif component.lower() == 'pr':
                print_avg_project(order_students, full_mark)

            elif component.lower() == 't1':
                print_avg_test1(order_students, full_mark)

            else :
                print_avg_test2(order_students, full_mark)

        elif task == '3':
            print_student(order_students)
            main()

        elif task == '4':
            list = []
            list1 = ['ID', 'LN', 'FN', 'A1', 'A2', 'PR', 'T1', 'T2', 'GR', 'FL']

            component = input("sorted by component (LN, GR) : ")
            flag= component.lower()=='ln' or component.lower()=='gr'
            for key in students.keys():
                list.append(students.get(key))
            while flag==False:
                print("Error input, input should be LN or GR. ")
                component = input("sorted by component (LN, GR) : ")
                flag = component.lower() == 'ln' or component.lower() == 'gr'

            if component.lower() == 'ln':
                order_ln = sorted(list, key=operator.itemgetter(1))
                order_ln.insert(0,list1)
                print_order_name(order_ln)
            else :
                order_fn = sorted(list, key=operator.itemgetter(8),reverse=True)
                order_fn.insert(0,list1)
                print_order_name(order_fn)

        elif task == '5':
            point_str = input("please enter new P/F point : ")
            point = float(point_str)
            flag= 0.00<= point<= 100.00
            while flag == False:
                print("Error input, input range should between 0 to 100.")
                point_str=input("please enter new P/F point : ")
                point = float(point_str)
                flag=0.00<= point<= 100.00
            copy_order_students = copy.deepcopy(order_students)
            get_letter_grade(copy_order_students, point)
            print_student(copy_order_students)


        elif task == '6':
            print("Good bye")
            quit()
        else:
            print("Error input! Input should from 1 to 6. ")
            main()



main()

