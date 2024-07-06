package com.tedu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;

/**
 * author: ���ڟ@
 * ��ҿ��Ƶ�̹�˿����ƶ��������
 * �з�̹���Զ��ƶ����������̹�˶�ս��
 * ��Ϸ��������ؿ���ÿ���ؿ��в�ͬ�����ĵз�̹�ˡ�
 * �����Ҫ�������ез�̹���Խ�����һ�ء�
 */

public class GameListener implements KeyListener {
    private ElementManager em = ElementManager.getManager();

    // ��¼���µķ����
    private Set<Integer> set = new HashSet<>();
    //��¼��ǰ��ͣ״̬
    private boolean isPause=false;
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }


    @Override
    public void keyPressed(KeyEvent e) {
//		System.out.println("keyPressed: " + e.getKeyCode());
        int key = e.getKeyCode();
        if (set.contains(key)) {
            // ��ֹ��סһ����������ţ���������޸�״̬
            // ֮������set���ϣ�����Ϊ���ܲ�ֻ��4������������ܻ���������
            return;
        }
        set.add(key);

        // �õ���Ҽ���
        List<ElementObj> playList = em.getElementsByKey(GameElement.PLAYER);
        for (ElementObj obj : playList) {
            obj.keyClick(true, key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ����debug��������ӳ�
        System.out.println("keyReleased: " + e.getKeyCode());

        int key = e.getKeyCode();
        if (!set.contains(key)) { // ��  ����
            return;
        }
        set.remove(key);

        List<ElementObj> playList = em.getElementsByKey(GameElement.PLAYER);
        for (ElementObj obj : playList) {
            obj.keyClick(false, key);
        }
        if(key==80)
        {
            isPause=!isPause;
            ((GameThread) GameJFrame.gj.getThread()).setPause(isPause);
        }
    }

}
