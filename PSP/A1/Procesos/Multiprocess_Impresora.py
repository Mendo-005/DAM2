import time
import multiprocessing as mp
import os


def imprimir_fichero(nombre_fichero):
    time.sleep(5)  
    
    try:
        with open(nombre_fichero, "r") as archivo:
            contenido = archivo.read()
            
        print("----------")
        print(contenido.strip())
        print("----------")
        print(f"process id: {os.getpid()}")
        
    except FileNotFoundError:
        print("----------")
        print("fichero vacío")
        print("----------")
        print(f"process id: {os.getpid()}")


def main():
    nombre_fichero = "fichero.txt"
    
    # Creamos un fichero con permisos de escritura
    with open(nombre_fichero, "w") as archivo:
        
        while True:
            linea = input("Escriba una línea: ")  
            
            # Si la línea es -1 salimos del proceso e imprimimos el id
            if linea == "-1":
                print(f"process id: {os.getpid()}")
                break  
                
            elif linea == "-P":
                # Creamos un proceso que imprime el fichero
                proceso = mp.Process(target=imprimir_fichero, args=(nombre_fichero,))
                proceso.start()
                # No esperamos a que termine el proceso hijo
                
            else:
                # Cualquier otra línea se añade al fichero
                archivo.write(f"{linea}\n")
                archivo.flush()  


# Iniciador del programa
if __name__ == "__main__":
    mp.set_start_method('spawn')
    main()