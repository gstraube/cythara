package com.github.cythara.glView;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private static final int TRIANGLE_COUNT=MyGLRenderer.TRIANGLE_COUNT;

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    // Use to access and set the view transformation
    private int vPMatrixHandle;

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private FloatBuffer[] vertexBuffer=new FloatBuffer[TRIANGLE_COUNT];

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[][] = new float[TRIANGLE_COUNT][9];
    private float y(int i){
        return (float) (8.0*(Math.sin(Math.toRadians(5+10*i))));
    }
    private float x(int i){
        return (float) (8.0*(Math.cos(Math.toRadians(5+10*i))));
    }
    private void setTriangleCoords(){
        for (int i=0;i<TRIANGLE_COUNT;i++){
            for(int j=0;j<9;j++){
                switch (j){
                    case 0:
                    case 1:
                    case 2:
                    case 5:
                    case 8:
                        triangleCoords[i][j]=0.0f;
                        break;
                    case 3:
                        triangleCoords[i][j]=x(i);
                        break;
                    case 4:
                        triangleCoords[i][j]=y(i);
                        break;
                    case 6:
                        triangleCoords[i][j]=x(i-1);
                        break;
                    case 7:
                        triangleCoords[i][j]=y(i-1);
                }
            }
        }
    }
    // Set color with red, green, blue and alpha (opacity) values
    private final int mProgram;
    private int colorHandle;

    public Triangle(int count) {

        setTriangleCoords();
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer[]byteBuffers = new ByteBuffer[count];
        for (int counter=0;counter<count;counter++) {
            byteBuffers[counter] = ByteBuffer.allocateDirect(
                    // (number of coordinate values * 4 bytes per float)
                    triangleCoords[counter].length * 4);
            // use the device hardware's native byte order
            byteBuffers[counter].order(ByteOrder.nativeOrder());

            // create a floating point buffer from the ByteBuffer
            vertexBuffer[counter] =  byteBuffers[counter].asFloatBuffer();
            // add the coordinates to the FloatBuffer
            vertexBuffer[counter].put(triangleCoords[counter]);
            // set the buffer to read the first coordinate
            vertexBuffer[counter].position(0);

            GLES20.glUniform4fv(colorHandle, 1, triangleCoords[counter], 0);
        }

        // Set color for drawing the triangle
    }

    private int positionHandle;

    private final int vertexCount = 9 / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    void draw(float[] mvpMatrix ) {
        float j=0;
        float k=0;
        float l=0;
        // Add program to OpenGL ES environment
        for (int count=0;count<TRIANGLE_COUNT;count++){
            GLES20.glUseProgram(mProgram);

            // get handle to vertex shader's vPosition member
            positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(positionHandle);
            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    vertexStride, vertexBuffer[count]);

            colorHandle=GLES20.glGetUniformLocation(mProgram, "vColor");

            if (count % 12 < 4) {
                j += 0.1;
            } else if (count % 12 < 8) {
                k += 0.1;
            } else if (count % 12 != 11) {
                l += 0.1;
                j -= 0.1;
            } else {
                j = 0;
                k = 0;
                l = 0;
            }
            float[] color = {0.4f + k, 0.5f + j, 0.5f + l, 1.0f};
            GLES20.glUniform4fv(colorHandle, 1, color, 0);
            // get handle to shape's transformation matrix
            vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(positionHandle);
        }
    }
}
