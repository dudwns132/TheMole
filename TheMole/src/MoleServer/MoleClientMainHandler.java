package MoleServer;

import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import Mole.Game.HomePanel;
import Mole.Game.InRoom;
import Mole.Game.LeaderBoardFrame;
import Mole.Game.Lobby;
import Mole.Game.LoginForm;
import Mole.Game.MainFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MoleClientMainHandler extends ChannelInboundHandlerAdapter {
	public static MainFrame mainFrame;
	public static HomePanel homePanel;
	public static Lobby lobby;
	public static InRoom inRoom;
	private LinkedList<String> room = new LinkedList<String>();
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		mainFrame = new MainFrame(ctx);
		homePanel = new HomePanel(ctx);
		mainFrame.add(homePanel);
		mainFrame.setVisible(true);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = (String)msg;
		String[] s = readMessage.split(",");

		if (s[0].equals("INFO")) 
			JOptionPane.showMessageDialog(mainFrame , s[1], "계정 정보", JOptionPane.PLAIN_MESSAGE);
		else if (s[0].equals("RANKING")) {
			LeaderBoardFrame leaderBoardFrame = new LeaderBoardFrame(homePanel);
			mainFrame.add(leaderBoardFrame);
			homePanel.setVisible(false);
			leaderBoardFrame.setVisible(true);
			leaderBoardFrame.boardcontent.append(s[1]);
		}
		else if (s[0].equals("ROOMLIST")) {
			for (int i = 1; i < s.length; i ++ )
				room.add(s[i]);
			System.out.println(room);
			lobby = new Lobby(ctx, room);
			mainFrame.add(lobby);
			homePanel.setVisible(false);
			lobby.setVisible(true);
			room.clear();
		}
		else if (s[0].equals("REFRESH")) {
			for (int i = 1; i < s.length; i ++ )
				room.add(s[i]);
			System.out.println(room);
			lobby = new Lobby(ctx, room);
			mainFrame.add(lobby);
			lobby.setVisible(false);
			lobby.setVisible(true);
			room.clear();
			System.out.println("ok");
		}
		else if (s[0].equals("CREAT")) {
			inRoom = new InRoom(ctx, LoginForm.getId(), "");
			mainFrame.add(inRoom);
			lobby.setVisible(false);
			inRoom.setVisible(true);
		}
		else if (s[0].equals("JOIN")) {
			inRoom = new InRoom(ctx, s[1], s[2]);
			inRoom.testStart.setText("준비");
			mainFrame.add(inRoom);
			lobby.setVisible(false);
			inRoom.setVisible(true);
		}
		else if (s[0].equals("GUEST")) {
			inRoom.setVisible(false);
			inRoom = new InRoom(ctx, s[1], s[2]);
			mainFrame.add(inRoom);
			inRoom.setVisible(false);
			inRoom.setVisible(true);
		}
		else if (s[0].equals("SENDMESSAGE")) {
			inRoom.getChatArea().append(s[1] + "\n");
			inRoom.getScroll().getVerticalScrollBar().setValue(inRoom.getScroll().getVerticalScrollBar().getMaximum());
		}
		else if (readMessage.equals("FULL"))
			JOptionPane.showMessageDialog(mainFrame, "풀방");
		
		else if (s[0].equals("BOOM")) {
			for (int i = 1; i < s.length; i ++ )
				room.add(s[i]);
			inRoom.setVisible(false);
			lobby = new Lobby(ctx, room);
			mainFrame.add(lobby);
			lobby.setVisible(false);
			lobby.setVisible(true);
			room.clear();
		}
		else if (s[0].equals("GUESTOUT")) {
			inRoom.setVisible(false);
			inRoom = new InRoom(ctx, s[1], "");
			mainFrame.add(inRoom);
			inRoom.setVisible(false);
			inRoom.setVisible(true);
		}
		else if (s[0].equals("EXITROOM")) {
			inRoom.setVisible(false);
			inRoom = new InRoom(ctx, LoginForm.getId(), "");
			int index = Collections.binarySearch(room, s[1]);
			room.set(index, LoginForm.getId());
			mainFrame.add(inRoom);
			inRoom.setVisible(false);
			inRoom.setVisible(true);
		}
		else if (readMessage.equals("READY"))
			inRoom.ready.setText("준비완료");
		else if (readMessage.equals("CANSLE"))
			inRoom.ready.setText("");
		else 
			ctx.fireChannelRead(readMessage);
	}
}
