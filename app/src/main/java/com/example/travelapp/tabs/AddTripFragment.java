package com.example.travelapp.tabs;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelapp.LoginActivity;
import com.example.travelapp.R;
import com.example.travelapp.recyclerviewImages.Trip;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTripFragment extends Fragment {
    private static final int RESULT_OK = -1;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton searchPhotoButton;
    private ImageButton uploadPhotoButton;
    private ImageView imageViewUpload;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private EditText locationEditText;
    private EditText descriptionEditText;
    private StorageTask uploadTask;
    public Uri imageUri;
    public static int PERMISSION_REQUEST_READ_FOLDERS = 1;


    public AddTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
        searchPhotoButton = view.findViewById(R.id.searchPhotoButton);
        uploadPhotoButton = view.findViewById(R.id.uploadPhotoButton);
        imageViewUpload = view.findViewById(R.id.imageViewUpload);
        locationEditText = view.findViewById(R.id.locationEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Trips");

        searchPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(getActivity(), "Upload in progress",Toast.LENGTH_SHORT).show();
                }
                else {
                    ImageUploader();
                }
            }
        });
        checkPermissions();
        return view;
    }

    private void checkPermissions() {
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        if(ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Arrays.toString(PERMISSIONS)) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), PERMISSIONS, PERMISSION_REQUEST_READ_FOLDERS);
        }
    }

    private void ImageUploader() {
        if(imageUri != null){
            StorageReference reference = storageReference.child(System.currentTimeMillis()+"." + getExtension(imageUri));
            uploadTask = reference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
                            Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                            firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    Trip trip = new Trip(locationEditText.getText().toString().trim(),
                                            descriptionEditText.getText().toString().trim(), url, LoginActivity.USER);
                                    String uploadId = databaseReference.push().getKey();
                                    if(uploadId != null) {
                                        databaseReference.child(uploadId).setValue(trip);
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                    Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getExtension(Uri imageUri) {
        ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageViewUpload);
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*  ");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_READ_FOLDERS){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permissions accepted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
