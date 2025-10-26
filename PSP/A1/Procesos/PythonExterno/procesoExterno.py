import multiprocessing as mp
import time
import random

def vigilaHorizonte():
    situacion = ['Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Alarma leve', 'Alarma Media', 'Alarma Grave']
    i = random.randint(0, 9)    
    return(situacion[i])

def camara(id, cola_alarmas, cola_control):
    print(f"Cámara {id} iniciada")
    
    while True:
        # Vigilar el horizonte
        estado = vigilaHorizonte()
        
        # Si no es calma, enviar alarma
        if estado != 'Calma':
            alarma = {'id': id, 'estado': estado}
            cola_alarmas.put(alarma)
            print(f"Cámara {id} - situación detectada: {estado}")
        
        # Verificar órdenes de control
        try:
            orden = cola_control.get_nowait()
            if orden == f"Apágate_{id}":
                print(f"Cámara {id} recibió orden de apagado")
                break
        except:
            pass  # No hay órdenes
        
        time.sleep(0.5)  # Pausa entre vigilancias
    
    print(f"Cámara {id} terminada")

import multiprocessing as mp
import time
from camara import camara

def main():
    print("Iniciando Centro de Vigilancia...")
    
    # Crear las colas
    cola_alarmas = mp.Queue()
    cola_control = mp.Queue()
    
    # Contador de alarmas por cámara
    contador_alarmas = {i: 0 for i in range(1, 6)}
    
    # Crear y lanzar 5 procesos de cámara
    procesos = []
    for id in range(1, 6):
        p = mp.Process(target=camara, args=(id, cola_alarmas, cola_control))
        procesos.append(p)
        p.start()
        print(f"Cámara {id} desplegada")
    
    print("Todas las cámaras desplegadas. Monitoreando...")
    
    try:
        while True:
            try:
                # Recibir alarmas con timeout
                alarma = cola_alarmas.get(timeout=1)
                
                # Mostrar la alarma
                print(f"ALERTA - Cámara {alarma['id']}: {alarma['estado']}")
                
                # Incrementar contador
                contador_alarmas[alarma['id']] += 1
                
                # Si la cámara tiene 4 alarmas, apagarla
                if contador_alarmas[alarma['id']] >= 4:
                    print(f"Cámara {alarma['id']} ha alcanzado 4 alarmas. Enviando orden de apagado...")
                    cola_control.put(f"Apágate_{alarma['id']}")
                    contador_alarmas[alarma['id']] = -999  # Marcar como apagada
                    
            except:
                # Timeout - verificar si todos los procesos han terminado
                procesos_activos = [p for p in procesos if p.is_alive()]
                if not procesos_activos:
                    print("Todas las cámaras han terminado")
                    break
                    
    except KeyboardInterrupt:
        print("\nCerrando centro de vigilancia...")
        
        # Enviar órdenes de apagado a todas las cámaras activas
        for id in range(1, 6):
            cola_control.put(f"Apágate_{id}")
    
    # Esperar a que terminen todos los procesos
    for p in procesos:
        p.join()
    
    print("Centro de Vigilancia cerrado")

if __name__ == "__main__":
    main()