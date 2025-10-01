import time
import multiprocessing as mp
import os

def hijo(num):
    c1 = mp.get_context()
    global valor
    print("Hijo antes de modificar: ", valor)
    valor = num
    print("Hijo: ", valor)
    time.sleep(num)
    print("Salgo del proceso hijo {}".format(os.getpid()))
    os._exit(0)
valor = 0

def main():
    global valor
    valor = -5
    p1 = mp.Process(target=hijo, args=(3,))
    p1.start()
    print ("Proceso creado {}".format(p1.pid))
    #p1.join()
    print("Padre: ", valor)
    p2 = mp.Process(target=hijo, args=(1,))
    p2.start()
    print ("Proceso creado {}".format(p2.pid))
    #p2.join()
    print("Padre: ", valor)
    print("Salgo del proceso padre {}".format(os.getpid()))

if __name__ == '__main__':
    mp.set_start_method('spawn')
    main()