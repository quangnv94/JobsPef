package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import duan2.jobspef.luyquangdat.com.myapplication.entity.CategoryResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.SimpleResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class FragmentCreateJobs extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private Drawer drawer;
    private Context context;
    private ProgressDialog progDialog = null;
    private ArrayList<CategoryResponse> listCategory = new ArrayList<>();
    private ArrayList<String> listNameCategory = new ArrayList<>();
    private EditText edtPhone;
    private EditText edtTitle;
    private EditText edtContent;
    private EditText edtBenefeed;
    private EditText edtRequiment;
    private EditText edtTimelimited;
    private EditText edtAddress;

    private Spinner spCategory;
    private Button btnClear;
    private Button btnPost;

    private String title;
    private String content;
    private String benifed;
    private String requirment;
    private String timeLimit;
    private String categoryId;
    private String phone;
    private String address;
    private int userId;
    private ArrayAdapter<String> karant_adapter;
    private static int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 999;
    private ImageView imgContent;
    private File file;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_post, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        context = rootView.getContext();
        initController(rootView);
        checkPermission();
        return rootView;
    }

    public void initController(View v) {
        edtPhone = v.findViewById(R.id.edtContact);
        edtTitle = v.findViewById(R.id.edtTitle);
        edtContent = v.findViewById(R.id.edtContent);
        edtBenefeed = v.findViewById(R.id.edtBenifed);
        edtRequiment = v.findViewById(R.id.edtRequei);
        edtTimelimited = v.findViewById(R.id.edtTime);
        edtAddress = v.findViewById(R.id.edtAddress);

        btnClear = v.findViewById(R.id.btnReset);
        btnPost = v.findViewById(R.id.btnPost);
        btnClear.setOnClickListener(this);
        btnPost.setOnClickListener(this);

        imgContent = v.findViewById(R.id.imgContent);
        imgContent.setOnClickListener(this);

        spCategory = v.findViewById(R.id.spCategory);

        getCategory();
        listNameCategory.add(0, getString(R.string.category_select));
        karant_adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, listNameCategory);
        spCategory.setAdapter(karant_adapter);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    categoryId = "";
                } else {
                    categoryId = listCategory.get(i - 1).getId_category();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                categoryId = "";
            }
        });

        ImageView imgBack = toolbar.findViewById(R.id.imgCreatePost);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCreateJobs()).addToBackStack(null).commit();

            }
        });
        ImageView imgMore = toolbar.findViewById(R.id.imgMore);
        imgMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(context);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage(getString(R.string.loading));
        progDialog.show();
    }


    public void validateForum() {
        phone = edtPhone.getText().toString().trim();
        content = edtContent.getText().toString().trim();
        requirment = edtRequiment.getText().toString().trim();
        benifed = edtBenefeed.getText().toString().trim();
        title = edtTitle.getText().toString().trim();
        timeLimit = edtTimelimited.getText().toString().trim();
        address = edtAddress.getText().toString().trim();

        if (title.equals("")) {
            edtTitle.setError(getString(R.string.title_empty));
            edtTitle.requestFocus();
        } else if (content.equals("")) {
            edtContent.setError(getString(R.string.content_empty));
            edtContent.requestFocus();
        } else if (categoryId.equals("")) {
            MyUtils.showToast(getContext(), getString(R.string.category_empty));
        } else if (requirment.equals("")) {
            edtRequiment.setError(getString(R.string.requiment_empty));
            edtRequiment.requestFocus();
        } else if (benifed.equals("")) {
            edtBenefeed.setError(getString(R.string.benifed_empty));
            edtBenefeed.requestFocus();
        } else if (timeLimit.equals("")) {
            edtTimelimited.setError(getString(R.string.time_empty));
            edtTimelimited.requestFocus();
        } else if (phone.equals("")) {
            edtPhone.setError(getString(R.string.invail_phone));
            edtPhone.requestFocus();
        } else if (address.equals("")) {
            edtAddress.setError(getString(R.string.invail_address));
            edtAddress.requestFocus();
        } else {
            UploadImage uploadImage = new UploadImage();
            uploadImage.execute(file);
        }
    }

    private void getCategory() {
        showProgressDialog();
        ConnectServer.getResponseAPI().getCategory().enqueue(new Callback<ArrayList<CategoryResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryResponse>> call, Response<ArrayList<CategoryResponse>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    listCategory = response.body();
                    for (int i = 0; i < listCategory.size(); i++) {
                        listNameCategory.add(listCategory.get(i).getCategory_name());
                        karant_adapter.notifyDataSetChanged();
                    }
                } else {
                    MyUtils.showToast(getContext(), getString(R.string.oops_something_gone_wrong));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryResponse>> call, Throwable t) {
                dismissProgressDialog();

            }
        });
    }


    private void dismissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    public void actionReset() {
        edtTitle.setText("");
        edtContent.setText("");
        edtBenefeed.setText("");
        edtRequiment.setText("");
        spCategory.setSelection(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgContent:
                seletePhotoAction();
                break;
            case R.id.btnPost:
                validateForum();
                break;
            case R.id.btnReset:
                actionReset();
                break;
        }

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
                        imgContent.setImageBitmap(photo);
                        file = MyUtils.saveBitmapToFile(MyUtils.resize(photo, 800, 800), "picture" + ".jpg");
                        //image_edit = new TypedFile("multipart/form-data", file);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        displayImageFromGallery(imageReturnedIntent, imgContent);
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_LONG)
                    .show();
        }

    }


    private class UploadImage extends AsyncTask<File, Void, String> {

        @Override
        protected String doInBackground(File... params) {
            File file = params[0];
            Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(getContext()));
            String img_url = "";
            try {
                Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                img_url = (String) result.get("url");
            } catch (IOException e) {
                e.printStackTrace();
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
            requestCreatePost(s);

        }
    }

    public void requestCreatePost(String image) {
        String token = MyUtils.getStringData(getContext(), Constants.TOKEN);
        ConnectServer.getResponseAPI().updateCreatePost(token, title, categoryId, image, benifed, timeLimit, phone, address, requirment).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                AppUtils.hideProgressDialog(getActivity());
                showDialogConfirm(R.drawable.success, R.style.DialogAnimationBottom,
                        getString(R.string.create_post_success), getString(R.string.success));
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                AppUtils.hideProgressDialog(getActivity());
                showDialogConfirm(R.drawable.success, R.style.DialogAnimationBottom,
                        "", getString(R.string.error));
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
        // image_edit = new TypedFile("multipart/form-data", file);
    }

    public void showDialogConfirm(int icon, int animationSource, String message, String title) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(icon);

        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = animationSource;
        alertDialog.show();

    }
}
