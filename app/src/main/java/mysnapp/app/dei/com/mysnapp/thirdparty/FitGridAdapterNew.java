package mysnapp.app.dei.com.mysnapp.thirdparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class FitGridAdapterNew<T> extends BaseAdapter {

    private Context context;
    private int columnWidth, columnHeight, row, column, layoutID, size = 0;;
    private List<T> list = new ArrayList();

    public FitGridAdapterNew(Context context) {
        this.context = context;
    }

    public FitGridAdapterNew(Context context, int layoutID) {
        this.context = context;
        this.layoutID = layoutID;
    }

    public FitGridAdapterNew(Context context, int layoutID, List<T> list) {
        this.context = context;
        this.layoutID = layoutID;
        this.list = list;
//        size = list.size();
    }

    public FitGridAdapterNew(Context context, int layoutID, int size) {
        this.context = context;
        this.layoutID = layoutID;

        this.size = size;
    }

    public FitGridAdapterNew(Context context, int layoutID, int row, int column) {
        this.context = context;
        this.layoutID = layoutID;
        this.row = row;
        this.column = column;
        size = row * column;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(layoutID, parent, false);

            AbsListView.LayoutParams params = new AbsListView.LayoutParams(columnWidth, columnHeight);
            itemView.setLayoutParams(params);

            onCreateView(position, itemView);
        }
        onBindView(position, itemView);
        return itemView;
    }

    public abstract void onCreateView(int position, View view);

    public abstract void onBindView(int position, View view);

    @Override
    public int getCount() {
        return size == 0 ? column * row : size;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    void setColumnHeight(int columnHeight) {
        this.columnHeight = columnHeight;
    }

    void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    void setNumColumns(int column) {
        this.column = column;
    }

    void setNumRows(int row) {
        this.row = row;
    }
}
