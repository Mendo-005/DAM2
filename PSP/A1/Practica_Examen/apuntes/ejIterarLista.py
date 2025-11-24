import random


def recorre(lista):
    for n, i in lista:
        if type(i) is str:
            print("El vencedor es {}".format(n))
        else:
            print("La apuesta {} de {} perdió".format(i,n))
    print(lista)
    
def main():
    jugadores = []
    for i in range(5):
        i = int(input("Introduce num de 1-5: "))
        n = input("Nombre del jugador: ")
        res = random.randint(0, 5) + 1
        if i == res:
            jugadores.append((n, "Vencedor"))
        else:
            jugadores.append((n,i))
    recorre(jugadores)


if __name__ == "__main__":
    main()
