package com.example.songbinwang.littledemo.anim;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.songbinwang.littledemo.R;
import com.example.songbinwang.littledemo.util.LogUtil;

/**
 * Created by songbinwang on 2017/2/20.
 */

public class MatrixActivity extends Activity {

    private EditText[] edits;
    private Button btn_change, btn_reset;
    private ImageView imageView;
    private static final String tag = "tag_matrix";

    Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        initViews();
        btn_change.setOnClickListener(onClickListener);
        btn_reset.setOnClickListener(onClickListener);
    }

    private void initViews() {
        imageView = (ImageView) findViewById(R.id.iv_show);
        edits = new EditText[9];
        for (int i = 0; i < 9; i++) {
            edits[i] = (EditText) findViewById(R.id.et_0 + i);
        }
        btn_change = (Button) findViewById(R.id.btn_change);
        btn_reset = (Button) findViewById(R.id.btn_reset);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_change:
                    changeImage();
                    break;
                case R.id.btn_reset:
                    resetImage();
                    break;
            }
        }
    };

    private void changeImage() {
        LogUtil.i(tag, "changeImage");
        float[] data = new float[9];
        for (int i = 0; i < edits.length; i++) {
            String txt = edits[i].getText().toString();
            try {
                if ("".equals(txt)) {
                    data[i] = 0;
                } else {
                    data[i] = Float.parseFloat(txt);
                }
            } catch (Exception e) {
                data[i] = 0;
            }
            LogUtil.i(tag, "" + data[i]);
        }
        matrix.setValues(data);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.aa2);
        Bitmap tempBitmap = bitmapDrawable.getBitmap();

        Bitmap bitmap = Bitmap.createBitmap(tempBitmap.getWidth(), tempBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        MatrixDrawable matrixDrawable = new MatrixDrawable(tempBitmap, matrix);
        matrixDrawable.draw(canvas);

        imageView.setImageBitmap(bitmap);
    }

    private void resetImage() {
        LogUtil.i(tag, "resetImage");
        matrix.reset();
        imageView.setImageResource(R.drawable.aa2);
    }

}
