import psutil
import time

time.sleep(1)
for p in psutil.process_iter():
	#print(p.name())
	if p.is_running():
		if p.name() == "Notepad.exe":
			print("Encontrado Notepad")
			while p.is_running():
				p.suspend()
				print("Suspendo")
				time.sleep(10)
				p.resume()
				print("Reactivo")
				time.sleep(5)


