import unicodedata

def quitar_acentos(texto):
    # Normaliza el texto y elimina los acentos
    texto_normalizado = unicodedata.normalize('NFD', texto)
    texto_sin_acentos = ''.join(c for c in texto_normalizado if unicodedata.category(c) != 'Mn')
    return texto_sin_acentos

def contar_palabras():
    try:
            with open('texto.txt', 'r', encoding='utf-8') as archivo:
                contenido = archivo.read()

            # Convertir a minúsculas y quitar acentos
            contenido = contenido.lower()
            contenido = quitar_acentos(contenido)

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
                palabraBuscada = input("Introduzca una palabra (o escriba '-1' para terminar): ").lower()
                if palabraBuscada == '-1':
                    break
                palabraBuscada = quitar_acentos(palabraBuscada)
                if palabraBuscada in contador_palabras:
                    print(f"La palabra '{palabraBuscada}' aparece {contador_palabras[palabraBuscada]} veces en el texto")
                else:
                    print(f"La palabra '{palabraBuscada}' no se encontró en el texto")
    
    except Exception as e:
        print(f"Ocurrió un error: {e}")

if __name__ == "__main__":
    contar_palabras()
