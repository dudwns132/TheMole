package MoleServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import DB.DBConnection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;

public class LoginServer {
	public LoginServer() {
	}

	public static void login(String id, String pw, ChannelHandlerContext ctx) {
		String password = null;
		try {
			Connection con = DBConnection.makeConnection(); // DB����
			PreparedStatement pstmt = con.prepareStatement("select *from gamer where id like ?");
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			;
			while (rs.next()) { // �����ͺ��̽� ���̺� ���� ���� Ȯ��
				password = rs.getString("passwords");
			}
			if (pw.equals(password)) {
				MoleServerMainHandler.onlineId.put(ctx.channel(), id);
				ctx.writeAndFlush("LOGIN");
			} else
				ctx.writeAndFlush("LOGINFAIL");

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void duplicateCheck(String id, ChannelHandlerContext ctx) {
		ArrayList<String> idList = new ArrayList<String>();
		try {
			Connection con = DBConnection.makeConnection(); // DB����
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM gamer");
			ResultSet rs = pstmt.executeQuery();
			;
			while (rs.next()) { // �����ͺ��̽� ���̺� ���� ���� Ȯ��
				idList.add(rs.getString("id"));
			}
			int index = Collections.binarySearch(idList, id);
			if (index >= 0)
				ctx.writeAndFlush("DUPLICATE"); // ���̵��ߺ�
			else
				ctx.writeAndFlush("NODUPLICATE"); // ���̵� �ߺ��ƴ�(��������)

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void signUpComplete(String id, String pw, ChannelHandlerContext ctx) {
		try {
			Connection con = DBConnection.makeConnection(); // DB����
			String sql = String.format("INSERT INTO gamer VALUES('%s','%s',0,0,0,1000);", id, pw);
			PreparedStatement pstmt = con.prepareStatement(sql);
			int i = pstmt.executeUpdate();

			if (i == 1) {
				ctx.writeAndFlush("SIGNUP");
			}
			pstmt.close();
			con.close();

		} catch (Exception a) {
		}
	}
}
