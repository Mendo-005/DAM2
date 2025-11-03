import threading
import time

# Variable global compartida
variable_compartida = ""
# Lock para controlar el acceso a la variable compartida
lock = threading.Lock()

def escritor_primera_mitad(texto):
    """Hilo que escribe la primera mitad del texto"""
    global variable_compartida
    mitad = len(texto) // 2
    primera_mitad = texto[:mitad]
    
    with lock:
        print(f"Escritor 1: Escribiendo primera mitad: '{primera_mitad}'")
        variable_compartida += primera_mitad
        time.sleep(0.1)  

def escritor_segunda_mitad(texto):
    """Hilo que escribe la segunda mitad del texto"""
    global variable_compartida
    mitad = len(texto) // 2
    segunda_mitad = texto[mitad:]
    
    with lock:
        print(f"Escritor 2: Escribiendo segunda mitad: '{segunda_mitad}'")
        variable_compartida += segunda_mitad
        time.sleep(0.1)

def lector():
    """Hilo que lee y presenta toda la variable compartida"""
    global variable_compartida
    
    with lock:
        print(f"Lector: Contenido completo de la variable: '{variable_compartida}'")
        time.sleep(0.1) 
def main():
    global variable_compartida
    
    while True:
        texto = input("Introduce texto (-1 para salir): ")
        
        if texto == "-1":
            print("Programa terminado.")
            break
        
        # Reiniciar variable compartida para cada iteración
        variable_compartida = ""
        
        # Crear hilos escritores
        hilo_escritor1 = threading.Thread(target=escritor_primera_mitad, args=(texto,))
        hilo_escritor2 = threading.Thread(target=escritor_segunda_mitad, args=(texto,))
        
        # Iniciar hilos escritores
        hilo_escritor1.start()
        hilo_escritor2.start()
        
        # Esperar a que terminen los escritores
        hilo_escritor1.join()
        hilo_escritor2.join()
        
        # Crear y lanzar hilo lector
        hilo_lector = threading.Thread(target=lector)
        hilo_lector.start()
        hilo_lector.join()
        
        print("-" * 50)

if __name__ == "__main__":
    main()