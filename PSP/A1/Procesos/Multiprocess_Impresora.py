import time
import multiprocessing as mp
import os


def fichero():
    ctx = mp.get_context()
    time.sleep(5)
    print("====================")
    print("".format(os.getpid()))
    print("====================")
    os._exit()

def main():
    # Creamos un fichero con perimsos de escritura
    with open("fichero.txt", "w") as archivo:
              
        while(True):
            linea = print(input("Escriba una línea: "))
            # Si la líne es -1 salimos del prceso he imprimimos el id
            if linea == "-1":
                print("Saliendo del proceso {}".format(os.getpid()))

                os._exit(0)

            elif linea != "-1" and linea != "-P":
                archivo.write(f"{linea}\n")

            elif linea == "-P":
                p1 = mp.Process(target=fichero, args=(3,))
                p1.start()

# Iniciador del programa
if __name__ == "__main__" :
    mp.set_start_method('spawn')
    main()