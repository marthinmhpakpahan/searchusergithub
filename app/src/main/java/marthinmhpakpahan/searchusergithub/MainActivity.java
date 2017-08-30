package marthinmhpakpahan.searchusergithub;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {


    RecyclerView recyclerView;
    EditText editTextSearch;

    // List for data from api and for search offline
    List<User> models, updatedDataContent;

    // Declaration adapter
    UserAdapter adapter;

    // Declaration interface api
    GithubServiceInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        editTextSearch = (EditText)findViewById(R.id.editTextSearch);

        // bind data into adapter
        models = new ArrayList<>();
        adapter = new UserAdapter(MainActivity.this, models);
        RecyclerView.LayoutManager mLayoutImageManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(mLayoutImageManager);
        recyclerView.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();

        service = retrofit.create(GithubServiceInterface.class);

        loadData();
    }

    private void loadData(){
        Call<List<User>> users = service.listRepos();
        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    models = response.body();
                    adapter.updateItem(models);
                    editTextSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int x, int i1, int i2) {
                            // set list to store data after search
                            updatedDataContent = new ArrayList<User>();
                            Log.e("KeyListener", "Size new: "+models.size());

                            // find item in list
                            for (int i=0; i<models.size(); i++) {
                                if (models.get(i).getUsername().toLowerCase().contains(editTextSearch.getText().toString().toLowerCase())) {
                                    updatedDataContent.add(models.get(i));
                                }
                            }
                            Log.e("KeyListener", "Size new: "+updatedDataContent.size());

                            // update into list
                            adapter = new UserAdapter(MainActivity.this, updatedDataContent);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                } else {
                    Log.e("Error", response.message()+" - "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
