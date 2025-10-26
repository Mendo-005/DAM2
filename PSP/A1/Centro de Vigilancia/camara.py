import multiprocessing as mp
import time
import random


def vigilaHorizonte():
    situacion = ['Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Alarma leve', 'Alarma Media', 'Alarma Grave']
    i = random.randint(0, 9)    
    return(situacion[i])

def camara(id, cola_alarma, cola_control):
    print(f"Cámara {id} iniciada")
    while True:
        estado = vigilaHorizonte()

        if estado != "Calma":
            alarma = {"id": id , "estado": estado}
            cola_alarma.put(alarma)
            print(f"Cámara {id} situación detectada: {estado}")

        
        try:
            orden = cola_control.get_nowait()
            if orden == f"Apagar{id}":
                print(f"Cámara {id} recibió orden de apagado")
                break
        except:
            pass
    print(f"Cámara {id} terminada")

    