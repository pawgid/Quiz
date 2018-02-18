package com.example.pawe.quiz.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.pawe.quiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import controller.HttpHandler;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Paweł on 2018-02-17.
 */

public class QuizListActivity extends AppCompatActivity {
    private String TAG = QuizListActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "http://quiz.o2.pl/api/v1/quizzes/0/100";

    ArrayList<HashMap<String, String>> quizList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_list);

        quizList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
            }
        });

        new GetQuizes().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetQuizes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//             Showing progress dialog
            pDialog = new ProgressDialog(QuizListActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray quizList = jsonObj.getJSONArray("items");

                    // looping through All quizes
                    for (int i = 0; i < quizList.length(); i++) {
                        JSONObject quiz = quizList.getJSONObject(i);

                        JSONObject mainPhoto = quiz.getJSONObject("mainPhoto");

                        String id = quiz.getString("id");
                        String title = quiz.getString("title");
                        String content = quiz.getString("content");
                        String questions = quiz.getString("questions");
                        String type = quiz.getString("type");
                        String photoUrl = mainPhoto.getString("url");

                        // tmp hash map for single quiz
                        HashMap<String, String> singleQuizRow = new HashMap<>();

                        // adding each child node to HashMap key => value
                        singleQuizRow.put("id", id);
                        singleQuizRow.put("title", title);
                        singleQuizRow.put("content", content);
                        singleQuizRow.put("questions", questions);
                        singleQuizRow.put("type", type);
                        singleQuizRow.put("url", photoUrl);

                        // adding quiz to quiz list
                        QuizListActivity.this.quizList.add(singleQuizRow);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog


            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    QuizListActivity.this, quizList,
                    R.layout.list_item, new String[]{"title", "content", "url"}, new int[]{R.id.title,
                    R.id.content});

            lv.setAdapter(adapter);
        }



    }


}
