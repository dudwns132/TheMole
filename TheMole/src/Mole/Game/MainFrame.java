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
		setTitle("Mole Game"); // 타이틀
		setSize(800, 600); // 프레임의 크기
		setResizable(false); // 창의 크기를 변경하지 못하게
		setLocationRelativeTo(null); // 창이 가운데 나오게
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// JFrame이 정상적으로 종료되게 함.
	//	pack();
		// 아이콘 이미지 설정
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image icon = kit.getImage("img/moleicon.png");
		setIconImage(icon);
	}

	public void CustomCursor() { // 커스텀 커서(마우스 커서)

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("img/cropcursor.png");
		Point point = new Point(20, 20);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "crop");
		setCursor(cursor);
	}
}
