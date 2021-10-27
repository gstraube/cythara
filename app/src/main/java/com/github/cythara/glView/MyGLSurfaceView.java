package com.github.cythara.glView;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

            setEGLContextClientVersion(2);
            setRenderer(new MyGLRenderer());
    }

}

