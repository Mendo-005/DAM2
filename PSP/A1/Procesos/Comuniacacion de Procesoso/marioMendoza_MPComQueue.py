import multiprocessing

def proceso_escritor(cola_comunicacion, texto):
    cola_comunicacion.put(texto)
    print(f"Escrito: {texto}")

def proceso_lector(cola_comunicacion):
    contenido = []
    
    while not cola_comunicacion.empty():
        try:
            elemento = cola_comunicacion.get(timeout=1)
            contenido.append(elemento)
        except:
            break
    
    if contenido:
        print("Contenido de la cola:")
        for i, texto in enumerate(contenido, 1):
            print(f"{i}. {texto}")
        
        for elemento in contenido:
            cola_comunicacion.put(elemento)
    else:
        print("La cola está vacía.")

def main():
    cola_comunicacion = multiprocessing.Queue()
    print("Introduce texto ('--' para salir):")
    
    while True:
        texto = input("> ")
        
        if texto == "--":
            break
        
        if texto == "-1":
            continue
        
        proceso_escr = multiprocessing.Process(target=proceso_escritor, args=(cola_comunicacion, texto))
        proceso_escr.start()
        proceso_escr.join()
        
        proceso_lect = multiprocessing.Process(target=proceso_lector, args=(cola_comunicacion,))
        proceso_lect.start()
        proceso_lect.join()

if __name__ == "__main__":
    multiprocessing.set_start_method('spawn', force=True)
    main()