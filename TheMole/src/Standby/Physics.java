package Standby;

import java.util.LinkedList;

import Mole.Game.Entities.EntityA;
import Mole.Game.Entities.EntityB;

public class Physics { // �浹����, �簢���� �����ȿ� ������ true�� ��ȯ ������ false

	public static boolean Collision(EntityA entA, LinkedList<Mole> entB) { // �ΰ� �� �δ���N , �Ѿ�1���� �δ��� 1�ϳ��� ����
		for (int i = 0; i < entB.size(); i++)
			if (entA.getBounds().intersects(entB.get(i).getBounds())) {
				entB.remove(i);
				return true;
			}
		return false;
	}

	public static boolean Collision(EntityA entA, EntityB entB) { // �ΰ� �� �δ���
		if (entA.getBounds().intersects(entB.getBounds())) {
			return true;
		}
		return false;
	}
	/*
	public static boolean Collision(LinkedList<EntityA> entA, LinkedList<Mole> entB) { // �Ѿ�N �� �δ���N
		for (int i = 0; i < entA.size(); i++)
			for (int j = 0; j < entB.size(); j++)
				if ((entA.get(i).getBounds()).intersects(entB.get(j).getBounds()))
					return true;
		return false;
	}
	*/
}
