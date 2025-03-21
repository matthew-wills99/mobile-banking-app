package com.example.comp4200project;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalMarginItemDecoration extends RecyclerView.ItemDecoration {
    private final int horizontalMargin;

    public HorizontalMarginItemDecoration(int horizontalMargin) {
        this.horizontalMargin = horizontalMargin;
    }

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        outRect.left = horizontalMargin / 2;
        outRect.right = horizontalMargin / 2;
    }
}