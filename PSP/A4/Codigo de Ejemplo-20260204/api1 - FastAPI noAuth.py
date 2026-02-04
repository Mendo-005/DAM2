import fastapi
import uvicorn
from pydantic import BaseModel

app = fastapi.FastAPI()

# Endpoints con GET

# Formato de la llamada URLBASE/info
@app.get("/info")
def info():
    return {"contenido": "Hola, estás en mi API", "autor": "Agustin"}

@app.get("/saludame")
# Formato de la llamada URLBASE/saludame?nombre=Maite&edad=17
def saludo(nombre: str, edad: int):
    return {"contenido": "Hola {}. Tienes {} años".format(nombre, edad), "autor": "Agustin"}

@app.get("/saludamemas/{nombre}/{edad}")
# Formato de la llamada URLBASE/saludamemas/Maite/17
def saludo2(nombre: str, edad: int):
    return {"contenido": "Hola {} ¿Cómo estás a tus {} años?".format(nombre, edad), "autor": "Agustin"}


# Endpoints con PUT, POST.
# Uso de Pydantic para recibir el body de la llamada

# Este es el body de la call
class Item(BaseModel):
    nombre: str
    edad: int
        
gotItems = []

# Formato de la llamada URLBASE/saludame?nombre=Maite&edad=17
@app.put("/addint")
def addInt(item: int):
    print(item)
    it = {'nombre': 'Noname', 'edad':item}
    gotItems.append(it)
    return {"introducido": item}

# Parámetros en el Body
# Formato de la llamada URLBASE/addjson/ y body con el siguiente JSON en formato string {"nombre":"Maite", "edad":56}
@app.post("/addjson")
def addJSON(item: Item):
    gotItems.append(item)
    return {"Recibida":{'nombre': item.nombre, 'edad':item.edad} }
  
# Formato de la llamada URLBASE/getList
@app.get("/getlist")
def returnList():
    return gotItems


if __name__ == "__main__":
    uvicorn.run(app, host="192.168.1.39", port=8090)
