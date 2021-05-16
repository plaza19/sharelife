package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.plaza19.sharelife.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> array;
    private View view;
    private ImageView image;

    public GridAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.array = arrayList;

    }

    @Override
    public int getCount() {
        return this.array.size();
    }

    @Override
    public Object getItem(int position) {
        return this.array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
            image = view.findViewById(R.id.imageView_grid_item);
            Picasso.with(context).load(array.get(position)).into(image);
        }
        return view;
    }
}
