import cv2
from transformers import pipeline
from PIL import Image
import time

def main():
    print("Cargando modelo de Hugging Face... (esto puede tardar unos segundos)")
    
    # 1. Cargamos el pipeline de Hugging Face
    # Usamos un modelo específico para emociones faciales
    try:
        classifier = pipeline(task="image-classification", model="dima806/facial_emotions_image_detection")
    except Exception as e:
        print(f"Error al descargar el modelo: {e}")
        return

    # 2. Cargamos el detector de rostros clásico de OpenCV (Haar Cascade)
    # Esto es ligero y rápido para encontrar DÓNDE está la cara
    face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

    cap = cv2.VideoCapture(0)
    
    if not cap.isOpened():
        print("No se puede abrir la cámara.")
        return

    print("¡Sistema listo! Presiona 'q' para salir.")
    
    # Variables para controlar la velocidad (optimización)
    frame_count = 0
    emotion_label = "Detectando..."
    emotion_score = 0.0

    while True:
        ret, frame = cap.read()
        if not ret:
            break

        # Convertir a escala de grises para el detector de caras (no para emociones)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        
        # Detectar caras
        faces = face_cascade.detectMultiScale(gray, 1.1, 4)

        for (x, y, w, h) in faces:
            # Dibujar rectángulo
            cv2.rectangle(frame, (x, y), (x+w, y+h), (255, 0, 0), 2)

            # Solo analizamos la emoción cada 5 fotogramas
            # Los modelos Transformers son pesados, si lo haces en cada frame irá lento.
            if frame_count % 5 == 0:
                try:
                    # Recortar la cara
                    face_img = frame[y:y+h, x:x+w]
                    
                    # OpenCV usa BGR, Hugging Face necesita RGB. Convertimos.
                    face_rgb = cv2.cvtColor(face_img, cv2.COLOR_BGR2RGB)
                    
                    # Convertir array de numpy a Imagen PIL
                    pil_image = Image.fromarray(face_rgb)

                    # Preguntar al modelo de Hugging Face
                    results = classifier(pil_image)
                    
                    # El resultado es una lista, cogemos el primero (más probable)
                    top_result = results[0]
                    emotion_label = top_result['label']
                    emotion_score = top_result['score']
                    
                except Exception as e:
                    print(f"Error procesando cara: {e}")

            # Escribir la emoción (usamos la última detectada para que no parpadee)
            text = f"{emotion_label}: {emotion_score:.2f}"
            cv2.putText(frame, text, (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (36, 255, 12), 2)

        cv2.imshow('Hugging Face Emotion Detector', frame)
        frame_count += 1

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()