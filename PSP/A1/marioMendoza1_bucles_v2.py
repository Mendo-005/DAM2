listaNumeros = []

while True:
    valor = int(input("Escriba un número: "))
    listaNumeros.append(valor)
    if valor == -1:
        break

print(listaNumeros)

pares = 0
impares = 1

for numero in listaNumeros:
    if numero % 2 == 0:
        pares += numero
    else:
        impares *= numero

print("Suma de pares:", pares)
print("Producto de impares:", impares)



