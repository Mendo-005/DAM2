listaDeNumeros = list(range(20))
print(listaDeNumeros)

# Ejercicio 1
print(f"El numero mas alto es: {max(listaDeNumeros)}")

# Ejercicio 2
print(sum(listaDeNumeros)/ len(listaDeNumeros))

# Ejercicio 3
listaDeNumeros.insert(5, "hola")
print(listaDeNumeros)

# Ejercicio  4
subListaDeNumeros = listaDeNumeros[6:12]
print(subListaDeNumeros)

# Ejercicio  5
ultimosCuatroNumeros = listaDeNumeros[::-1][:4]
print(ultimosCuatroNumeros)