import threading
import random

def vacaciones(name, mes="Ago", dias=15):
    print (f"{name} tomará {dias} días en {mes}")


meses = ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"]
personas = ["Ana", "Manuel", "Maite", "Angel"]
for p in personas:
    mes = random.randint(0,11)
    dias = random.randint(10,20)
    t = threading.Thread(target=vacaciones, args=(p,), kwargs={'mes':meses[mes], 'dias':dias})
    t.start()