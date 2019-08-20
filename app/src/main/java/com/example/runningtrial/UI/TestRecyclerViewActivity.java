package com.example.runningtrial.UI;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtrial.R;
import com.example.runningtrial.base.ActivityBasic;
import com.example.runningtrial.base.DataWarehouse;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.runningtrial.base.Utils.getResId;

public class TestRecyclerViewActivity extends ActivityBasic {
    String TAG = getClass().getSimpleName();
    private TextView textView;
    private RecyclerView recyclerView;
    private MainRecyclerViewGridAdapter myAdapter;
    private ArrayList<GridData> gridDataArray = new ArrayList<>();
    private ItemTouchHelper.Callback myCallback;
    boolean isGrid = false;
    int dragDirs, swipeDirs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler_view);

        findViews();
    }

    private void findViews() {
        textView = findViewById(R.id.textView);
        DataWarehouse.getRef().tvDebug = textView;

        setDataSnapShot();

        recyclerView = findViewById(R.id.rvGrid);
        if (isGrid){
            GridLayoutManager mgr=new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(mgr);
            dragDirs = ItemTouchHelper.UP|ItemTouchHelper.DOWN |
                    ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT;
            swipeDirs = 0;
        } else {
            // xml need to add android:scrollbars="vertical"
            LinearLayoutManager mgr = new LinearLayoutManager(this);
            mgr.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(mgr);
            dragDirs = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            swipeDirs = ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT;
        }
        myAdapter = new MainRecyclerViewGridAdapter(gridDataArray, isGrid);
        recyclerView.setAdapter(myAdapter);
        setItemTouchHelperCallback();
    }

    private void setDataSnapShot() {
        String [] resStrings = { "cat0", "cat1", "cow", "dog0", "dog1", "dog2", "dog3", "dove", "fish", "teddy_bear"};
        for (String s : resStrings) {
            gridDataArray.add(new GridData(getResId("ic_"+s, R.drawable.class), s));
        }
    }

    private void setItemTouchHelperCallback() {
        myCallback = new ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
            // For drag function
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(gridDataArray,fromPosition,toPosition);
                myAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                // return super.getSwipeThreshold(viewHolder);
                return 0.7f;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                gridDataArray.remove(position);
                myAdapter.notifyItemRemoved(position);
                // Swipe from left to right
                if (direction == ItemTouchHelper.LEFT)
                    textView.setText("Swipe LEFT: item " + position);
                // Swipe from right to left
                if (direction == ItemTouchHelper.RIGHT)
                    textView.setText("Swipe RIGHT: item " + position);
            }

            // old method, new methos is onChildDraw(), 每次View Holder的狀態變成拖拽( ACTION_STATE_DRAG )
            // 或者滑動( ACTION_STATE_SWIPE )的時候被調用
            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                // We only want the active item
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    if (viewHolder instanceof MainRecyclerViewGridAdapter.ViewHolder) {
                        MainRecyclerViewGridAdapter.ViewHolder itemViewHolder =
                                (MainRecyclerViewGridAdapter.ViewHolder) viewHolder;
                        itemViewHolder.onItemSelected();
                    }
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            // old method, new methods is onChildDraw(), 在一個view被拖拽然後被放開的時候被調用，
            // 同時也會在滑動被取消或者完成ACTION_STATE_IDLE )的時候被調用
            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);

                if (viewHolder instanceof MainRecyclerViewGridAdapter.ViewHolder) {
                    MainRecyclerViewGridAdapter.ViewHolder itemViewHolder =
                            (MainRecyclerViewGridAdapter.ViewHolder) viewHolder;
                    itemViewHolder.onItemClear();
                }
            }
        };
        //
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(myCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
