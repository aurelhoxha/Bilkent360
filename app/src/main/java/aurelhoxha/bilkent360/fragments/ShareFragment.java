package aurelhoxha.bilkent360.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.other.BlogType;


public class ShareFragment extends Fragment {

    private RecyclerView mBlogList;

    private DatabaseReference mDatabase;
    private String mUserId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragmentView = inflater.inflate(R.layout.fragment_share, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mBlogList = (RecyclerView)myFragmentView.findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return myFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<BlogType,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogType, BlogViewHolder>(

                BlogType.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, BlogType model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getActivity(),model.getImage());
            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title)
        {
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc)
        {
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image)
        {
            ImageView post_image = (ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }

}
