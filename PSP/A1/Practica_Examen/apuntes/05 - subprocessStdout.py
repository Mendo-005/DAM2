import subprocess
import time

# Vigilo cada 1 segundos si un servicio Web está arriba
# codecs: https://docs.python.org/3/library/codecs.html#standard-encodings

servicio = "www.madrid.org"
error = False

while not error:
    try:
        subproc = subprocess.Popen(["ping", servicio,"-n","1"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        (standardout, standarderr) = subproc.communicate()
        salidaStr = standardout.decode("cp858")
        if standarderr is not None:
            salidaErr = standarderr.decode("cp858")
    except:
        error = True
        salidaStr = "Error en comando"    
    print(salidaStr)
    print("......")
    time.sleep(1)