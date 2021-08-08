package MoleServer;

import javax.swing.JOptionPane;

import Mole.Game.GameStart;
import Mole.Game.HumanUI;
import Mole.Game.LoginForm;
import Mole.Game.MoleInHumanPerformance;
import Mole.Game.MoleUI;
import Mole.Game.vegetableThread;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MoleClientGameHandler extends ChannelInboundHandlerAdapter {
	public static MoleUI moleUI;
	public static HumanUI humanUI;
	private GameStart gameStart;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = (String) msg;
		String[] s = readMessage.split(",");

		if (s[0].equals("MOLESTART")) {
			MoleClientMainHandler.inRoom.setVisible(false);
			gameStart = new GameStart(s[3], s[4]);
			gameStart.molePlayer.setText(s[3]);
			gameStart.humanPlayer.setText(s[4]);
			MoleClientMainHandler.mainFrame.add(gameStart);
			gameStart.setVisible(true);
			if (MoleClientMainHandler.homePanel.getPlayStatus() == true)
				MoleClientMainHandler.homePanel.getSoundToPlay().pause();
			else
				MoleClientMainHandler.homePanel.setPlayStatus(true);
			moleUI = new MoleUI(ctx, s[1], Integer.parseInt(s[5]), Integer.parseInt(s[6]), Integer.parseInt(s[7]),
					Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[10]));
			MoleClientMainHandler.mainFrame.add(moleUI);
			Thread.sleep(2000);
			gameStart.setVisible(false);
			moleUI.setVisible(true);
		}

		else if (s[0].equals("HUMANSTART")) {
			MoleClientMainHandler.inRoom.setVisible(false);
			GameStart gameStart = new GameStart(s[3], s[4]);
			gameStart.humanPlayer.setText(s[3]);
			gameStart.molePlayer.setText(s[4]);
			MoleClientMainHandler.mainFrame.add(gameStart);
			gameStart.setVisible(true);
			if (MoleClientMainHandler.homePanel.getPlayStatus() == true)
				MoleClientMainHandler.homePanel.getSoundToPlay().pause();
			else
				MoleClientMainHandler.homePanel.setPlayStatus(true);
			humanUI = new HumanUI(ctx, s[1], Integer.parseInt(s[5]), Integer.parseInt(s[6]), Integer.parseInt(s[7]),
					Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[10]));
			MoleClientMainHandler.mainFrame.add(humanUI);
			Thread.sleep(2000);
			gameStart.setVisible(false);
			humanUI.setVisible(true);
		}

		for (int i = 0; i < s.length; i++) {
			if (s[i].equals("GAMECOUNT")) {
				if (moleUI == null) {
					humanUI.setSecond(humanUI.getSecond() - 1);
					humanUI.setDdSecond(humanUI.getdFormat().format(humanUI.getSecond()));
					humanUI.setDdMinute(humanUI.getdFormat().format(humanUI.getMinute()));
					humanUI.getCounterLabel().setText(humanUI.getDdMinute() + ":" + humanUI.getDdSecond());
					if (humanUI.getSecond() == 0 && humanUI.getMinute() == 0) {
						JOptionPane.showMessageDialog(null, "인간 승리!!", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[HUMANWIN]," + humanUI.getName() + "," + LoginForm.getId() + ",");
						humanUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						humanUI.setSecond(0);
						humanUI.setMinute(3);
						if (humanUI.getMusicStatus() == true)
							try {
								humanUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
					if (humanUI.getSecond() == -1) {
						humanUI.setSecond(59);
						humanUI.setMinute(humanUI.getMinute() - 1);

						humanUI.setDdSecond(humanUI.getdFormat().format(humanUI.getSecond()));
						humanUI.setDdMinute(humanUI.getdFormat().format(humanUI.getMinute()));
						humanUI.getCounterLabel().setText(humanUI.getDdMinute() + ":" + humanUI.getDdSecond());
					}
				} else {
					moleUI.setSecond(moleUI.getSecond() - 1);
					System.out.println(moleUI.getSecond());
					moleUI.setDdSecond(moleUI.getdFormat().format(moleUI.getSecond()));
					moleUI.setDdMinute(moleUI.getdFormat().format(moleUI.getMinute()));
					moleUI.getCounterLabel().setText(moleUI.getDdMinute() + ":" + moleUI.getDdSecond());
					if (moleUI.getSecond() == 0 && moleUI.getMinute() == 0) {
						JOptionPane.showMessageDialog(null, "인간 승리!!", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[MOLELOSE]," + LoginForm.getId() + ",");
						moleUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						moleUI.setSecond(0);
						moleUI.setMinute(3);
						if (moleUI.getMusicStatus() == true)
							try {
								moleUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
					if (moleUI.getSecond() == -1) {
						moleUI.setSecond(59);
						moleUI.setMinute(moleUI.getMinute() - 1);

						moleUI.setDdSecond(moleUI.getdFormat().format(moleUI.getSecond()));
						moleUI.setDdMinute(moleUI.getdFormat().format(moleUI.getMinute()));
						moleUI.getCounterLabel().setText(moleUI.getDdMinute() + ":" + moleUI.getDdSecond());
					}
				}
			} else if (s[i].equals("RIGHT")) {
				if (moleUI == null)
					humanUI.getHuman().humanMove(s[i]);
				else
					moleUI.getMoleInHumanPerformance().moleInHumanMove(s[i]);
			} else if (s[i].equals("LEFT")) {
				if (moleUI == null)
					humanUI.getHuman().humanMove(s[i]);
				else
					moleUI.getMoleInHumanPerformance().moleInHumanMove(s[i]);
			} else if (s[i].equals("LEFTSTOP")) {
				if (moleUI == null)
					humanUI.getHuman().humanMove(s[i]);
				else
					moleUI.getMoleInHumanPerformance().moleInHumanMove(s[i]);
			} else if (s[i].equals("RIGHTSTOP")) {
				if (moleUI == null)
					humanUI.getHuman().humanMove(s[i]);
				else
					moleUI.getMoleInHumanPerformance().moleInHumanMove(s[i]);
			} else if (s[i].equals("v1EAT")) {
				if (moleUI == null) {
					humanUI.getV1().setBounds(0, 0, 0, 0);
					humanUI.getV1().setVisible(false);
					humanUI.setVegcount(humanUI.getVegcount() - 1);
					humanUI.getVegCountLabel().setText(humanUI.getVegcount() + "");
					if (humanUI.getVegcount() == 0) {
						JOptionPane.showMessageDialog(null, "두더지 승리", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[HUMANLOSE]," + LoginForm.getId() + ",");
						humanUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (humanUI.getMusicStatus() == true)
							try {
								humanUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();

							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}

				} else {
					moleUI.getV1().setBounds(0, 0, 0, 0);
					moleUI.getV1().setVisible(false);
					moleUI.setVegcount(moleUI.getVegcount() - 1);
					moleUI.getVegcountLabel().setText(moleUI.getVegcount() + "");
					if (moleUI.getVegcount() == 0) {
						JOptionPane.showMessageDialog(null, "두더지 승리", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[MOLEWIN]," + moleUI.getName() + "," + LoginForm.getId() + ",");
						moleUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (moleUI.getMusicStatus() == true)
							try {
								moleUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();

							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				}
			} else if (s[i].equals("v1")) {
				int x = Integer.parseInt(s[i + 1]);
				int y = Integer.parseInt(s[i + 2]);
				if (moleUI == null) {
					humanUI.getV1().setIcon(vegetableThread.vegetables[y]);
					humanUI.getV1().setBounds(x, 240, 32, 32);
					humanUI.getV1().setVisible(true);
				} else {
					moleUI.getV1().setIcon(vegetableThread.vegetables[y]);
					moleUI.getV1().setBounds(x, 240, 32, 32);
					moleUI.getV1().setVisible(true);
				}
			} else if (s[i].equals("v2EAT")) {
				if (moleUI == null) {
					humanUI.getV2().setBounds(0, 0, 0, 0);
					humanUI.getV2().setVisible(false);
					humanUI.setVegcount(humanUI.getVegcount() - 1);
					humanUI.getVegCountLabel().setText(humanUI.getVegcount() + "");
					if (humanUI.getVegcount() == 0) {
						JOptionPane.showMessageDialog(null, "두더지 승리", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[HUMANLOSE]," + LoginForm.getId() + ",");
						humanUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (humanUI.getMusicStatus() == true)
							try {
								humanUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();

							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				} else {
					moleUI.getV2().setBounds(0, 0, 0, 0);
					moleUI.getV2().setVisible(false);
					moleUI.setVegcount(moleUI.getVegcount() - 1);
					moleUI.getVegcountLabel().setText(moleUI.getVegcount() + "");
					//moleUI.getV1().setVisible(false);
					//moleUI.getV1().setBounds(0, 0, 0, 0);
				//	moleUI.setVegcount(moleUI.getVegcount() - 1);
				//	moleUI.getVegcountLabel().setText(moleUI.getVegcount() + "");
					if (moleUI.getVegcount() == 0) {
						JOptionPane.showMessageDialog(null, "두더지 승리", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[MOLEWIN]," +  moleUI.getName() + "," + LoginForm.getId() + ",");
						moleUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (moleUI.getMusicStatus() == true)
							try {
								moleUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();

							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				}
			} else if (s[i].equals("v2")) {
				int x = Integer.parseInt(s[i + 1]);
				int y = Integer.parseInt(s[i + 2]);
				if (moleUI == null) {
					humanUI.getV2().setIcon(vegetableThread.vegetables[y]);
					humanUI.getV2().setBounds(x, 240, 32, 32);
					humanUI.getV2().setVisible(true);
				} else {
					moleUI.getV2().setIcon(vegetableThread.vegetables[y]);
					moleUI.getV2().setBounds(x, 240, 32, 32);
					moleUI.getV2().setVisible(true);
				}
			} else if (s[i].equals("v3EAT")) {
				if (moleUI == null) {
					humanUI.getV3().setBounds(0, 0, 0, 0);
					humanUI.getV3().setVisible(false);
					humanUI.setVegcount(humanUI.getVegcount() - 1);
					humanUI.getVegCountLabel().setText(humanUI.getVegcount() + "");
					if (humanUI.getVegcount() == 0) {
						JOptionPane.showMessageDialog(null, "두더지 승리", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[HUMANLOSE]," + LoginForm.getId() + ",");
						humanUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (humanUI.getMusicStatus() == true)
							try {
								humanUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();

							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				} else {
					moleUI.getV3().setBounds(0, 0, 0, 0);
					moleUI.getV3().setVisible(false);
					moleUI.setVegcount(moleUI.getVegcount() - 1);
					moleUI.getVegcountLabel().setText(moleUI.getVegcount() + "");
				//	moleUI.getV1().setVisible(false);
				//	moleUI.getV1().setBounds(0, 0, 0, 0);
				//	moleUI.setVegcount(moleUI.getVegcount() - 1);
				//	moleUI.getVegcountLabel().setText(moleUI.getVegcount() + "");
					if (moleUI.getVegcount() == 0) {
						JOptionPane.showMessageDialog(null, "두더지 승리", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[MOLEWIN]," + moleUI.getName() + "," + LoginForm.getId() + ",");
						moleUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (moleUI.getMusicStatus() == true)
							try {
								moleUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();

							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				}
			} else if (s[i].equals("v3")) {
				int x = Integer.parseInt(s[i + 1]);
				int y = Integer.parseInt(s[i + 2]);
				if (moleUI == null) {
					humanUI.getV3().setIcon(vegetableThread.vegetables[y]);
					humanUI.getV3().setBounds(x, 240, 32, 32);
					humanUI.getV3().setVisible(true);
				} else {
					moleUI.getV3().setIcon(vegetableThread.vegetables[y]);
					moleUI.getV3().setBounds(x, 240, 32, 32);
					moleUI.getV3().setVisible(true);
				}
			} else if (s[i].equals("ITEM")) {
				int a = Integer.parseInt(s[i + 1]);
				int b = Integer.parseInt(s[i + 2]);
				if (moleUI == null) {
					humanUI.getI1().setTimerstop(false);
					humanUI.getI2().setTimerstop(false);
					humanUI.getI1().setBounds(a, 270, 40, 40);
					humanUI.getI1().setVisible(true);
					humanUI.getI2().setBounds(b, 270, 40, 40);
					humanUI.getI2().setVisible(true);
				} else {
					moleUI.getI1().setBounds(a, 270, 40, 40);
					moleUI.getI1().setVisible(true);
					moleUI.getI2().setBounds(b, 270, 40, 40);
					moleUI.getI2().setVisible(true);
				}
			} else if (s[i].equals("MOLEITEM1EAT"))
				if (moleUI == null)
					humanUI.getI1().setVisible(false);
				else {
					moleUI.getI1().setBounds(0, 0, 0, 0);
					moleUI.getI1().setVisible(false);
				}
			else if (s[i].equals("MOLEITEM2EAT"))
				if (moleUI == null)
					humanUI.getI2().setVisible(false);
				else {
					moleUI.getI2().setBounds(0, 0, 0, 0);
					moleUI.getI2().setVisible(false);
				}
			else if (s[i].equals("HUMANITEM1EAT")) {
				if (moleUI == null) {
					humanUI.getI1().setTimerstop(true);
					humanUI.getI1().setVisible(false);
					humanUI.getHuman().setHumanItem(true);
				} else {
					moleUI.getI1().setVisible(false);
					moleUI.getI1().setBounds(0, 0, 0, 0);
				}
			} else if (s[i].equals("HUMANITEM2EAT")) {
				if (moleUI == null) {
					humanUI.getI2().setTimerstop(true);
					humanUI.getI2().setVisible(false);
					humanUI.getHuman().setHumanItem(true);
				} else {
					moleUI.getI2().setVisible(false);
					moleUI.getI2().setBounds(0, 0, 0, 0);
				}
			} else if (s[i].equals("HUMANSPEEDUP")) {
				if (moleUI == null)
					humanUI.getHuman().setHumanspeed(7);
				else
					moleUI.getMoleInHumanPerformance().setHumanspeed(7);
			}
			else if (s[i].equals("HUMANSPEEDDOWN")) {
				if (moleUI == null)
					humanUI.getHuman().setHumanspeed(5);
				else
					moleUI.getMoleInHumanPerformance().setHumanspeed(5);
			}
			else if (s[i].equals("MOLEMOVE")) {
				int x = Integer.parseInt(s[i + 1]);
				int y = Integer.parseInt(s[i + 2]);
				humanUI.moleMessage(s[i + 3], x, y);
			} else if (s[i].equals("MOLETRAP")) {
				if (moleUI == null)
					humanUI.getHuman().setHumanspeed(0);
				else
					moleUI.getMoleInHumanPerformance().setHumanspeed(0);
			}
			else if (s[i].equals("MOLETRAPSTOP")) {
				if (moleUI == null)
					humanUI.getHuman().setHumanspeed(5);
				else
					moleUI.getMoleInHumanPerformance().setHumanspeed(5);
			}
			else if (s[i].equals("HUMANTRAP"))
				moleUI.setMoleKill(false);
			else if (s[i].equals("HUMANTRAPSTOP"))
				moleUI.setMoleKill(true);
			else if (s[i].equals("SNAKE")) {
				int status = Integer.parseInt(s[i + 1]);
				if (moleUI == null)
					humanUI.makeSnake(status);
				else
					moleUI.makeSnake(status);
			}else if (s[i].equals("SNAKEMOVE")) {
				int moveCount = Integer.parseInt(s[i + 1]);
				if (moleUI == null)
					humanUI.getSnake().move(moveCount);
				else
					moleUI.getSnake().move(moveCount);
			} else if (s[i].equals("SNAKEDIE")) {
				if (moleUI == null) {
					humanUI.getSnake().snakeDie();
					humanUI.setIsSnake(false);
					if (humanUI.getHuman().getB() != null) {
						humanUI.getHuman().getB().setVisible(false);
						humanUI.remove(humanUI.getHuman().getB());
					}
				}
				else {
					moleUI.getSnake().snakeDie();
					moleUI.setIsSnake(false);
					if (moleUI.getMoleInHumanPerformance().getB() != null) {
						moleUI.getMoleInHumanPerformance().getB().setVisible(false);
						moleUI.remove(moleUI.getMoleInHumanPerformance().getB());
					}
				}
			} else if (s[i].equals("BULLET")) {
				int direction = Integer.parseInt(s[i + 1]);
				int status = Integer.parseInt(s[i + 2]);
				if (humanUI == null)
					moleUI.getMoleInHumanPerformance().bullet(moleUI.getMoleInHumanPerformance().getX(), direction, status);
				else {
					humanUI.getHuman().bullet(humanUI.getHuman().getX(), direction, status);
					humanUI.getHuman().setBulletCount(humanUI.getHuman().getBulletCount()-1);
					humanUI.getBulletLabel().setText(humanUI.getHuman().getBulletCount() + "");
				}
			} else if (s[i].equals("MOLEDIE")) {
				if (moleUI == null) {
					humanUI.moleDie(s[i + 1]);
					if (humanUI.getHuman().getB() != null) {
						humanUI.getHuman().getB().setVisible(false);
						humanUI.remove(humanUI.getHuman().getB());
					}
					if (humanUI.getMoleCount() == 0) {
						JOptionPane.showMessageDialog(null, "인간 승리", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[HUMANWIN]," + humanUI.getName() + "," + LoginForm.getId() + ",");
						humanUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (humanUI.getMusicStatus() == true)
							try {
								humanUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				} else {
					moleUI.moleDie(s[i + 1]);
					if (moleUI.getMoleInHumanPerformance().getB() != null) {
						moleUI.getMoleInHumanPerformance().getB().setVisible(false);
						moleUI.remove(moleUI.getMoleInHumanPerformance().getB());
					}
					if (moleUI.getMoleCount() == 0) {
						JOptionPane.showMessageDialog(null, "인간 승리", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[MOLELOSE]," + LoginForm.getId() + ",");
						moleUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (moleUI.getMusicStatus() == true)
							try {
								moleUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				}
			} else if (s[i].equals("MINUSLIFE")) {
				if (moleUI == null) {
					humanUI.getSnake().snakeDie();
					humanUI.setIsSnake(false);
					humanUI.getHuman().setHumanLife(humanUI.getHuman().getHumanLife()-1);
					humanUI.getLifeLabel().setText(humanUI.getHuman().getHumanLife() + "");
						
					if (humanUI.getHuman().getHumanLife() == 0) {
						JOptionPane.showMessageDialog(null, "인간 패배", "Result", JOptionPane.PLAIN_MESSAGE);
						ctx.writeAndFlush("[HUMANLOSE]," + LoginForm.getId() + ",");
						humanUI.setVisible(false);
						if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
							MoleClientMainHandler.inRoom.testStart.setText("준비");
						MoleClientMainHandler.inRoom.ready.setText("");
						MoleClientMainHandler.inRoom.setVisible(true);
						if (humanUI.getMusicStatus() == true)
							try {
								humanUI.getSoundToPlay().pause();
								MoleClientMainHandler.homePanel.getSoundToPlay().play();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				}
			 else {
				moleUI.getSnake().snakeDie();
				moleUI.setIsSnake(false);
				moleUI.getMoleInHumanPerformance().setHumanlife(moleUI.getMoleInHumanPerformance().getHumanlife()-1);
				moleUI.getLifeLabel().setText(moleUI.getMoleInHumanPerformance().getHumanlife() + "");
				if (moleUI.getMoleInHumanPerformance().getHumanlife() == 0) {
					JOptionPane.showMessageDialog(null, "인간 패배", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[MOLEWIN]," + moleUI.getName() + "," + LoginForm.getId() + ",");
					moleUI.setVisible(false);
					if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
						MoleClientMainHandler.inRoom.testStart.setText("준비");
					MoleClientMainHandler.inRoom.ready.setText("");
					MoleClientMainHandler.inRoom.setVisible(true);
					if (moleUI.getMusicStatus() == true)
						try {
							moleUI.getSoundToPlay().pause();
							MoleClientMainHandler.homePanel.getSoundToPlay().play();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			}
		}
		}
	}
}
