package es.ciudadescolar;

import marytts.modules.synthesis.VoiceManager;

public class VoiceTest {
    public static void main(String[] args) {
        var voice = VoiceManager.getInstance().getAvailableVoices().iterator().next();
        var synthesizer = VoiceManager.getInstance().getVoice(voice.getName());
        synthesizer.allocate();
        synthesizer.speak("Hola, esto es una prueba de síntesis de voz en Java.");
    }
}
