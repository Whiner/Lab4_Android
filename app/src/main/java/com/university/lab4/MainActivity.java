package com.university.lab4;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.university.lab4.filework.FileScanner;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends ListActivity {
    private FileScanner fileScanner = new FileScanner();
    private String currentPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private View selectedItem;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        this.getListView().setSelector(R.color.select_color);
        fillListView(currentPath);
        this.getListView().setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            protected void onLongPress() {
                if (selectedItem != null) {
                    TextView viewById = selectedItem.findViewById(R.id.text_view);
                    String file = currentPath.concat("/" + viewById.getText().toString());
                    String newPathForCopy = fileScanner.getNewPathForCopy(file);
                    if (fileScanner.copyFileOrDirectory(file, newPathForCopy)) {
                        Toast.makeText(MainActivity.this, "Скопировано", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Ошибка копирования", Toast.LENGTH_SHORT).show();
                    }
                    fillListView(currentPath);
                }
            }

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
            public void onSwipeTop() {
                if (selectedItem != null) {
                    onNextFile(selectedItem);
                }
            }

            @Override
            public void onSwipeBottom() {
                if (selectedItem != null) {
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
            if (fileScanner.isAvailable(path)) {
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
            selectedItem = null;
        }
    }

    private boolean fillListView(String path) {
        List<File> files = fileScanner.get(path);
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                if (file1.isDirectory() && file2.isDirectory()) {
                    if (file1.getName().trim().startsWith(".")) {
                        return 1;
                    } else if (file2.getName().trim().startsWith(".")) {
                        return -1;
                    }
                    return file1.getName().toLowerCase().compareTo(file2.getName().toLowerCase());
                } else if (file1.isDirectory()) {
                    return -1;
                } else if (file2.isDirectory()) {
                    return 1;
                }
                if (file1.getName().trim().startsWith(".")) {
                    return 1;
                } else if (file2.getName().trim().startsWith(".")) {
                    return -1;
                }
                return file1.getName().toLowerCase().compareTo(file2.getName().toLowerCase());
            }
        });
        if (files.isEmpty()) {
            Toast.makeText(this, "Папка пуста", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            setListAdapter(new CustomAdapter(this, files));
            return true;
        }
    }
}

