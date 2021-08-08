package Mole.Game;

import java.applet.AudioClip;
import javax.swing.JApplet;

public class SoundPlayer {

	AudioClip inputSound; // ���ϸ� �׳� ���ϸ� ������ // �����˾Ƽ� ã����

	public SoundPlayer(String SoundFileURL) {
		try {
			inputSound = JApplet.newAudioClip(getClass().getResource(SoundFileURL));
		} catch (Exception e) {
			System.out.println("������ ���о����ϴ�");
		}
	}

	public void startPlay() {
		try {
			inputSound.play();
			// System.out.println("�������");
		} catch (Exception e) {
			System.out.println("���� ����� ���߽��ϴ�");
		}
	}

	public void stopPlayer() {
		inputSound.stop();
	}
}