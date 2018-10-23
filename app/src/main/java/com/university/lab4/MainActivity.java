package com.university.lab4;

import android.app.ListActivity;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.university.lab4.filework.FileScanner;

import java.io.File;
import java.util.List;

public class MainActivity extends ListActivity {
    private FileScanner fileScanner = new FileScanner();
    private String currentPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private View selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        this.getListView().setSelector(R.color.select_color);
        fillListView(currentPath);
        this.getListView().setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            public void onSwipeRight() {
                if (selectedItem != null) {
                    onNextFile(selectedItem);
                }
            }

            @Override
            public void onSwipeLeft() {
                onPrevFile();
            }

            @Override
            public void onZoomScale(float scaleFactor) {
                if(scaleFactor > 1.0){
                    if (selectedItem != null) {
                        onNextFile(selectedItem);
                    }
                } else {
                    onPrevFile();
                }
            }
        });
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        if (selectedItem != null) {
            selectedItem.setSelected(false);
        }
        selectedItem = v;
        selectedItem.setSelected(true);
        super.onListItemClick(l, v, position, id);
    }

    private void onNextFile(View v) {
        TextView viewById = v.findViewById(R.id.text_view);
        String path = currentPath.concat("/" + viewById.getText());
        if (fileScanner.isDirectory(path)) {
            if(fileScanner.isAvailable(path)) {
                if (fillListView(path)) {
                    currentPath = path;
                    selectedItem = null;
                }
            } else {
                Toast.makeText(this, "Папка защищена правами доступа", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Файл " + viewById.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onPrevFile() {
        String path = fileScanner.getParentPath(currentPath);
        if (path == null) {
            Toast.makeText(this, "Дальше некуда", Toast.LENGTH_SHORT).show();
        } else {
            currentPath = path;
            fillListView(currentPath);
        }
    }

    private boolean fillListView(String path) {
        List<File> files = fileScanner.get(path);
        if (files.isEmpty()) {
            Toast.makeText(this, "Папка пуста", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            setListAdapter(new CustomAdapter(this, files));
            return true;
        }
    }
}

