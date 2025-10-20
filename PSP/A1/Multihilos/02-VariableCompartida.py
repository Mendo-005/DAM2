import threading

def resto():
    global Contador
    global numIter
    while numIter < 200:
        numIter +=1
        if Contador > 0:
            Contador -= 1
            print(f"Resto {Contador}", flush=True)
    return

def sumo():
    global Contador
    global numIter
    while numIter < 200:
        numIter +=1
        if Contador < 10:
            Contador += 1
            print(f"Sumo {Contador}", flush=True)
    return

Contador = 5
numIter = 0
t1 = threading.Thread(target=sumo)
t2 = threading.Thread(target=resto)
t1.start()
t2.start()
