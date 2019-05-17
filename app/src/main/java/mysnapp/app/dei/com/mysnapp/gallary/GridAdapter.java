package mysnapp.app.dei.com.mysnapp.gallary;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.thirdparty.FitGridAdapter;

public class GridAdapter extends FitGridAdapter {

    private int[] drawables = {
            R.drawable.ic_ar, R.drawable.ic_cart, R.drawable.ic_claim_card, R.drawable.ic_close,
            R.drawable.ic_home, R.drawable.ic_home2, R.drawable.ic_info, R.drawable.ic_cart,
            R.drawable.ic_ar, R.drawable.ic_claim_card, R.drawable.ic_home2, R.drawable.ic_home};

    private Context context;

    GridAdapter(Context context) {
        super(context, R.layout.grid_item);
        this.context = context;
    }

    @Override
    public void onCreateView(int position, View view) {
        ImageView iv = view.findViewById(R.id.grid_item_iv);
        iv.setImageResource(drawables[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBindView(final int position, View itemView) {
        ImageView iv = itemView.findViewById(R.id.grid_item_iv);
        iv.setImageResource(drawables[position]);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
