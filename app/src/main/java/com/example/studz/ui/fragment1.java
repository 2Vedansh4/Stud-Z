package com.example.studz.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.studz.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment1 extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;


    public fragment1() {
        // Required empty pub./;P'[/-'
        // ]]lic constructor
    }
    VideoView videoView ;
    private PlayerView playerView;
    private SimpleExoPlayer exoPlayer;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment1 newInstance(String param1, String param2) {
        fragment1 fragment = new fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Videos").child("videos1").child("url");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storagePath = dataSnapshot.getValue(String.class);

                    // Get the Cloud Storage reference
                    StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(storagePath);

                    // Get the download URL
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // uri contains the download URL
                        String downloadUrl = uri.toString();

                        // Now, you can use the download URL to play the video
                        playVideo(downloadUrl);
                    }).addOnFailureListener(exception -> {
                        // Handle failure to get the download URL
                        Toast.makeText(getContext(), "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    });
//
                }
                else {
                    Toast.makeText(getContext(), "omphoo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
               
            }
        });


        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        return view ;
    }
    private void playVideo(String videoUrl) {
        VideoView videoView = getView().findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.video3b);
        videoView.setVideoURI(videoUri);
       // videoView.setVideoURI(Uri.parse(videoUrl));
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(getView().findViewById(R.id.mediaControllerAnchor));
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(mp -> {
            // Video is prepared and ready to play
            videoView.start();
        });
        videoView.setOnErrorListener((mp, what, extra) -> {
            // Handle video playback error
            Log.e("VideoView", "Error during video playback");
            return false;
        });
    }


}