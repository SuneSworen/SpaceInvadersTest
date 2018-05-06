package main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ExplosionSound implements Runnable {
	private static Mixer mixer;
	private static Clip clip;

	
	public ExplosionSound() {
		
	}
	public void run() {
		try {
			Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
			mixer = AudioSystem.getMixer(mixInfos[0]);
			DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
			clip = (Clip)(mixer.getLine(dataInfo));
			URL url = Main.class.getResource("Blast.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			clip.open(audioIn);
			FloatControl volume= (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-20.0f);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		do {
			try {
				Thread.sleep(50);
			}catch(InterruptedException ie) {

			}
		}while(clip.isActive());
	}
	
	public void play() {
		new Thread(this).start();
	}
}
