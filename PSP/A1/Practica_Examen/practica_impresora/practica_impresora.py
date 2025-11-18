from multiprocessing import Process as ps
import os
import time

nombre_fichero = "mi_fichero.txt"

def main():
    while(True):
        linea = input("Escriba una linea: ")
        
        if linea == "-1":
            print(f"Id del proceso: {os.getpid()}")
            break

        elif linea == "-P":
            p2 = ps(target = nom_fichero)
            p2.start()
            p2.join()

        else:
            p1 = ps(target = impresion, args= (linea,))
            p1.start()


def impresion(linea):
    try:
        with open(nombre_fichero, "a", encoding='utf-8') as f:
            f.write(f"{linea} \n")

    except IOError as e:
        print("Error al escribir en el fichero: " + e)

def nom_fichero():
    
    time.sleep(3)
    print("----------")
    print(nombre_fichero)
    print("----------")
    print(f"proceso id: {os.getpid()}")

if __name__ == "__main__":
    main()