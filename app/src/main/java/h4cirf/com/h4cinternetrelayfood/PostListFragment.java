package h4cirf.com.h4cinternetrelayfood;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link PostListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostListFragment extends Fragment {
    private final long POSTS_PER_PAGE = 10;
    private long currentPostStart = 0;
    private ArrayList<Post> posts;
    private PostListAdapter adapter;

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
        posts = new ArrayList<>();
        // Get the first POSTS_PER_PAGE posts and put it in our list
        // TODO remove the debug generic list
        Post p1 = new Post();
        p1.postDescription = "Lots of yummy carrots!";
        p1.weight = "10";
        p1.expiry = "10-04-2018";
        p1.foodType = "Veg";
        posts.add(p1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        // Populate our list
        adapter = new PostListAdapter(getContext(), R.layout.post_list_item, posts);
        ListView listView = view.findViewById(R.id.postListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post selectedPost = (Post) parent.getItemAtPosition(position);
                // TODO: Launch the post view using the selected post
            }
        });
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
}
