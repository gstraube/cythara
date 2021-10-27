package com.github.cythara.glView;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MyGLRenderer extends TimerTask implements GLSurfaceView.Renderer {
    private com.github.cythara.glView.Triangle mTriangle;
    final static int TRIANGLE_COUNT=36;
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];
    private final float[] translationMback = new float[16];

    private static float averagePitch=0;
    private static float newAveragePitch=0;
    private static float lastAveragePitch=0;
    private final float[] scratch = new float[16];
    public static void setNewAveragePitch(float averagePitch) {
        MyGLRenderer.lastAveragePitch=averagePitch;
        MyGLRenderer.newAveragePitch =averagePitch;
    }
    static void setAveragePitch(float averagePitch){
        MyGLRenderer.averagePitch=averagePitch;
    }
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        mTriangle = new Triangle(TRIANGLE_COUNT);
    }
    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);    // Set the camera position (View matrix)

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        Matrix.translateM(translationMback, 0, vPMatrix, 0, 0, -7.0f, 0);
        float angle = averagePitch*0.1f;
        Matrix.setRotateM(rotationMatrix, 0, angle, 0.0f, 0, -1.0f);
        // Combine the rotation matrix with the projection and camera view
        // Note that the vPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, translationMback, 0, rotationMatrix, 0);

        // Draw triangle
        mTriangle.draw(scratch);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    @Override
    public void run() {
        float pitchDifference=newAveragePitch-lastAveragePitch;
        for (int i=0;i<10;i++) {
            if (lastAveragePitch > 1) {
                try {
                    Thread.sleep(10);
                    com.github.cythara.glView.MyGLRenderer.setAveragePitch(lastAveragePitch += pitchDifference *0.1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                com.github.cythara.glView.MyGLRenderer.setAveragePitch(averagePitch);
            }
        }

    }
}