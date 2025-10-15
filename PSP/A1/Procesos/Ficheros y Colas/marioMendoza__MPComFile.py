import os
import sys
import multiprocessing

def escritor(fichero, texto):
    """Proceso que escribe en el archivo."""
    with open(fichero, 'a') as f:
        f.write(texto + "\n")

def lector(fichero):
    """Proceso que lee y muestra el contenido del archivo."""
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
            # Crear procesos hijo
            p_escritor = multiprocessing.Process(target=escritor, args=(fichero, texto))
            p_escritor.start()
            p_escritor.join()  # Esperamos que termine el proceso de escritura

            # Crear proceso lector
            p_lector = multiprocessing.Process(target=lector, args=(fichero,))
            p_lector.start()
            p_lector.join()  # Esperamos que termine el proceso de lectura

if __name__ == "__main__":
    # El nombre del fichero es el primer parámetro de la línea de comandos
    if len(sys.argv) != 2:
        print("Por favor, proporciona el nombre del archivo como argumento.")
        sys.exit(1)

    nombre_fichero = sys.argv[1]
    
    if not os.path.exists(nombre_fichero):
        open(nombre_fichero, 'w').close()  # Crear el archivo si no existe

    proceso_principal(nombre_fichero)
