package com.tedu.controller;

import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.PaoPao;
import com.tedu.element.PaoPaoExplode;
import com.tedu.element.Player;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.manager.MusicPlayer;
import com.tedu.show.GameJFrame;

/**
 * author: ���ڟ@
 *ElementManager em: ��ϷԪ�ع�����ʵ�������ڹ�����Ϸ�еĸ���Ԫ�أ�����ҡ����ˡ����ߵȣ���
 * MusicPlayer bgm: ��Ϸ�������ֲ�����ʵ����
 * int map: ��ǰѡ��ĵ�ͼ��š�
 * int vectory: �����ж���Ϸʤ��״̬�ı�����
 * boolean isOver: �����Ϸ�Ƿ������
 * boolean isPause: �����Ϸ�Ƿ���ͣ��
 * int mode: ��Ϸģʽ�����˻�˫�ˣ���
 * long gameTime: ��Ϸ����ʱ���ʱ����
 */

public class GameThread extends Thread {

    // ����Ԫ�ع�����
    private ElementManager em;
    // ��Ϸ����ʱ�ı�������
    private MusicPlayer bgm;

    // ѡ���ͼ
    private int map = 0; // Ĭ��ֵΪ0����δѡ���ͼ

    public int getMap() {
        return this.map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    private int vectory = 1;// �����Ƿ�ʤ����0����ʤ��

    // ��Ϸ�����Ƿ����
    private boolean isOver = false;

    // ��ͣ�ж���
    private boolean isPause;

    private int mode;


    public GameThread(int map, int mode) {
        this.mode = mode;
        this.map = map;
        em = ElementManager.getManager();
    }

    @Override
    public void run() {
        // super.run();

        // ��չ�����Խ�true��Ϊһ������������Ϸ���̿��ƣ����磺��ͣ��
        // while (true) {
        // ��Ϸ��ʼǰ������������������Ϸ��Դ��������
        gameLoad();

        // ��Ϸ����ʱ
        gameRun();

        // ��Ϸ��������ʱ����Ϸ��Դ����
        gameOver();
    }


    private void gameLoad() {
        // System.out.println("gameLoad");

        // ����ͼƬ��Դ��ע������ڼ��ص�ͼ������֮ǰ
        GameLoad.loadImg();

        // ������Ϸ���֣�������Ч�ͱ������֣�
        GameLoad.loadMusic();

        // ���ص�ͼ��10 �������óɱ������л��ؿ�
        GameLoad.MapLoad(this.map);


        if (mode == 2) {
            GameLoad.loadPlayer("144,144,player1,65,87,68,83,32,1",
                    "528,480,player2,37,38,39,40,10,2");
        } else {
            GameLoad.loadPlayer("144,144,player1,65,87,68,83,32,1");
            GameLoad.loadEnemy("528,480,player2,37,38,39,40,10,2");
        }
        // GameLoad.loadPlayer("144,144,player1,37,38,39,40,17,1");

        // ����NPC...

    }


    private long gameTime = 3L;

    private void gameRun() {

        // ��ʼѭ�����ű������֡���ʱ��������ܻ��λ��
        bgm = GameLoad.musicMap.get("bgm0").setLoop(true);
        // bgm.play();

        // Ԥ����չ��true���Ը�Ϊ���������ڿ��ƹؿ�������
        while (!isOver) {
            // System.out.println("gameRun");
            if (!isPause) {
                // ����Ԫ��ˢ���ƶ�
                Map<GameElement, List<ElementObj>> all = em.getGameElements();
                fclicked();
                moveAndUpdate(all);

                // Լ������һ����������ײ�����������ڶ�������������ײ��һ��
                elementsCollide(GameElement.PLAYER, GameElement.MAPS); // Player���ϰ���
                elementsCollide(GameElement.EXPLODE, GameElement.PLAYER); // ���ݱ�ը��Player
                elementsCollide(GameElement.EXPLODE, GameElement.MAPS); // ���ݱ�ը�͵�ͼ
                elementsCollide(GameElement.EXPLODE, GameElement.ENEMY);
                elementsCollide(GameElement.PLAYER, GameElement.TOOL); // Player�͵���
                elementsCollide(GameElement.ENEMY, GameElement.TOOL);
                elementsCollide(GameElement.EXPLODE, GameElement.PAOPAO); // ���ݱ�ը������
                elementsCollide(GameElement.PLAYER, GameElement.PAOPAO); // player������

                List<ElementObj> enemyList = em.getElementsByKey(GameElement.ENEMY);
                for (ElementObj obj : enemyList) {
                    if (obj instanceof Enemy) {
                        Enemy e = (Enemy) obj;
                        e.automate();
                    }
                }

                gameTime++;

                checkEnd(GameElement.PLAYER);
                checkEnd(GameElement.ENEMY);
            }
            try {
                sleep(35);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkEnd(GameElement ge) {
        List<ElementObj> playerList = ElementManager.getManager().getElementsByKey(ge);
        for (ElementObj player : playerList) {
            Player player1 = (Player) player;
            if (!player1.isLive()) {
                vectory--;
                if (vectory == 0) {
                    String msg = "���밴������Ϸ";
                    if (player1.getPlayerNum() == 1) {
                        if (mode == 2) {
                            msg = "С��ʤ��" + msg;
                        } else {
                            msg = "������" + msg;
                        }
                    } else if (player1.getPlayerNum() == 2) {
                        if (mode == 2) {
                            msg = "С��ʤ��" + msg;
                        } else {
                            msg = "��Ӯ��" + msg;
                        }
                    }
                    if (player1.getPlayerNum() == 1) {
                        Object[] options = { "ȷ��" };
                        JOptionPane.showMessageDialog(null,
                                msg, "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                        isOver = true;
                    }
                    if (player1.getPlayerNum() == 2) {
                        Object[] options = { "ȷ��" };
                        JOptionPane.showMessageDialog(null,
                                msg, "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                        isOver = true;
                    }
                    vectory = 1;
                }
            }
        }
    }


    public void elementsCollide(GameElement eleA, GameElement eleB) {
        List<ElementObj> listA = em.getElementsByKey(eleA);
        List<ElementObj> listB = em.getElementsByKey(eleB);
        // ����͵���֮�����ײ����
        if (eleB == GameElement.TOOL) {
            for (ElementObj g1 : listA) {
                for (ElementObj g2 : listB) {
                    if (g2.collide(g1)) {
                        return;
                    }
                }
            }
        }
        // ���ݱ�ը�͵�ͼ֮�����ײ����
        if (eleA == GameElement.EXPLODE && eleB == GameElement.MAPS) {
            for (ElementObj g1 : listA) {
                for (ElementObj g2 : listB) {
                    if (g1.collide(g2)) {
                        g2.setLive(false);
                        return;
                    }
                }
            }
        }

        // ���ݱ�ը������֮�����ײ����
        if (eleA == GameElement.EXPLODE && eleB == GameElement.PAOPAO) {
            for (ElementObj g1 : listA) {
                for (ElementObj g2 : listB) {
                    if (g1.collide(g2)) {
                        g2.setLive(false);
                        return;
                    }
                }
            }
        }

        // player������֮�����ײ����
        if (eleA == GameElement.PLAYER && eleB == GameElement.PAOPAO) {
            for (ElementObj g1 : listA) {
                for (ElementObj g2 : listB) {
                    if (g1.collide(g2)) {
                    }
                }
            }
        }
        for (ElementObj a : listA) {
            for (ElementObj b : listB) {
                if (a.collide(b)) {

                    // ��ըʱ��ײ��δ��ըʱ�Ĳ���ײ
                    if (eleA.equals(GameElement.EXPLODE)
                            && (eleB.equals(GameElement.PLAYER) ||
                            eleB.equals(GameElement.ENEMY))) {
                        b.die(gameTime);
                        // System.out.println(b);
                        if (b.isLive()) {
                            Player player = (Player) b;
                            player.setBoom(true);
                        }
                    }
                }
            }
        }

    }

    public void moveAndUpdate(Map<GameElement, List<ElementObj>> all) {
        // GameElement.values()�����ط������޷����ȥ
        // ���ص������˳��ʱ��ö�ٱ�������ʱ��˳��

        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            // �������ϲ�Ҫʹ�õ�����foreach���޸����ݻ��׳��쳣
            // for (int i = 0; i < list.size(); i++) {
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);

                // ���Ԫ�ش�������״̬��������Ԫ�ع��������Ƴ�
                if (!obj.isLive()) {
                    // list.remove(i--); // ����

                    // ����һ�����������������п������ܶ����顣���磺������������װ��
                    //
                    obj.die(gameTime); // ������������
                    em.removeElement(i, ge);
                    // list.remove(i);
                    continue;
                }
                obj.count(gameTime);
                obj.model(gameTime);
            }
        }
    }


    private void gameOver() {
        // ���Ԫ�ع�������������ж���
        ElementManager.getManager().clearAll();

        GameJFrame.setJPanel("OverJPanel");
    }


    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }



    private int fclickedTime = 0; // ��˸����

    private void fclicked() {
        List<ElementObj> playerList = ElementManager.getManager().getElementsByKey(GameElement.PLAYER);
        for (ElementObj player : playerList) {
            Player player1 = (Player) player;
            if (player1.isBoom()) {
                if (fclickedTime < 48) {
                    if (player1.getFclickedY() == 48) {
                        player1.setFclickedY(0);
                    } else {
                        player1.setFclickedY(48);
                    }
                    fclickedTime++;
                } else {
                    fclickedTime = 0;
                    player1.setBoom(false);
                }
                player1.model(gameTime);
            }
        }
    }

    public MusicPlayer getBgm() {
        return bgm;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

}
