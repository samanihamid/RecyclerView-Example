package au.edu.unsw.infs3634.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private List<Product> mProducts = new ArrayList<>();
    private ProductAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate Recyclerview
        mRecyclerView = findViewById(R.id.recycleView);
        // Set setHasFixedSize true if contents of the adapter does not change it's height or the width
        mRecyclerView.setHasFixedSize(true);

        // Specify linear layout manager for RecyclerView
         RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        // Specify grid layout manager for RecyclerView
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        // Prepare data
        prepareProducts();

        ProductAdapter.ClickListener listener = new ProductAdapter.ClickListener() {
            @Override
            public void onProductClick(View view, int productID) {
                final Product product = mProducts.get(productID);
                // Display details for the selected RecyclerView item (product on the list)
                Toast.makeText(getApplicationContext(), product.getName()+"\nPrice = $"+product.getPrice(), Toast.LENGTH_SHORT).show();
            }
        };
        // Instantiate adapter
        mAdapter = new ProductAdapter(mProducts, listener);
        // Set the adapter for the recycler view
        mRecyclerView.setAdapter(mAdapter);
    }

    private void prepareProducts(){
        Random rand = new Random();
        for(int i=0; i < 50; i++) {
            // random.nextInt(max - min + 1) + min (generates number from min to max (including both))
            int price = rand.nextInt(1000 - 100 + 1) + 100;
            Product product = new Product("Product "+i, price);
            mProducts.add(product);
        }
    }
}