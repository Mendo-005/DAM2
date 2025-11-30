import queue
import random
import threading
import time


def vigilaHorizonte():
    objetos = ['nada','persona','coche','moto','bicicleta']
    obj = random.randint(-10, 4)
    obj = 0 if obj < 0 else obj    
    return objetos[obj]


cola_objetos = queue.Queue()
lock = threading.Lock()
persona = []
vehic = []
hilos = []

def personas(id):
    total_personas = len(persona)
    print(f"Soy hilo {id} y llevo {total_personas} pesonas")
    
    
def vehiculos(id):
    total_veh = len(vehic)
    print(f"Soy hilo {id} y llevo {total_veh} vehiculos")
    

def camara(cola_objetos, i):
        lectura = cola_objetos.get()
        id = i
        
        if lectura == "persona":
            th_persona = threading.Thread(target=personas, args=(id,))
            with lock:
                persona.append(th_persona)
            
            th_persona.start()
            time.sleep(0.5)

        elif lectura == "moto" or "coche" or "moto":
            th_veh = threading.Thread(target=vehiculos, args=(id,))
            with lock:
                vehic.append(th_veh)
            th_veh.start()
            time.sleep(0.5)

        elif lectura == "finalizar":
            th_veh.join()
            print(f"Total de vehiculos {len(vehiculos)}")
            th_persona.join()
            print(f"Total de personas {len(personas)}")
    
def main():
    print("Camara encendida")
    for i in range(200):
        th = threading.Thread(target=camara, args=(cola_objetos, i))
        th.start()
        deteccion = vigilaHorizonte()
        cola_objetos.put(deteccion)
        hilos.append(th)
    
    for i in range(len(hilos)):
        cola_objetos.put("finalizar")
        
    for th in hilos:
        th.join()
    
    print("Programa finalizao")
    

if __name__ == "__main__":
    main()