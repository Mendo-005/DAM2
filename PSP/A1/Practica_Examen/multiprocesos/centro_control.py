from multiprocessing import Queue
import multiprocessing as mp
import queue
import random
import time

def vigilaHorizonte():
    situacion = ['Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Alarma leve', 'Alarma Media', 'Alarma Grave']
    i = random.randint(0, 9)    
    return(situacion[i])

def camara(id_cam, cola_alarmas, cola_control):
    while True:
        # 1. Comprobamos la cola de control DE FORMA NO BLOQUEANTE
        try:
            mensaje = cola_control.get_nowait() # Intenta leer
            
            # CORRECCIÓN 1: Usar id_cam (la variable local) en lugar de id
            if mensaje == f"Apagar{id_cam}":
                print(f"[Cámara {id_cam}] Recibida orden de apagado. Bye!")
                break
            else:
                # CORRECCIÓN 2 (LÓGICA): Si el mensaje es para otra cámara (ej: Apagar3)
                # y lo he leído yo (Cámara 1), tengo que devolverlo a la cola.
                cola_control.put(mensaje)
                time.sleep(0.1) # Pequeña pausa para dejar que otros lean
                
        except queue.Empty:
            pass # Si la cola está vacía, seguimos trabajando
        
        print(f"Camara {id_cam} encendida")
        alarma = vigilaHorizonte()

        if alarma != "Calma":
            # Creamos el diccionario
            mensaje_alarma = {
                "id": id_cam,
                "nivel": alarma
            }
            cola_alarmas.put(mensaje_alarma)
        time.sleep(0.5)

def main():
    cola_alarmas = Queue()
    cola_control = Queue()
    
    contador_alarmas = {i: 0 for i in range(1, 6)}
    procesos = []

    # CORRECCIÓN 3: El bucle for solo crea los procesos
    for i in range(1,6):
        p = mp.Process(target= camara, args=(i, cola_alarmas, cola_control))
        p.start()
        procesos.append(p)
        
    # CORRECCIÓN 3: El while True va FUERA del for (a la misma altura)
    while True:
        try:
            # Esperamos 1 segundo. Si no hay alarmas, salta al except para ver si hay procesos vivos
            alarma = cola_alarmas.get(timeout=1)
            
            id_recibido = alarma["id"]
            contador_alarmas[id_recibido] += 1
            
            print(f"Alarma recibida de Cámara {id_recibido}. Total: {contador_alarmas[id_recibido]}")

            if contador_alarmas[id_recibido] >= 4:
                # Ojo: aquí había un posible error si se seguían mandando alarmas
                # Verificamos si ya hemos mandado apagar para no spamear la cola (opcional, pero recomendado)
                if contador_alarmas[id_recibido] < 100: 
                    print(f"Cámara {id_recibido} ha alcanzado 4 alarmas. Enviando orden de apagado...")
                    cola_control.put(f"Apagar{id_recibido}")
                    contador_alarmas[id_recibido] = 100 # Marca para no volver a apagarla

        except queue.Empty: # Es mejor capturar queue.Empty que un except genérico
            procesos_activos = [p for p in procesos if p.is_alive()]
            if not procesos_activos:
                print("Todas las cámaras han terminado")
                break
    
    # Esperar a que terminen todos los procesos
    for p in procesos:
        p.join()
    
    print("Centro de Vigilancia cerrado")

if __name__ == "__main__":
    main()