package com.flower.tool.reboot.manager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import com.flower.tool.reboot.view.FloatControlView;

/**
 * Created by Mao on 17:01 4/22/2015
 */
public class MyWindowManager {
    private static FloatControlView mFloatView;
    /**
     * С������View�Ĳ���
     */
    private static WindowManager.LayoutParams smallWindowParams;

    /**
     * ���ڿ�������Ļ����ӻ��Ƴ�������
     */
    private static WindowManager mWindowManager;

    /**
     * ����������
     * @param context ������
     */
    public static void createFloatView(Context context,FloatControlView.OnClickFloatView mListener) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if(mFloatView == null){
            mFloatView = new FloatControlView(context);
            mFloatView.setClickListener(mListener);
            if(smallWindowParams == null){
                smallWindowParams = new WindowManager.LayoutParams();
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatControlView.viewWidth;
                smallWindowParams.height = FloatControlView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
            mFloatView.setParams(smallWindowParams);
            windowManager.addView(mFloatView,smallWindowParams);
        }
    }


    /**
     * ��С����������Ļ���Ƴ���
     *
     * @param context
     *            ����ΪӦ�ó����Context.
     */
    public static void removeSmallWindow(Context context) {
        if (mFloatView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mFloatView);
            mFloatView = null;
        }
    }

    public static boolean isWindowShowing() {
        return mFloatView != null;
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
