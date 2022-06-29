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


// package com.example.coding_1;

// import android.content.DialogInterface;
// import android.os.Bundle;

// import androidx.annotation.NonNull;
// import androidx.fragment.app.Fragment;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import android.util.Log;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.EditText;
// import android.widget.TextView;
// import android.widget.Toast;

// import com.google.android.gms.tasks.OnCompleteListener;
// import com.google.android.gms.tasks.OnFailureListener;
// import com.google.android.gms.tasks.Task;
// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseUser;
// import com.google.firebase.database.DataSnapshot;
// import com.google.firebase.firestore.DocumentReference;
// import com.google.firebase.firestore.DocumentSnapshot;
// import com.google.firebase.firestore.FirebaseFirestore;
// import com.google.firebase.firestore.QueryDocumentSnapshot;
// import com.google.firebase.firestore.QuerySnapshot;

// import java.util.ArrayList;

// /**
//  * A simple {@link Fragment} subclass.
//  * Use the {@link HomeFragment#newInstance} factory method to
//  * create an instance of this fragment.
//  */
// public class HomeFragment extends Fragment {
//     RecyclerView rv_lists;
//     AdapterClass adapterClass;
//     ArrayList<ModelClass>modelClasses;
//     LinearLayoutManager linearLayoutManager;
//     FirebaseAuth mAuth;
//     FirebaseFirestore db;
//     FirebaseUser user;
//     String Uid;
//     TextView et_name,et_town;


//     // TODO: Rename parameter arguments, choose names that match
//     // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//     private static final String ARG_PARAM1 = "param1";
//     private static final String ARG_PARAM2 = "param2";

//     // TODO: Rename and change types of parameters
//     private String mParam1;
//     private String mParam2;

//     public HomeFragment() {
//         // Required empty public constructor
//     }

//     /**
//      * Use this factory method to create a new instance of
//      * this fragment using the provided parameters.
//      *
//      * @param param1 Parameter 1.
//      * @param param2 Parameter 2.
//      * @return A new instance of fragment HomeFragment.
//      */
//     // TODO: Rename and change types and number of parameters
//     public static HomeFragment newInstance(String param1, String param2) {
//         HomeFragment fragment = new HomeFragment();
//         Bundle args = new Bundle();
//         args.putString(ARG_PARAM1, param1);
//         args.putString(ARG_PARAM2, param2);
//         fragment.setArguments(args);
//         return fragment;
//     }

//     @Override
//     public void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         if (getArguments() != null) {
//             mParam1 = getArguments().getString(ARG_PARAM1);
//             mParam2 = getArguments().getString(ARG_PARAM2);

//         }

//     }



//     @Override
//     public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                              Bundle savedInstanceState) {
//         // Inflate the layout for this fragment
//         View view=inflater.inflate(R.layout.fragment_home,container, false);
//         et_name=view.findViewById(R.id.textView99);
// //        et_email=view.findViewById(R.id.et_email);
//         et_town=view.findViewById(R.id.tv_town);
//         rv_lists=view.findViewById(R.id.rv_lists);
//         db=FirebaseFirestore.getInstance();
//         mAuth= FirebaseAuth.getInstance();
//         Uid=mAuth.getCurrentUser().getUid();
//         user=mAuth.getCurrentUser();

// //        modelClasses=new ArrayList<>();
// ////        modelClasses.add(new ModelClass(R.drawable.student2,"A S R Perera","Colombo"));
// ////        modelClasses.add(new ModelClass(R.drawable.student2,"A S R Perera2","Colombo"));
// //
// /////////1st try//////////////////////////////////////////////////////////////////////////////////////////////////////////////
// //        DocumentReference documentReference=db.collection("USER").document(Uid);
// //        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
// //            @Override
// //            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
// //                if(task.isSuccessful()){
// //                    DocumentSnapshot documentSnapshot=task.getResult();
// //                    if(documentSnapshot.exists()){
// //                        String name,town;
// //                        name=(String) documentSnapshot.getData().get("USER NAME");
// //                        town=(String) documentSnapshot.getData().get("TOWN");
// //                        modelClasses.add(new ModelClass(R.drawable.student2,name,town));
// //                        Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
// //
// //
// //
// //                    }
// //                    else{
// //                        System.out.println("No such document");
// //                    }
// //
// //
// //                }
// //                else{
// //                    Toast.makeText(getContext(),"Task is not success!",Toast.LENGTH_SHORT).show();
// //
// //                }
// //            }
// //        }).addOnFailureListener(new OnFailureListener() {
// //            @Override
// //            public void onFailure(@NonNull Exception e) {
// //                Toast.makeText(getContext(),"Error occurred!",Toast.LENGTH_SHORT).show();
// //
// //            }
// //        });
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//         //////////////2nd try//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//         modelClasses = new ArrayList<>();

//         db.collection("USER").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//             @Override
//             public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                 if(task.isSuccessful()){
//                     modelClasses=new ArrayList<>();
//                     for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
//                         String name=documentSnapshot.getData().get("USER NAME").toString();
//                         String town=documentSnapshot.getData().get("TOWN").toString();
//                         modelClasses.add(new ModelClass(R.drawable.student2,name,town));
//                         Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();

//                     }
//                 }
//                 else{

//                     Toast.makeText(getContext(),"Error occurred 1!",Toast.LENGTH_SHORT).show();

//                 }

//             }
//         }).addOnFailureListener(new OnFailureListener() {
//             @Override
//             public void onFailure(@NonNull Exception e) {
//                 Toast.makeText(getContext(),"Error occurred 2!",Toast.LENGTH_SHORT).show();

//             }
//         });

// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//         setAdapter();
//         return view;


//     }

//     private void setAdapter() {

//         linearLayoutManager=new LinearLayoutManager(getContext());
//         linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//         rv_lists.setLayoutManager(linearLayoutManager);
//         adapterClass=new AdapterClass(modelClasses);
//         rv_lists.setAdapter(adapterClass);

// //        rv_lists.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
// //        adapterClass =new AdapterClass(modelClasses);
// //        rv_lists.setAdapter(adapterClass);
// //        rv_lists.setLayoutManager(linearLayoutManager);

//     }

// }
