import threading
import time

def resto(mc, i):
    global Contador
    numIter = 0
    while numIter < 500:
        numIter +=1
        mc.acquire()
        #if Contador > 0:
        Contador -= i
        print(f"Resto {i} y da {Contador}", flush=True)
        mc.release()
    return

def sumo(mc, i):
    global Contador
    numIter = 0
    while numIter < 500:
        numIter +=1
        mc.acquire()
        #if Contador < 10:
        Contador += i
        print("Sumo {} y da {}".format(i,Contador), flush=True)
        mc.release()
    return

Contador = 5
numIter = 0
mys = threading.Semaphore(3)
for i in range(1):
    t1 = threading.Thread(target=sumo, args=(mys,1,))
    t2 = threading.Thread(target=resto, args=(mys,1,))
    t2.start()
    t1.start()
