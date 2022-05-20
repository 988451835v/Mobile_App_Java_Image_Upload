package com.example.coding_1;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MathsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MathsFragment extends Fragment {
    TextView tv_add;
    Bitmap image_file;
    EditText edit_cap;
    ImageView img_post;
    String Uid;
    FirebaseAuth mAuth;
    Button upload;
    FirebaseFirestore db;
    StorageReference storageReference;
    UploadTask uploadTask;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode!= RESULT_CANCELED){
                switch(requestCode){
                    case 0:
                        if(resultCode == RESULT_OK && data!=null) {
                            image_file = (Bitmap) data.getExtras().get("data");
                            img_post.setImageBitmap(image_file);
//                            uploadImage(image_file);
                        }
                        break;
                    case 1:
                        if(resultCode == RESULT_OK && data!=null){
                            Uri path=data.getData();
                            try {
                                image_file=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),path);
                                img_post.setImageBitmap(image_file);
//                                uploadImage(image_file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                }
            }

    }



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MathsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MathsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MathsFragment newInstance(String param1, String param2) {
        MathsFragment fragment = new MathsFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_maths, container, false);
        tv_add=view.findViewById(R.id.tv_add);
        img_post=view.findViewById(R.id.img_post);
        upload=view.findViewById(R.id.upload);
        edit_cap=view.findViewById(R.id.edit_text2);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        Uid=mAuth.getUid();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(v, "Please Wait", Snackbar.LENGTH_LONG);


                String des=edit_cap.getText().toString();
                String id=db.collection("Post").document().getId();
                DocumentReference documentReference=db.collection("Post").document(id);
                Map<String,Object>post=new HashMap<>();
                post.put("USERID", Uid );
                post.put("POST_DESCRIPTION",des);


                documentReference.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        UploadImage(image_file,id);
                        snackbar.show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error 1", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] option = {"Take photo","From gallery","cancel"};
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Select Your Option");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                //cam
                                Intent takePicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture,0);
                                break;
                            case 1:
                                //gellery
                                Intent pickPicture=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPicture,1);
                                break;
                            case 2:
                                dialog.dismiss();
                                break;

                        }
                    }
                });
            builder.show();
            }
        });
        return view;
    }

    private void UploadImage(Bitmap image_file,String id){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        image_file.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        storageReference= FirebaseStorage.getInstance().getReference().child("POST_IMAGE/"+id);
        uploadTask=storageReference.putBytes(byteArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.search_edit_frame,new HomeFragment()).commit();
                Toast.makeText(getContext(), "Upload successfully", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Post not uploaded successfully", Toast.LENGTH_SHORT).show();

            }
        });

    }
}