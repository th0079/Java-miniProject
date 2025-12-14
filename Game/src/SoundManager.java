import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {
	private static Clip bgmClip;
	private static float sfxVolume = 0.0f;
	
	public void playBGM(String name) {
		File soundFile = new File(name);
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			bgmClip = AudioSystem.getClip();
			bgmClip.open(audioInputStream);
			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
			bgmClip.start();
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void pauseBGM() {
		bgmClip.close();
	}
	
	public void playSFX(String name) {
		File soundFile = new File(name);
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			Clip sfxClip = AudioSystem.getClip();
			sfxClip.open(audioInputStream);
			
			FloatControl volumeControl = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
			volumeControl.setValue(sfxVolume);
			
			sfxClip.start();
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void setBGMVolume(int value) {
		float volume;
		FloatControl volumeControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
		if (value <= 0) volume = -80.0f; // 음소거
		else volume = (float) (Math.log10(value / 100.0) * 20.0);
		volumeControl.setValue(volume);
	}
	
	public void setSFXVolume(int value) {
		if (value <= 0) sfxVolume = -80.0f; // 음소거
		else sfxVolume = (float) (Math.log10(value / 100.0) * 20.0);
	}
}
