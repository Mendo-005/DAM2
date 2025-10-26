import os
import multiprocessing as mp
from camara import camara 
import random

def main():
    # Creamos las colas
    cola_alarma = mp.Queue()
    cola_control = mp.Queue()

    contador_alarmas = {i: 0 for i in range(1, 6)}

    procesos = []
    # Desplegamos 3 procesos
    for id in range(1,6):
        pCamara = mp.Process(target=camara, args= (id, cola_alarma, cola_control))
        procesos.append
        pCamara.start()
    
    print("Todas las camaras monitorean")

    try:
        while True:
            try:

                alarma = cola_alarma.get(timeout=1)
                contador_alarmas[alarma["id"]] += 1

                if contador_alarmas[alarma['id']] >= 4:
                        print(f"Cámara {alarma['id']} ha alcanzado 4 alarmas. Enviando orden de apagado...")
                        cola_control.put(f"Apagar{alarma['id']}")
    
            except:
                 procesos_activos = [p for p in procesos if p.is_alive()]
                 if not procesos_activos:
                    print("Todas las cámaras han terminado")
                    break
    except KeyboardInterrupt:
        print("\nCerrando centro de vigilancia...")
        
        # Enviar órdenes de apagado a todas las cámaras activas
        for id in range(1, 6):
            cola_control.put(f"Apagar{id}")
    
    # Esperar a que terminen todos los procesos
    for p in procesos:
        p.join()
    
    print("Centro de Vigilancia cerrado")

if __name__ == "__main__":
    main()