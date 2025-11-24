import threading
import queue
import random
import time

# Modelos y precios
CATALOGO = {
    'Golf': 25000, 'Passat': 32000, 'Polo': 19000, 'Tiguan': 35000
}

# Cola compartida (donde se ponen los coches)
cola_coches = queue.Queue()

def fabrica(id_linea):
    """ Productor: Fabrica 50 coches y termina """
    modelos = list(CATALOGO.keys())
    
    for i in range(50):
        # Elegir modelo y precio
        modelo = random.choice(modelos)
        precio = CATALOGO[modelo]
        
        coche = {'modelo': modelo, 'precio': precio}
        
        # Simular tiempo de fabricación
        time.sleep(0.1)
        
        # Poner en la cola
        cola_coches.put(coche)
        print(f"Fabricado (Línea {id_linea}): {modelo}")
    
    print(f"--- Línea {id_linea} ha terminado ---")

def concesionario(id_concesionario):
    """ Consumidor: Intenta vender coches """
    while True:
        try:
            # Intenta sacar un coche. Espera máximo 1 segundo.
            coche = cola_coches.get(timeout=1)
            
            # Si el coche es None, significa que debemos cerrar
            if coche is None:
                break
            
            # Vender el coche
            print(f"Vendido (Concesionario {id_concesionario}): {coche['modelo']} por {coche['precio']}")
            time.sleep(0.2)
            
        except queue.Empty:
            # Si pasa 1 segundo y no hay coches
            print(f"Concesionario {id_concesionario}: No encuentro coches...")

def main():
    # Pedir datos al usuario
    n_productores = int(input("Número de líneas de producción: "))
    n_consumidores = int(input("Número de concesionarios: "))
    
    hilos_fabricas = []

    # 1. Crear y arrancar FABRICAS
    for i in range(n_productores):
        t = threading.Thread(target=fabrica, args=(i+1,))
        hilos_fabricas.append(t)
        t.start()

    # 2. Crear y arrancar CONCESIONARIOS
    hilos_concesionarios = []
    for i in range(n_consumidores):
        t = threading.Thread(target=concesionario, args=(i+1,))
        hilos_concesionarios.append(t)
        t.start()

    # 3. Esperar a que las fabricas terminen sus 50 coches
    for t in hilos_fabricas:
        t.join()

    # 4. Avisar a los concesionarios que terminen 
    for i in range(n_consumidores):
        cola_coches.put(None)
    
    # 5. Esperar a que los concesionarios cierren
    for t in hilos_concesionarios:
        t.join()

    print("Fin del programa.")

if __name__ == "__main__":
    main()