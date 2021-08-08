package Mole.Game;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import MoleServer.MoleClientMainHandler;
import io.netty.channel.ChannelHandlerContext;

public class MainFrame extends JFrame{
	public MainFrame(ChannelHandlerContext ctx) {
		CustomCursor();
		setTitle("Mole Game"); // Ÿ��Ʋ
		setSize(800, 600); // �������� ũ��
		setResizable(false); // â�� ũ�⸦ �������� ���ϰ�
		setLocationRelativeTo(null); // â�� ��� ������
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// JFrame�� ���������� ����ǰ� ��.
	//	pack();
		// ������ �̹��� ����
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image icon = kit.getImage("img/moleicon.png");
		setIconImage(icon);
	}

	public void CustomCursor() { // Ŀ���� Ŀ��(���콺 Ŀ��)

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("img/cropcursor.png");
		Point point = new Point(20, 20);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "crop");
		setCursor(cursor);
	}
}
