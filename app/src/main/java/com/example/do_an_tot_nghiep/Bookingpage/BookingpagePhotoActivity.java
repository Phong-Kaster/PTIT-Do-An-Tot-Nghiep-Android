package com.example.do_an_tot_nghiep.Bookingpage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.BookingPhotoDelete;
import com.example.do_an_tot_nghiep.Container.BookingPhotoUpload;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Model.Photo;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.BookingPhotoRecyclerView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Phong-Kaster
 * @since 10-12-2022
 * Booking page Photo activity shows all photos that have been uploads by user in a booking.
 */
public class BookingpagePhotoActivity extends AppCompatActivity {

    private final String TAG = "Booking-page Photo Activity";
    private String bookingId;
    private String bookingStatus;
    private ImageButton btnBack;

    private Map<String, String> header;
    private LoadingScreen loadingScreen;
    private Dialog dialog;


    private BookingPhotoRecyclerView bookingPhotoAdapter;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private List<Photo>  list;

    private AppCompatButton btnUpload;
    private int photoId;

    private GlobalVariable globalVariable;

    private BookingpageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingpage_photo);


        setupComponent();
        setupViewModel();
        setupEvent();

    }

    /**
     * @since 10-12-2022
     */
    private void setupComponent()
    {
        if( getIntent().getStringExtra("bookingId") != null)
        {
            bookingId = getIntent().getStringExtra("bookingId");
            bookingStatus = getIntent().getStringExtra("bookingStatus");
        }
        else
        {
            System.out.println(TAG);
            System.out.println("Booking ID is empty !");
            Toast.makeText(this, getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
            this.finish();
        }

        globalVariable = (GlobalVariable) this.getApplication();
        header = globalVariable.getHeaders();
        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerView);
        layout = findViewById(R.id.bookingLinearLayout);

        btnUpload = findViewById(R.id.btnUpload);

        if(Objects.equals(bookingStatus, "processing"))
        {
            btnUpload.setVisibility(View.VISIBLE);
        }
        else
        {
            btnUpload.setVisibility(View.GONE);
        }
    }

    /**
     * @since 10-12-2022
     * setup event
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view->finish());

        btnUpload.setOnClickListener(view->{
            verifyStoragePermissions(this);

            Intent intent = new Intent();
            intent.setType("image/*");//allows any image file type. Change * to specific extension to limit it
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            openGalleryToPickPhoto.launch(intent);
        });
    }

    /**
     * @since 10-12-2022
     * setup view model
     */
    private void setupViewModel()
    {
        /*DECLARE*/
        viewModel = new ViewModelProvider(this).get(BookingpageViewModel.class);
        viewModel.instantiate();


        /*SEND REQUEST*/
        viewModel.bookingPhotoReadAll(header, bookingId);
        viewModel.getBookingPhotoReadAllResponse().observe(this, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    list = response.getData();
                    setupRecyclerView(list);
                }
                /*result == 0 => thong bao va thoat ung dung*/
                if( result == 0)
                {
                    dialog.announce();
                    dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                    dialog.btnOK.setOnClickListener(view->{
                        dialog.close();
                        finish();
                    });
                }

            }
            catch(Exception ex)
            {
                /*Neu truy van lau qua ma khong nhan duoc phan hoi thi cung dong ung dung*/
                System.out.println(TAG);
                System.out.println(ex);

                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    finish();
                });
            }
        });/*end SEND REQUEST*/


        /*ANIMATION*/
        viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean ) loadingScreen.start();
            else loadingScreen.stop();
        });
    }

    /**
     * @since 10-12-2022
     * setup recycler view
     */
    private void setupRecyclerView(List<Photo> list)
    {
        bookingPhotoAdapter = new BookingPhotoRecyclerView(this, list);
        recyclerView.setAdapter(bookingPhotoAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        if(Objects.equals(bookingStatus, "processing"))
        {
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(recyclerView);
        }
    }

    /**
     * @since 10-12-2022
     * call back
     */
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAbsoluteAdapterPosition();
            photoId = list.get(position).getId();

            removePhotoFromList(photoId);
            list.remove(position);
            bookingPhotoAdapter.notifyItemRemoved(position);// trigger event one photo deleted from list
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(BookingpagePhotoActivity.this, R.color.colorRed))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    /**
     * @since 10-12-2022
     * send request to remove photo from list
     */
    private void removePhotoFromList(int photoId)
    {
        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<BookingPhotoDelete> container = api.bookingPhotoDelete(header, photoId);

        /*Step 4*/
        container.enqueue(new Callback<BookingPhotoDelete>() {
            @Override
            public void onResponse(@NonNull Call<BookingPhotoDelete> call, @NonNull Response<BookingPhotoDelete> response) {
                if(response.isSuccessful())
                {
                    BookingPhotoDelete content = response.body();
                    assert content != null;
                    int result = content.getResult();
                    if( result == 1)
                    {
                        Snackbar snackbar = Snackbar.make(layout, "Success", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    else
                    {
                        Snackbar snackbar = Snackbar.make(layout, "Fail", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingPhotoDelete> call, @NonNull Throwable t) {
                System.out.println(TAG);
                System.out.println("Error from function removePhotoFromList(): " + t.getMessage());
            }
        });
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     */
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * open Gallery To Pick Photo
     */
    private final ActivityResultLauncher<Intent> openGalleryToPickPhoto = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if( result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        assert data != null;
                        Uri uri = data.getData();

                        uploadPhotoToServer(uri);
                    }
                    else
                    {

                    }
                }
            });

    private void uploadPhotoToServer(Uri uri)
    {
        /*Step 1 - set up file path*/
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri,
                projection, null, null, null);

        int columnIndex = cursor.getColumnIndex(projection[0]);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();


        /*Step 2 - configure new request*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
//        System.out.println("booking id: " + bookingId);
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), bookingId);

        File file = new File(Uri.parse(filePath).toString());
        RequestBody requestBodyFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part actualFile = MultipartBody.Part.createFormData("file", file.getName(), requestBodyFile);


        String accessToken = globalVariable.getAccessToken();
        String type = "Patient";
        Call<BookingPhotoUpload> container = api.bookingPhotoUpload(accessToken, type, id, actualFile);

        /*Step 4*/
        container.enqueue(new Callback<BookingPhotoUpload>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<BookingPhotoUpload> call, @NonNull Response<BookingPhotoUpload> response) {
                if(response.isSuccessful())
                {
                    BookingPhotoUpload content = response.body();
                    assert content != null;

                    int result = content.getResult();
                    String msg = content.getMsg();

                    if( result == 1)
                    {
                        viewModel.bookingPhotoReadAll(header, bookingId);
                        bookingPhotoAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        dialog.announce();
                        dialog.btnOK.setOnClickListener(view->dialog.close());
                        dialog.show(R.string.attention, msg, R.drawable.ic_info);
                    }
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingPhotoUpload> call, @NonNull Throwable t) {
                System.out.println(TAG);
                System.out.println("ERROR");
                t.printStackTrace();
            }
        });
    }
}