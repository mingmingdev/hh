package com.fifth.mygroup.trudgedailydemo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fifth.mygroup.trudgedailydemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationCommentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notification_comment_fragment, container, false);
    }

}
