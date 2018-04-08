package h4cirf.com.h4cinternetrelayfood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.amazonaws.http.HttpResponse;
import com.auth0.android.Auth0;
import com.auth0.android.management.UsersAPIClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import h4cirf.com.h4cinternetrelayfood.models.PostModel;
import h4cirf.com.h4cinternetrelayfood.models.PostSearchModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link PostListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostListFragment extends Fragment {
    private final int POSTS_PER_PAGE = 20;
    private int currentPostStart = 0;
    private ArrayList<PostModel> posts;
    private PostListAdapter adapter;
    private ListView listView;
    SearchView searchView;

    public PostListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PostListFragment.
     */
    public static PostListFragment newInstance() {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        // Get the first POSTS_PER_PAGE posts and put it in our list
        posts = new ArrayList<>();
        listView = view.findViewById(R.id.postListView);
        adapter = new PostListAdapter(getContext(), R.layout.post_list_item, posts);
        searchView = view.findViewById((R.id.postListSearch));
        view.requestFocus();
        //*
        // Populate our database
        MainActivity parentActivity = (MainActivity) getActivity();
        PostModel tempModel = new PostModel();
        tempModel.amount = "20";
        tempModel.creationDate = Calendar.getInstance().getTime();
        tempModel.description = "Lots of carrots";
        tempModel.pickupAddress = "1395 University St, Eugene, OR 97403";
        tempModel.readiness = "packed";
        tempModel.pickupWindow = "Now or never";
        tempModel.title = "Huge carrots!";
        tempModel.status = "available";
        //tempModel.email = MainActivity.userProfile.getEmail();
        tempModel.email = "b1t@ymail.com";
        //parentActivity.viewPost(tempModel);
        for(int i = 0; i < 20; ++i)
            posts.add(tempModel);
        //*/

        // Get our list
        fetchList();
        // Populate our ListView
        listView.setAdapter(adapter);
        //*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostModel selectedPost = (PostModel) parent.getItemAtPosition(position);
                ((MainActivity) getActivity()).viewPost(selectedPost);
                //System.out.println("Pressed post with position" + position);
            }
        });
        //*/
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void fetchList()
    {
        String query = searchView.getQuery().toString();
        // If we aren't searching for anything i.e. our query is empty, get everything
        if(query.isEmpty()) {
            Call<ArrayList<PostModel>> call = MainActivity.api.doGetListResources(0, POSTS_PER_PAGE);
            call.enqueue(new Callback<ArrayList<PostModel>>() {
                @Override
                public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                    // If we got
                    if (response.isSuccessful()) {
                        posts.clear();
                        posts.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                    System.err.println("We goofed");
                }
            });
        }
        else
        {
            // Set up our search argument
            PostSearchModel psm = new PostSearchModel();
            psm.query = query;
            psm.offset = currentPostStart;
            psm.limit = POSTS_PER_PAGE;
            Call<ArrayList<PostModel>> call = MainActivity.api.doSearchPost(psm);
            call.enqueue(new Callback<ArrayList<PostModel>>() {
                @Override
                public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                    // If we got
                    if (response.isSuccessful()) {
                        posts.clear();
                        posts.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                    System.err.println("We goofed");
                }
            });
        }
    }

    public void nextPageAction(View view)
    {
        currentPostStart += POSTS_PER_PAGE;
        fetchList();
    }
    public void prevPageAction(View view)
    {
        currentPostStart -= POSTS_PER_PAGE;
        if (currentPostStart < 0)
            currentPostStart = 0;
        fetchList();
    }
    public void firstPageAction(View view)
    {
        currentPostStart = 0;
        fetchList();
    }
}