package com.flower.tool.reboot.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.flower.tool.reboot.R;

import java.lang.reflect.Field;

/**
 * Created by Mao on 16:46 4/22/2015
 */
public class FloatControlView extends LinearLayout {
    private WindowManager windowManager;
    public static int viewWidth;
    public static int viewHeight;

    /**
     * ��¼��ǰ��ָλ������Ļ�ϵĺ�����ֵ
     */
    private float xInScreen;

    /**
     * ��¼��ǰ��ָλ������Ļ�ϵ�������ֵ
     */
    private float yInScreen;
    /**
     * ��¼��ָ����ʱ����Ļ�ϵĺ������ֵ
     */
    private float xDownInScreen;

    /**
     * ��¼��ָ����ʱ����Ļ�ϵ��������ֵ
     */
    private float yDownInScreen;

    /**
     * ��¼��ָ����ʱ��С��������View�ϵĺ������ֵ
     */
    private float xInView;

    /**
     * ��¼��ָ����ʱ��С��������View�ϵ��������ֵ
     */
    private float yInView;

    /**
     * ��¼ϵͳ״̬���ĸ߶�
     */
    private static int statusBarHeight;

    private WindowManager.LayoutParams mParams;

    private OnClickFloatView mListener;

    /**
     * �ϴε����ʱ��
     */
    private long waitTime = 300;

    // �Ѿ����� touch �Ĵ���
    private long touchTime = 0;
    
    
    public FloatControlView(Context context) {
        this(context, null);
    }

    public FloatControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_view,this);
        View view = findViewById(R.id.tv_float);
        //view.setOnLongClickListener(this);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY()-getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY()-getStatusBarHeight();
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen){
                    if(mListener != null){
                        mListener.onClick();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    /**
     * ����С����������Ļ�е�λ�á�
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * ��С�������Ĳ������룬���ڸ���С��������λ�á�
     *
     * @param params
     *            С�������Ĳ���
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }
    /**
     * ���ڻ�ȡ״̬���ĸ߶ȡ�
     *
     * @return ����״̬���߶ȵ�����ֵ��
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    public void setClickListener(OnClickFloatView mListener) {
        this.mListener = mListener;
    }


    public interface  OnClickFloatView{
        public void onClick();
        public void onLongClick();
    }
}
