def contar_palabras():
    try:
            with open('texto.txt', 'r', encoding='utf-8') as archivo:
                contenido = archivo.read()

            # Convertir a minúsculas 
            contenido = contenido.lower()

            # Dividir el texto en palabras 
            words = contenido.split()

            # Diccionario 
            contador_palabras = {}

            for palabra in words:
                if palabra in contador_palabras:
                    contador_palabras[palabra] += 1
                else:
                    contador_palabras[palabra] = 1
            print(contador_palabras)
            while True:
                palabraBuscada = input("Introduzca una palabra (o escriba 'salir' para terminar): ").lower()
                if palabraBuscada == -1:
                    break
                if palabraBuscada in contador_palabras:
                    print(f"La palabra '{palabraBuscada}' aparece {contador_palabras[palabraBuscada]} veces en el texto")
                else:
                    print(f"La palabra '{palabraBuscada}' no se encontró en el texto")
    
    except Exception as e:
        print(f"Ocurrió un error: {e}")

if __name__ == "__main__":
    contar_palabras()
