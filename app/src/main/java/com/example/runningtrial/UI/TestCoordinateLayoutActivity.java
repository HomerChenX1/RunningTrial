package com.example.runningtrial.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.runningtrial.R;

public class TestCoordinateLayoutActivity extends AppCompatActivity {
    int windowwidth;
    int windowheight;
    AppCompatButton btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_coordinate_layout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        windowheight = metrics.heightPixels;
        windowwidth  = metrics.widthPixels;

        btn1 = findViewById(R.id.appCompBtn);
        // enable drag and move
        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // which contains btn1 : CoordinatorLayout
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) btn1.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        btn1.performClick();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();

                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                        }
                        if (y_cord > windowheight) {
                            y_cord = windowheight;
                        }

                        layoutParams.leftMargin = x_cord - 25;
                        layoutParams.topMargin = y_cord - 75;

                        btn1.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}

class ButtonBehavior extends CoordinatorLayout.Behavior<Button> {
    public ButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull Button child, @NonNull View dependency) {
        // 判斷 child 是否依賴 Dependency
        // 返回 false 表示 child 不依賴 Dependency
        // return super.layoutDependsOn(parent, child, dependency);
        return dependency instanceof AppCompatButton;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull Button child, @NonNull View dependency) {
        // child 具體要執行的行為
        // 當 Dependency 發生變化時(位置、寬、高等)會執行此方法
        // 返回 true 表示 child 要作出相應的變化，否則 false
        // return super.onDependentViewChanged(parent, child, dependency);
        int top = dependency.getTop();
        setPosition(child, top);
        return true;
    }

    private void setPosition(View view, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams)view.getLayoutParams();
        layoutParams.topMargin = y;
        view.setLayoutParams(layoutParams);
    }
}


