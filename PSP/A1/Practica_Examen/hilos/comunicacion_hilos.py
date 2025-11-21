import threading as th

# Variable compartida
texto_global = ""
lock = th.Lock()


def escritor(parte):
    global texto_global
    with lock:
        texto_global += parte


def lector():
    global texto_global
    with lock:
        print("Contenido leído:", texto_global)


def main():
    global texto_global

    while True:
        texto = input("Introduce un texto (-1 para salir): ")

        if texto == "-1":
            print("Fin del programa.")
            break

        # Reset de la variable global antes de escribir
        texto_global = ""

        # Dividir texto
        mitad = len(texto) // 2
        parte1 = texto[:mitad]
        parte2 = texto[mitad:]
        print(f"Parte 1: {parte1}")
        print(f"Parte 2: {parte2}")

        # Crear hilos escritores
        t1 = th.Thread(target=escritor, args=(parte1,))
        t2 = th.Thread(target=escritor, args=(parte2,))

        # Iniciar hilos escritores
        t1.start()
        t2.start()

        # Esperar a que terminen
        t1.join()
        t2.join()

        # Crear e iniciar hilo lector
        t3 = th.Thread(target=lector)
        t3.start()
        t3.join()


if __name__ == "__main__":
    main()
