package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;

/**
 * author: ���ڟ@
 * ������
 * λ�úͳߴ磺
 * int x, y��Ԫ�ص�����λ�á�
 * int w, h��Ԫ�صĿ�Ⱥ͸߶ȡ�
 * ͼ�������
 * ImageIcon icon��Ԫ�ص�ͼ�꣬���ڻ���Ԫ�ء�
 * int sort������������ֶΣ������ڻ���˳�����ײ�����ʹ�á�
 * ״̬��
 * boolean live��Ԫ���Ƿ���ı�־��
 */

public abstract class ElementObj implements Comparable<ElementObj> {

    private int x;
    private int y;
    // private AtomicInteger x = new AtomicInteger(0);
    // private AtomicInteger y = new AtomicInteger(0);
    private int w;
    private int h;
    private ImageIcon icon;
    private int sort;


    private boolean live = true;

    // �����ι��캯��
    public ElementObj() {
    }


    public abstract void showElement(Graphics g);


    public ElementObj createElement(String str) {
        return this;
    }


    public void keyClick(boolean isPressed, int key) {

    }

    public void count(long gameTime) {
    }


    public final void model(long gameTime) {
        updateImage(gameTime); // ��װ��Ҫ���ʱ��
        move(); // �ƶ�
        add(); // �����ӵ�
    }


    protected void add() {
    }


    protected void updateImage(long gameTime) {
    }


    protected void move() {
    }


    public void die(long gameTime) {
    }


    public Rectangle getRectangle() {
        return new Rectangle(x, y, w, h);
        // return new Rectangle(x.get(), y.get(), w, h);
    }


    public boolean collide(ElementObj obj) {
        return this.getRectangle().intersects(obj.getRectangle());
    }


    public int getX() {
        return x;
        // return x.get();
    }

    public void setX(int x) {
        // System.out.println("some motherfucker use setX with " + x);
        // this.x.set(x);
        this.x=x;
    }

    public int getY() {
        // return y.get();
        return y;
    }

    public void setY(int y) {
        // System.out.println("some motherfucker use setY with " + y);
        // this.y.set(y);
        this.y=y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
