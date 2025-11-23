import os
import pprint

def limpiar(s1):
    s1 = s1.replace("á", "a")
    s1 = s1.replace("é", "e")
    s1 = s1.replace("í", "i")
    s1 = s1.replace("ó", "o")
    s1 = s1.replace("ú", "u")
    s1 = s1.replace("-", "")
    s1 = s1.replace("?", "")
    s1 = s1.replace("¿", "")
    s1 = s1.replace("!", "")
    s1 = s1.replace("¡", "")
    s1 = s1.replace(".", "")
    s1 = s1.replace(",", "")

    return(s1)

def buscar(w, l):
    encontrado = False
    elemento = {}
    for elem in l:
        if elem["palabra"]==w:
            encontrado = True
            elemento = elem
    return (encontrado, elemento)

def construir(w, l):
    encontrado = False
    for elem in l:
        if elem["palabra"]==w:
            encontrado = True
            elem["veces"] += 1
    if not encontrado:
        d = dict(palabra= w, veces= 1)
        l.append(d)
    return(l)

def main():
    listaPalabras = []
    cwd = os.path.dirname(os.path.realpath(__file__))
    f = open(cwd+"/texto.txt", "r", encoding='utf8')
    text = f.read()
    wordList = text.split()
    for word in wordList:
        word = limpiar(word)
        listaPalabras = construir(word, listaPalabras)
    input("Texto Procesado {} palabras encontradas. Pulsa enter para continuar...".format(len(listaPalabras)))
    wtseek = input("Introduce palabra (-1 para terminar): ")
    while wtseek != "-1":
        #wtseek = limpiar(wtseek)
        f,e = buscar(wtseek, listaPalabras)
        if f:
            print("{} aparece {} veces".format(wtseek, e["veces"]))
        else:
            print("{} no aparece".format(wtseek))
        wtseek = input("Introduce palabra (-1 para terminar): ")
    print("bye...")
    pprint.pp(listaPalabras)


if __name__ == "__main__":
    main()
