package au.edu.unsw.infs3634.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
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
        for(int i=10; i < 50; i++) {
            // random.nextInt(max - min + 1) + min (generates number from min to max (including both))
            int price = rand.nextInt(1000 - 100 + 1) + 100;
            Product product = new Product("Product-"+i, price);
            mProducts.add(product);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.sortName:
                // sort by name
                mAdapter.sort(1);
                return true;
            case R.id.sortPrice:
                // sort by price
                mAdapter.sort(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}