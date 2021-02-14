package com.example.dollarchecker.testretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dollarchecker.R;
import com.example.dollarchecker.Record;
import com.example.dollarchecker.ValCurs;
import com.example.dollarchecker.testretrofit.CbrApi;
import com.example.dollarchecker.testretrofit.JsonPlaceHolderApi;
import com.example.dollarchecker.testretrofit.Post;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class testRetrofit extends AppCompatActivity {
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_retrofit);

        textView = findViewById(R.id.text__view_result);

        firstXMLtest xml = new firstXMLtest();
        xml.doOperation();
    }

    class firstXMLtest{
        public void doOperation(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://cbr.ru/scripts/")
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            CbrApi cbrApi = retrofit.create(CbrApi.class);

            Call<ValCurs> call = cbrApi.getDollarsByRange("01/01/2021", "01/02/2021");

            call.enqueue(new Callback<ValCurs>() {
                @Override
                public void onResponse(Call<ValCurs> call, Response<ValCurs> response) {
                    if(!response.isSuccessful()){
                        textView.setText("Code: " + response.code());
                        return;
                    }

                    ValCurs vals = response.body();

                    for(Record record: vals.getValueList()){
                        String res = "";
                        res += "Date: "+ record.getDate() + "\n";
                        res+= "Value: "+ record.getValue() + "\n\n";
                        textView.append(res);
                        Log.d("TAG", record.getDate());

                    }

                }

                @Override
                public void onFailure(Call<ValCurs> call, Throwable t) {
                    textView.setText(t.getMessage());
                }
            });
        }
    }

    class firstJSONtest{
        public void doOperation(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<List<Post>> call = jsonPlaceHolderApi.getPost();

            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    if(!response.isSuccessful()){
                        textView.setText("Code: " + response.code());
                        return;
                    }

                    List<Post> posts = response.body();

                    for (Post post: posts){
                        String content = "";
                        content += "ID: " + post.getId() + "\n";
                        content += "UserID: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Text: " + post.getText() + "\n\n";

                        textView.append(content);

                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    textView.setText(t.getMessage());
                }
            });
        }
    }



}