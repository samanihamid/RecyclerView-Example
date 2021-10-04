package au.edu.unsw.infs3634.recyclerviewexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Adapter class that connects data set to the recycler view
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> implements Filterable {
    private List<Product>  mProducts;
    private List<Product> mProductsFiltered;
    private ClickListener mListener;

//    Initialise the dataset of the Adapter
    ProductAdapter(List<Product> products, ClickListener listener){
        this.mProducts = products;
        this.mProductsFiltered = products;
        this.mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mProductsFiltered = mProducts;
                } else {
                    ArrayList<Product> filteredList = new ArrayList<>();
                    for(Product product : mProducts) {
                        if(product.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(product);
                        }
                    }
                    mProductsFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mProductsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mProductsFiltered = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    // Allows click events to be caught
    public interface ClickListener {
        void onProductClick(View view, int productID);
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
        final Product product = mProductsFiltered.get(position);
        int productID = position;
        holder.name.setText(product.getName());
        holder.price.setText("Price: $"+String.valueOf(product.getPrice()));
        holder.itemView.setTag(productID);
    }

    // Total number of rows in the list
    @Override
    public int getItemCount() {
        return mProductsFiltered.size();
    }

    // Create view holder. The view holder has two text view elements
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, price;
        private ClickListener listener;

        public MyViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(MyViewHolder.this);
            name = itemView.findViewById(R.id.tvName);
            price = itemView.findViewById(R.id.tvPrice);
        }

        @Override
        public void onClick(View v) {
            listener.onProductClick(v, (Integer) v.getTag());
        }
    }

    // Sort method
    public void sort(final int sortMethod) {
        if(mProductsFiltered.size() > 0) {
            Collections.sort(mProductsFiltered, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    if(sortMethod == 1) {
                        return o1.getName().compareTo(o2.getName());
                    } else if(sortMethod == 2)
                        return String.valueOf(o1.getPrice()).compareTo(String.valueOf(o2.getPrice()));
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
        notifyDataSetChanged();
    }

}
