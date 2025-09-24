import random

def recorre(lista_tuplas):
    """Función que recorre la lista de tuplas y analiza cada elemento"""
    for tupla in lista_tuplas:
        primer_elemento = tupla[0]
        nombre = tupla[1]
        
        if isinstance(primer_elemento, str):
            print(f"El ganador es {nombre}")
        elif isinstance(primer_elemento, int):
            print(f"{nombre} apostó el {primer_elemento} y perdió")
    
    print("\nLista completa:")
    print(lista_tuplas)

def main():
    lista_tuplas = []
    
    # Generar UN ÚNICO número aleatorio para toda la partida
    numero_aleatorio = random.randint(1, 5)
    print(f"Se ha generado un número aleatorio del 1 al 5")
    
    # Bucle para 5 intentos o hasta que pongan -1
    for intento in range(5):
        print(f"\n-Intento {intento + 1}-")
        
        # Pedir nombre
        nombre = input("Ingrese el nombre del jugador: ")
        
        # Pedir número con validación
        while True:
            try:
                num = int(input("Ingrese un número del 1-5 (o -1 para salir): "))
                if num == -1:
                    print("Saliendo del juego...")
                    break
                elif 1 <= num <= 5:
                    break
                else:
                    print("ERROR: Ingrese un número del 1-5 o -1 para salir")
            except ValueError:
                print("ERROR: Ingrese un número válido")
        
        # Si pusieron -1, salir del bucle
        if num == -1:
            break
        
        # Crear tupla según si acertó o falló
        if num == numero_aleatorio:
            tupla = ("Vencedor", nombre)
            print(f"¡{nombre} ha acertado!")
        else:
            tupla = (num, nombre)
        
        # Añadir tupla a la lista
        lista_tuplas.append(tupla)
    
    # Si hay tuplas en la lista, llamar a la función recorre
    if lista_tuplas:
        print(f"\nEl número aleatorio era: {numero_aleatorio}")
        print("\n=== RESULTADOS ===")
        recorre(lista_tuplas)
    else:
        print("No se jugaron partidas.")

if __name__ == "__main__":
    main()