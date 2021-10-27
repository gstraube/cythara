package com.github.cythara.glView;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.github.cythara.Note;


public class MyGLSurfaceView extends GLSurfaceView {
    static boolean setRenderer=false;
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setEGLContextClientVersion(2);
        setRenderer(new MyGLRenderer());
    }
    public void render(){
        requestRender();
    }

}

