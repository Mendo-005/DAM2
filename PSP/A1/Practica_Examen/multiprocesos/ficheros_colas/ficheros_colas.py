import multiprocessing

def escritor(titulo, texto):
    with open(titulo, "a", encoding='utf-8') as f:
        f.write(f"{texto}\n")

def lector(titulo):
    with open(titulo, "r") as f:
        contenido = f.read()
        print(f"Texto en el fichero: {contenido}")

def main():
    
    titulo = input("Introduzca el nombre del fichero: ") + ".txt"
    try:
        while (True):
            texto = input("Introduzca un mensaje: ")

            if texto != "--":
                # Proceso Escritor
                p = multiprocessing.Process(target= escritor, args=(titulo, texto))
                p.start()
                p.join()

                # Proceso Lector
                p2 = multiprocessing.Process(target= lector, args=(titulo,))
                p2.start()

            else:
                print("Saliendo del programa...")
                break          
    except IOError:
        print("Error: " + IOError)

if __name__ == "__main__":
    main()