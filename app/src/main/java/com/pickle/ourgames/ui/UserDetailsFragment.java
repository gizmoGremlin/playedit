package com.pickle.ourgames.ui;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.pickle.ourgames.R;
import com.pickle.ourgames.ui.viewmodels.UserDetailsViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

public class UserDetailsFragment extends Fragment {
    private CircularImageView imgUserPortait;
    private UserDetailsViewModel viewModel;
    private FirebaseAuth auth;
    private String uuid;

    Intent intent;
    ImageButton btnOpenFile;
    private static final int GALLERY_REQUEST_CODE = 888;

    public static UserDetailsFragment newInstance() {
        return new UserDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_details_fragment, container, false);
        btnOpenFile = v.findViewById(R.id.btn_open_image_user_detail);
        imgUserPortait = v.findViewById(R.id.img_user_portrait_user_details);
        imgUserPortait.setCircleColor(Color.WHITE);
// Set Border
        imgUserPortait.setBorderColor(Color.RED);
        imgUserPortait.setBorderWidth(10);
// Add Shadow with default param
        imgUserPortait.setShadowEnable(true);
// or with custom param
        imgUserPortait.setShadowRadius(15);
        imgUserPortait.setShadowColor(Color.RED);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        auth = FirebaseAuth.getInstance();
        uuid = auth.getUid();

        Picasso.get().load(R.drawable.filledin_red_heart).into(imgUserPortait);

        btnOpenFile.setOnClickListener(click -> {
            pickFromGallery();
        });
    }


    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        String[] permission = {"android.permission.READ_EXTERNAL_STORAGE"};
        String permissionAsString = "android.permission.READ_EXTERNAL_STORAGE";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (getContext().checkSelfPermission(permissionAsString) != PackageManager.PERMISSION_GRANTED) {
                int permsRequestCode = 200;

                ActivityCompat.requestPermissions(getActivity(), permission,permsRequestCode);

            }else{
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        }else {
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        }







    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);


                    File f = new File(cursor.getString(columnIndex));
                        Uri uri = Uri.fromFile(f);

                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    Picasso.get().load(f).into(imgUserPortait, new Callback() {
                        @Override
                        public void onSuccess() {

                            Uri image = data.getData();  // persist to storage
                            viewModel.uploadProfilePic(image,uuid);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Portrait load err:", e.getMessage());
                        }
                    });
                  //  imgUserPortait.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if (requestCode == 200){
           if (permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               startActivityForResult(intent, GALLERY_REQUEST_CODE);
           }
       }



    }
}
