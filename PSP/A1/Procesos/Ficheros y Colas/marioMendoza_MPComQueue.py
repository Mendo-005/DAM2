import os
import sys
import multiprocessing

def escritor(cola, texto):
    """Proceso que escribe en la cola."""
    cola.put(texto)

def lector(cola, fichero):
    """Proceso que lee de la cola y escribe en el archivo."""
    while not cola.empty():
        texto = cola.get()
        with open(fichero, 'a') as f:
            f.write(texto + "\n")

            
    with open(fichero, 'r') as f:
        contenido = f.read()
        print("Contenido del archivo:")
        print(contenido)

def proceso_principal(fichero):
    """Proceso principal que gestiona los otros procesos."""
    while True:
        texto = input("Introduce un texto (-1 para terminar, -- para salir): ")
        
        if texto == "--":
            print("Finalizando...")
            break
        elif texto != "-1":
            # Crear una cola para comunicar los procesos
            cola = multiprocessing.Queue()

            # Crear procesos hijo
            p_escritor = multiprocessing.Process(target=escritor, args=(cola, texto))
            p_escritor.start()
            p_escritor.join() 

            # Crear proceso lector
            p_lector = multiprocessing.Process(target=lector, args=(cola, fichero))
            p_lector.start()
            p_lector.join()  

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Por favor, proporciona el nombre del archivo como argumento.")
        sys.exit(1)

    nombre_fichero = sys.argv[1]
    
    if not os.path.exists(nombre_fichero):
        open(nombre_fichero, 'w').close()  

    proceso_principal(nombre_fichero)
