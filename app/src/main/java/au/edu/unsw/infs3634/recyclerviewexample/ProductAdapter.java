package au.edu.unsw.infs3634.recyclerviewexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter class that connects data set to the recycler view
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    private List<Product>  mProducts;
    private ClickListener mListener;

//    Initialise the dataset of the Adapter
    ProductAdapter(List<Product> products, ClickListener listener){
        this.mProducts = products;
        this.mListener = listener;
    }

    // Allows clicks events to be caught
    public interface ClickListener {
        void onClick(View view, int productID);
    }

    // Inflate the row layout from xml when needed (just the view, no data)
    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view, mListener);
    }

    // Bind the data to the TextView elements in each row
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        final Product product = mProducts.get(position);
        int productID = position;
        holder.name.setText(product.getName());
        holder.price.setText("Price: $"+String.valueOf(product.getPrice()));
        holder.itemView.setTag(productID);
    }

    // Total number of rows in the list
    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    // Create view holder. The view holder has two text view elements and a listener to catch click events
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, price;
        private ClickListener listener;

        public MyViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.tvName);
            price = itemView.findViewById(R.id.tvPrice);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (Integer) v.getTag());
        }
    }

}
