import threading as th
import random
import time

def centro_control(lista_alarmas, lock_a):
    print("Centro de control activado")
    
    while True:
        # Revisar alarmas
        with lock_a:
            if lista_alarmas:
                alerta = lista_alarmas.pop(0)
                print(f"CENTRO DE CONTROL: Dron {alerta['id']} reporta {alerta['alarma']}")
        
        time.sleep(0.5)

def main():
    # Listas
    lista_alarmas = []
    lista_control = []
    lock_a = th.Lock()
    lock_c = th.Lock()
    drones_activos = []
    dron_id = 1
    
    # Iniciamos el centro de control
    hilo_control = th.Thread(target=centro_control, args=(lista_alarmas, lock_a), daemon=True)
    hilo_control.start()
    
    print("Comandos disponibles:")
    print("  1 - Despegar dron")
    print("  2 - Aterrizar dron")
    print("  3 - Salir")
    
    while True:
        try:
            # Escoger entre las tres opciones
            opcion = input("\n Comandos (1-3): ")
            
            # Activar
            if opcion == "1":
                h_dron = th.Thread(target= dron, args= (dron_id, lista_alarmas,lock_a, lista_control, lock_c))
                h_dron.start()
                drones_activos.append(h_dron)
                dron_id += 1
            
            # Aterrizar
            elif opcion == "2":
                if drones_activos:
                    with lock_c:
                        lista_control.append("Aterrizar")
                    print("Orden Aterrizar")
                    dron_aterrizar = drones_activos.pop(0)
                    dron_aterrizar.join()
                else:
                    print("No hay drones activos")
            # Salir
            elif opcion == "3":
                with lock_c:
                    for _ in drones_activos:
                        lista_control.append("Aterrizar")
                
                for hilo in drones_activos:
                    hilo.join()
                
                print("Sistema cerrado")
            else:
                print("Comando no válido")
                
        except KeyboardInterrupt:
            print("Apagando sistema")
            

# Alarmas y Dornes
def dron(id_dron, lista_alarmas, lock_a, lista_control, lock_c):
    print(f"Dron {id_dron} despegado")
    
    while True:
        # Vigilar horizonte
        alarma = vigilaHorizonte()
        
        # Si hay alarma, reportarla
        if alarma != "Calma":
            with lock_a:
                lista_alarmas.append({'id': id_dron, 'alarma': alarma})
                print(f"Dron {id_dron}: {alarma}")
        
        # Consultar lista de control
        with lock_c:
            if lista_control:
                orden = lista_control.pop(0)
                if orden == "Aterrizar":
                    print(f"Dron {id_dron} aterrizando")
                    break
        time.sleep(2)

def vigilaHorizonte():
    situacion = ['Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Alarma leve', 'Alarma Media', 'Alarma Grave']
    i = random.randint(0, 9)    
    return(situacion[i])

if __name__ == "__main__":
    main()