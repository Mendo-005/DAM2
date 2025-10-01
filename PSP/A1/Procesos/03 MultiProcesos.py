import time 
import multiprocessing as mp
import os 

def hijo(num):

    global valor 
    print(f"Hijo antes de modificar {valor}")
    valor = num
    print(f"Hijo {valor}")
    time.sleep(num)
    print("Salgo del proceso hijo{}".format(os.getid()))

valor = 0
def main():
    global valor
    valor = 5
    # Args es una tupla, al recibir solo un parametro, el fotmato es bi dimensional sin rellenar (3, "vacio")
    pl = mp.Process(target= hijo, args = (3,))
    pl.start()
    print("Proceso creado {}".format(pl.pid))
    # pl.join() - Sirve para matar un proceso Hijo y seguir con el proceso Padre
    print(f"Padre {valor}")

if __name__ == "__main__":
    main()