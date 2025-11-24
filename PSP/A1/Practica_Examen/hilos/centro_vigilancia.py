import random
import threading as th
import time


def vigilaHorizonte():
    situacion = ['Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Calma', 'Alarma leve', 'Alarma Media', 'Alarma Grave']
    i = random.randint(0, 9)    
    return(situacion[i])

def dron(id, lista_a, lista_c, lock_a, lock_c):
    print(f"Dron {id} desplegado")
    
    while True:
        with lock_c:
            if lista_c:
                orden = lista_c.pop()
                if orden == "Aterrizar":
                    print(f"Dron {id} aterrizando")
                    break
        time.sleep(2)

        alarma = vigilaHorizonte()
        if alarma != "Calma":
            mensaje_alarma = {
                "id": id,
                "nivel": alarma
            }
            with lock_a:
                lista_a.append(mensaje_alarma)
                print(f"Dron {id}: {alarma}")


def centro_control(lista_alarmas, lock_a):
    print("Centro de control activado")
    
    while True:
        # Revisar alarmas
        with lock_a:
            if lista_alarmas:
                alerta = lista_alarmas.pop(0)
                print(f"CENTRO DE CONTROL: Dron {alerta['id']} reporta {alerta['nivel']}")
        
        time.sleep(0.5)

def main():

    lista_alarmas = []
    lock_a = th.Lock()
    lista_control = []
    lock_c = th.Lock()
    drones_activos = []
    dron_id = 1

    # Iniciamos el centro de control
    hilo_control = th.Thread(target=centro_control, args=(lista_alarmas, lock_a), daemon=True)
    hilo_control.start()

    print("/// CENTRO DE VIGILANCIA ///")
    print("Despegar dron (1)")
    print("Aterrizar Dron (2)")
    print("Salir (3)")

    try:
        while True:
            opcion = int(input("Eliga una opcion: "))
            if opcion == 1:
                h = th.Thread(target= dron , args= (dron_id, lista_alarmas,lista_control,lock_a,lock_c)) 
                h.start()
                drones_activos.append(h)
                dron_id += 1

            elif opcion == 2:
                if len(drones_activos) > 0: 
                    with lock_c:
                        lista_control.append("Aterrizar")
                    print("Orden de aterrizaje enviada...")
                    # Eliminamos uno de la lista de activos "lógicamente" (el más antiguo o el último),
                    # asumiendo que uno morirá. Para ser exactos deberíamos limpiar hilos muertos,
                    # pero para este ejercicio basta con sacar uno de la lista para llevar la cuenta.
                    drones_activos.pop() 
                else:
                    print("No hay drones volando.")

            elif opcion == 3:
                print("Aterrizando todos los drones...")
                with lock_c:
                    for h in drones_activos:
                        lista_control.append("Aterrizar")
                for hilo in drones_activos:
                        hilo.join()
        
                print("Todos los drones aterrizaron")
                break
                
            else:
                print("Opcion no valida")
                
    except ValueError:
            print("Error en la entrada de datos.")



if __name__ == "__main__":
    main()