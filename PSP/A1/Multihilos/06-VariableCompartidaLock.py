import threading

def resto(l):
    global Contador
    global numIter
    while numIter < 200:
        numIter +=1
        l.acquire()
        if Contador > 0:
            Contador -= 1
            print(f"Resto {Contador}", flush=True)
        l.release()
    return

def sumo(l):
    global Contador
    global numIter
    while numIter < 200:
        numIter +=1
        l.acquire()
        if Contador < 10:
            Contador += 1
            print(f"Sumo {Contador}", flush=True)
        l.release()
    return

Contador = 5
numIter = 0
lock = threading.Lock()
t1 = threading.Thread(target=sumo, args=(lock,))
t2 = threading.Thread(target=resto, args=(lock,))
t1.start()
t2.start()
