import multiprocessing
import time

# Función que simula trabajo
def trabajador(id_proceso, cola_comunicacion, lista_compartida):
    
    # 1. Usamos la COLA para decir "estoy empezando"
    cola_comunicacion.put(f"Proceso {id_proceso} arrancando")
    
    time.sleep(0.5)
    
    # 2. Usamos la LISTA COMPARTIDA para guardar un resultado
    # Nota: No hace falta Lock explícito para appends simples en Manager, 
    # pero para operaciones complejas sí sería recomendable.
    lista_compartida.append(id_proceso * 10)
    
    cola_comunicacion.put(f"Proceso {id_proceso} terminó")

def main():
    # Creamos el Manager para la lista compartida
    with multiprocessing.Manager() as manager:
        
        # Lista especial que sí se comparte entre procesos
        lista_compartida = manager.list()
        
        # Cola para mensajes
        cola = multiprocessing.Queue()
        
        procesos = []
        
        # Lanzamos 3 procesos
        for i in range(3):
            p = multiprocessing.Process(
                target=trabajador, 
                args=(i, cola, lista_compartida) # Pasamos la lista del manager
            )
            p.start()
            procesos.append(p)
        
        # Esperamos a que terminen
        for p in procesos:
            p.join()
            
        # Leemos todo lo que hay en la cola
        print("--- Mensajes en la Cola ---")
        while not cola.empty():
            print(cola.get())
            
        # Vemos la lista final
        print("\n--- Lista Compartida Final ---")
        print(lista_compartida) # ¡Aquí sí veremos los datos!

if __name__ == "__main__":
    main()