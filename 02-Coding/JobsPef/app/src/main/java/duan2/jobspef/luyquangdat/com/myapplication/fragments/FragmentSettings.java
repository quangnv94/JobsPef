package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import duan2.jobspef.luyquangdat.com.myapplication.AppUtils;
import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;


import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
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
    private boolean isUploadSuccess;

    private String token;
    private String name;
    private String email;
    private String address;
    private String phone;
    private File file;
    private String nameNew;
    private String phoneNew;
    private String addressNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        token = MyUtils.getStringData(getContext(), Constants.PROFILE_ID);
        context = rootView.getContext();
        toolbar = rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        initController();
        initCompoment(rootView);
        checkPermission();
        return rootView;
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWritePermission = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasCameraPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            }
        }

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.CAMERA}, 998);
        }

    }


    private void initController() {
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getString(R.string.settings));
        ImageView imgBack = toolbar.findViewById(R.id.imgCreatePost);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCreateJobs()).addToBackStack(null).commit();
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


    }

    public void initCompoment(View v) {
        btnSave = v.findViewById(R.id.btnSave);
        btnChange = v.findViewById(R.id.btnEditInfo);

        tvName = v.findViewById(R.id.tvName);
        tvEmail = v.findViewById(R.id.tvEmail);
        tvPhone = v.findViewById(R.id.tvPhone);
        tvAddress = v.findViewById(R.id.tvAddress);

        edtName = v.findViewById(R.id.edtName);
        edtPhone = v.findViewById(R.id.edtPhone);
        edtAddress = v.findViewById(R.id.edtAddress);

        imgAva = v.findViewById(R.id.imgAvatar);

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
        nameNew = edtName.getText().toString().trim();
        phoneNew = edtPhone.getText().toString().trim();
        addressNew = edtAddress.getText().toString().trim();
        if (nameNew.length() == 0) {
            nameNew = name;
        } else if (nameNew.length() < 8) {
            edtName.setError(getResources().getString(R.string.name_too_short));
            edtName.requestFocus();
            return;
        } else if (phoneNew.length() == 0) {
            phoneNew = phone;
        } else if (phoneNew.length() < 10) {
            edtPhone.setError(getString(R.string.invail_phone));
            edtPhone.requestFocus();
            return;
        } else if (addressNew.length() == 0) {
            addressNew = address;
        } else if (addressNew.length() < 10) {
            edtAddress.setError(getString(R.string.invail_address));
            edtAddress.requestFocus();
            return;
        }
        updateProfile();
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

    public void seletePhotoAction() {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());

        b.setTitle(getString(R.string.select_image));
        b.setMessage(R.string.select_image);
        b.setPositiveButton(getString(R.string.from_camera), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } catch (SecurityException s) {
                    s.toString();
                }

            }
        });
        b.setNegativeButton(getString(R.string.from_galaxy), new DialogInterface.OnClickListener() {

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
                        file = MyUtils.saveBitmapToFile(MyUtils.resize(photo, 800, 800), "picture" + ".jpg");
                        //image_edit = new TypedFile("multipart/form-data", file);
                        Log.d("nguyenvanquang", photo + "");
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {

                        displayImageFromGallery(imageReturnedIntent, imgAva);
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void updateProfile() {

        TestSync testSync = new TestSync();
        testSync.execute(file);
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
        file = MyUtils.saveBitmapToFile(MyUtils.resize(bitmap, 800, 800), "picture" + ".jpg");
    }

    public void getInfoMember() {
        AppUtils.showProgressDialog(getContext(), getString(R.string.load));
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
                        Glide.with(getContext()).load(avatar_id).error(R.drawable.avatar).into(imgAva);
                    }
                    MyUtils.insertStringData(getContext(), Constants.IMAGE_ID, response.body().getMessage().getAvatar_id());
                    MyUtils.insertStringData(getContext(), Constants.NAME, response.body().getMessage().getName());
                    ((MainActivity) context).onDataPass();
                } else {
                    tvName.setText(getResources().getString(R.string.nodata));
                    tvEmail.setText(getResources().getString(R.string.nodata));
                    tvPhone.setText(getResources().getString(R.string.nodata));
                    tvAddress.setText(getResources().getString(R.string.nodata));
                }
            }

            @Override
            public void onFailure(Call<InfoMemberResponse> call, Throwable t) {
                AppUtils.hideProgressDialog(getContext());
            }
        });
    }


    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        updateImage = (UpdateImage) a;
    }

    public interface UpdateImage {
        void onDataPass();
    }


    private class TestSync extends AsyncTask<File, Void, String> {

        @Override
        protected String doInBackground(File... params) {
            File file = params[0];
            Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(getContext()));
            String img_url = "";
            if (file != null) {
                try {
                    Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                    img_url = (String) result.get("url");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return img_url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppUtils.showProgressDialog(getActivity(), getString(R.string.load));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("nguyenvanquang", nameNew + phoneNew + addressNew);
            updateProfileRequest(nameNew, s, phoneNew, addressNew);
        }
    }
}
