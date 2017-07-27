package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import duan2.jobspef.luyquangdat.com.myapplication.AppUtils;
import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;


import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.common.JobsPef;
import duan2.jobspef.luyquangdat.com.myapplication.entity.InfoMemberResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.SimpleResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentSettings extends Fragment implements View.OnClickListener {
    private Context context;
    private Toolbar toolbar;
    private Switch switchNotification;
    private Drawer drawer;

    private Button btnSave;
    private Button btnChange;

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvAddress;

    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPhone;
    private EditText edtAddress;

    private ImageView imgAva;

    private boolean isVisible = false;

    private UpdateImage updateImage;

    private static int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 999;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;

    private boolean isUploadSuccess;

    private String token;
    private String name;
    private String email;
    private String address;
    private String phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        token = MyUtils.getStringData(getContext(), Constants.PROFILE_ID);
        storageReference = storage.getReferenceFromUrl("gs://mchat-2a75e.appspot.com/userava").child(token + "ava.png");
        context = rootView.getContext();
        toolbar = rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        initController(rootView);
        initCompoment(rootView);
        checkPermission();
        return rootView;
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWritePermission = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasCameraPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                // Toast.makeText(getContext(), getString(R.string.not_permission_galaxy), Toast.LENGTH_SHORT).show();
            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            } else {
                //  Toast.makeText(getContext(), getString(R.string.not_permission_galaxy), Toast.LENGTH_SHORT).show();
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {
                // Toast.makeText(getContext(), getString(R.string.not_permission_camera), Toast.LENGTH_SHORT).show();
            }

            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            }
        }

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        } else {
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.CAMERA}, 998);
        } else {
        }

    }


    private void initController(View v) {
        switchNotification = v.findViewById(R.id.switch_notification);
        switchNotification.setChecked(JobsPef.getBooleanData(getContext(), Constants.NOTIFICATION_ON_FLAG));
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getString(R.string.settings));
        ImageView imgBack = toolbar.findViewById(R.id.imgBack);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentNotification()).addToBackStack(null).commit();
            }
        });
        ImageView imgMore = toolbar.findViewById(R.id.imgMore);
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen()) {
                    drawer.openDrawer();
                } else {
                    drawer.closeDrawer();
                }
            }
        });

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyUtils.insertBooleanData(getActivity(), Constants.NOTIFICATION_ON_FLAG, isChecked);
            }
        });
    }

    public void initCompoment(View v) {
        btnSave = (Button) v.findViewById(R.id.btnSave);
        btnChange = (Button) v.findViewById(R.id.btnEditInfo);

        tvName = (TextView) v.findViewById(R.id.tvName);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        tvPhone = (TextView) v.findViewById(R.id.tvPhone);
        tvAddress = (TextView) v.findViewById(R.id.tvAddress);

        edtName = (EditText) v.findViewById(R.id.edtName);
        edtPhone = (EditText) v.findViewById(R.id.edtPhone);
        edtAddress = (EditText) v.findViewById(R.id.edtAddress);

        imgAva = (ImageView) v.findViewById(R.id.imgAvatar);

        btnSave.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        imgAva.setOnClickListener(this);

        getInfoMember();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                validateFourm();
                break;
            case R.id.btnEditInfo:
                showEdittext();
                break;
            case R.id.imgAvatar:
                seletePhotoAction();
                break;
        }
    }

    public void validateFourm() {
        String nameNew = edtName.getText().toString().trim();
        String phoneNew = edtPhone.getText().toString().trim();
        String addressNew = edtAddress.getText().toString().trim();
        if (nameNew.length() == 0) {
            nameNew = name;
        } else if (nameNew.length() < 8) {
            edtName.setError(getResources().getString(R.string.name_too_short));
            edtName.requestFocus();
            return;
        } else if (phoneNew.length() == 0) {
            phoneNew = phone;
        } else if (phoneNew.length() < 10) {
            edtPhone.setError("Số điện thoại không đúng ");
            edtPhone.requestFocus();
            return;
        } else if (addressNew.length() == 0) {
            addressNew = address;
        } else if (addressNew.length() < 10) {
            edtAddress.setError("Địa chỉ của bạn quá ngắn ");
            edtAddress.requestFocus();
            return;
        }
        updateProfile(nameNew, token + "ava.png", phoneNew, addressNew);
    }

    public void showEdittext() {
        if (!isVisible) {
            isVisible = true;
            edtName.setVisibility(View.VISIBLE);
            edtPhone.setVisibility(View.VISIBLE);
            edtAddress.setVisibility(View.VISIBLE);
        } else {
            isVisible = false;
            edtName.setVisibility(View.GONE);
            edtPhone.setVisibility(View.GONE);
            edtAddress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 999:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                }
                break;
            case 998:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                }
                break;
            default:
                break;
        }
    }

    public void seletePhotoAction() {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());

        b.setTitle("Chọn ảnh đại diện ");
        b.setMessage("Hãy lựa chọn các cách dưới đây ");
        b.setPositiveButton("Từ máy ảnh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } catch (SecurityException s) {
                }

            }
        });
        b.setNegativeButton("Từ thư viện", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which)

            {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
            }

        });

        android.app.AlertDialog alertDialog = b.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationBottom;
        // show it
        alertDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        try {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        imgAva.setImageBitmap(photo);
                        File file = MyUtils.saveBitmapToFile(MyUtils.resize(photo, 800, 800), "picture" + ".jpg");
                        //image_edit = new TypedFile("multipart/form-data", file);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        displayImageFromGallery(imageReturnedIntent, imgAva);
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void updateProfile(String name, String ava, String phone, String address) {
        storeImageToFirebase(name, ava, phone, address);

    }

    public void updateProfileRequest(String name, final String ava, String phone, String address) {
        ConnectServer.getResponseAPI().updateProfile(token, name, ava, phone, address).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                AppUtils.hideProgressDialog(getContext());
                getInfoMember();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                AppUtils.hideProgressDialog(getContext());
            }
        });
    }

    public void storeImageToFirebase(final String name, final String ava, final String phone, final String address) {
        AppUtils.showProgressDialog(getContext(), "Loading");
        imgAva.setDrawingCacheEnabled(true);
        imgAva.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        imgAva.layout(0, 0, imgAva.getMeasuredWidth(), imgAva.getMeasuredHeight());
        imgAva.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(imgAva.getDrawingCache());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] data = outputStream.toByteArray();
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                AppUtils.hideProgressDialog(getContext());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                updateImage.onDataPass(1);
                updateProfileRequest(name, ava, phone, address);
            }
        });
    }

    private void displayImageFromGallery(Intent data, ImageView imageView) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bitmap = MyUtils.bitmapRotate(imgDecodableString);
        imageView.setImageBitmap(bitmap);
        File file = MyUtils.saveBitmapToFile(MyUtils.resize(bitmap, 800, 800), "picture" + ".jpg");
        // image_edit = new TypedFile("multipart/form-data", file);
    }

    public void getInfoMember() {
        AppUtils.showProgressDialog(getContext(), "Loading");
        String id = MyUtils.getStringData(getContext(), Constants.USER_ID);
        ConnectServer.getResponseAPI().getInfoMember(id).enqueue(new Callback<InfoMemberResponse>() {
            @Override
            public void onResponse(Call<InfoMemberResponse> call, Response<InfoMemberResponse> response) {
                AppUtils.hideProgressDialog(getContext());
                if (response.isSuccessful()) {
                    tvName.setText(" " + response.body().getMessage().getName());
                    tvEmail.setText(" " + response.body().getMessage().getContact_email());
                    tvPhone.setText(" " + response.body().getMessage().getPhone_number());
                    tvAddress.setText(" " + response.body().getMessage().getAddress());
                    String avatar_id = response.body().getMessage().getAvatar_id();
                    if (response.body().getMessage().getAvatar_id() == null || response.body().getMessage().getAvatar_id().equals("")) {
                        return;
                    } else {
                        StorageReference storageReferencegetAva = storage.getReferenceFromUrl("gs://mchat-2a75e.appspot.com/userava").child(response.body().getMessage().getAvatar_id());
                        getAva(storageReferencegetAva);
                    }
                    MyUtils.insertStringData(getContext(), Constants.IMAGE_ID, response.body().getMessage().getAvatar_id());
                    MyUtils.insertStringData(getContext(), Constants.IMAGE_ID, response.body().getMessage().getName());
                } else {
                    tvName.setText("Chưa có dữ liệu !");
                    tvEmail.setText("Chưa có dữ liệu !");
                    tvPhone.setText("Chưa có dữ liệu !");
                    tvAddress.setText("Chưa có dữ liệu !");
                }
            }

            @Override
            public void onFailure(Call<InfoMemberResponse> call, Throwable t) {
                AppUtils.hideProgressDialog(getContext());
                Log.d("bisaodasy", call.toString());
            }
        });
    }

    public void getAva(StorageReference storageReferencegetAva) {
        try {
            final File localFile = File.createTempFile("images", "jpg");

            storageReferencegetAva.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgAva.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
        }

    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        updateImage = (UpdateImage) a;
    }

    public interface UpdateImage {
        public void onDataPass(int data);
    }
}
