import multiprocessing
import os

def proceso_escritor(nombre_fichero, texto):
    with open(nombre_fichero, 'a') as fichero:
        fichero.write(texto + '\n')
    print(f"Escrito: {texto}")

def proceso_lector(nombre_fichero):
    if os.path.exists(nombre_fichero):
        with open(nombre_fichero, 'r') as fichero:
            contenido = fichero.read()
            print("Contenido del archivo:")
            print(contenido)
    else:
        print("El archivo no existe.")

def main():
    nombre_fichero = "comunicacion.txt"
    print("Introduce texto ('--' para salir):")
    
    while True:
        texto = input("> ")
        
        if texto == "--":
            break
        
        if texto == "-1":
            continue
        
        proceso_escr = multiprocessing.Process(target=proceso_escritor, args=(nombre_fichero, texto))
        proceso_escr.start()
        proceso_escr.join()
        
        proceso_lect = multiprocessing.Process(target=proceso_lector, args=(nombre_fichero,))
        proceso_lect.start()
        proceso_lect.join()

if __name__ == "__main__":
    multiprocessing.set_start_method('spawn', force=True)
    main()