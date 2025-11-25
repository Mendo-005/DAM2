import multiprocessing as mp
import subprocess
import time


def escritor(titulo):
    with open(titulo, "a") as f:
        subprocess.Popen("Notepad.exe", titulo)

def lector(titulo,palabra_clave):
    try:
        while True:
            with open(titulo, "r") as f:
                contenido = f.read()
                if palabra_clave in contenido:
                    print(f"Se ha encontrado la palabra clave: {palabra_clave}" )
                    time.sleep(1)
                else:
                    print(f"No se ha encontrado la palabra clave" )
                    time.sleep(1)
    except KeyboardInterrupt:
        print("Error: Texto introducido")
        
def main():
    
    titulo = input("Introduzca el nombre del fichero: ") + ".txt"
    palabra_clave = input("Intoduzca palabra clave: ")
    
    p = mp.Process(target= escritor, args= (titulo,))
    p.start()
    
    p2 = mp.Process(target= lector, args= (titulo, palabra_clave))
    p2.start()


if __name__ == "__main__":
    main()  