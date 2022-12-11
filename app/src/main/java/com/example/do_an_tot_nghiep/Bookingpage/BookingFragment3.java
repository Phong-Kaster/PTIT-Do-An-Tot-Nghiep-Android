package com.example.do_an_tot_nghiep.Bookingpage;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
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
 * @since 09-12-2022
 * flow: Fragment 1 -> Fragment 3 -> Fragment 2
 */
public class BookingFragment3 extends Fragment {

    private final String TAG = "Booking Fragment 3";
    private String bookingId;

    private AppCompatButton btnNext;
    private AppCompatButton btnUpload;
    private RecyclerView recyclerView;
    private BookingPhotoRecyclerView adapter;

    private LinearLayout layout;
    private Context context;
    private Activity activity;

    private Map<String, String> header;
    private GlobalVariable globalVariable;
    private LoadingScreen loadingScreen;
    private Dialog dialog;

    private BookingpageViewModel viewModel;
    private List<Photo> list;
    private int photoId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking3, container, false);

        setupComponent(view);
        setupViewModel();
        setupEvent();

        return view;
    }

    /**
     * @since 11-12-2022
     * setup component
     */
    private void setupComponent(View view)
    {
        Bundle bundle = getArguments();
        assert bundle != null;
        bookingId = (String) bundle.get("bookingId");

        context = requireContext();
        activity = requireActivity();
        globalVariable = (GlobalVariable) activity.getApplication();
        header = globalVariable.getHeaders();
        dialog = new Dialog(context);
        loadingScreen = new LoadingScreen(activity);

        recyclerView = view.findViewById(R.id.recyclerView);
        layout = view.findViewById(R.id.linearLayout);
        btnUpload = view.findViewById(R.id.btnUpload);
        btnNext = view.findViewById(R.id.btnNext);
    }

    /**
     * @since 11-12-2022
     * setup event
     */
    private void setupEvent()
    {
        /*BUTTON UPLOAD*/
        btnUpload.setOnClickListener(view->{
            verifyStoragePermissions(activity);

            Intent intent = new Intent();
            intent.setType("image/*");//allows any image file type. Change * to specific extension to limit it
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            openGalleryToPickPhoto.launch(intent);
        });/*end BUTTON UPLOAD*/


        /*BUTTON NEXT*/
        btnNext.setOnClickListener(view->{
            String fragmentTag = "bookingFragment2";
            BookingFragment2 nextFragment = new BookingFragment2();

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, nextFragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .commit();
        });/*end BUTTON NEXT*/
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
        viewModel.getBookingPhotoReadAllResponse().observe((LifecycleOwner) context, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    list = response.getData();
                    System.out.println(TAG);
                    System.out.println("photo size: " + list.size());
                    setupRecyclerView(list);
                }
                /*result == 0 => thong bao va thoat ung dung*/
                if( result == 0)
                {
                    dialog.announce();
                    dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                    dialog.btnOK.setOnClickListener(view->{
                        dialog.close();
                        activity.finish();
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
                    activity.finish();
                });
            }
        });/*end SEND REQUEST*/


        /*ANIMATION*/
        viewModel.getAnimation().observe((LifecycleOwner) context, aBoolean -> {
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
        adapter = new BookingPhotoRecyclerView(context, list);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
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
            adapter.notifyItemRemoved(position);// trigger event one photo deleted from list
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
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
                    Snackbar snackbar;
                    if( result == 1)
                    {
                        snackbar = Snackbar.make(layout, "Success", Snackbar.LENGTH_SHORT);
                    }
                    else
                    {
                        snackbar = Snackbar.make(layout, "Fail", Snackbar.LENGTH_SHORT);
                    }
                    snackbar.show();
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
     * @since 11-12-2022
     * open Gallery To Pick Photo
     */
    private final ActivityResultLauncher<Intent> openGalleryToPickPhoto = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if( result.getResultCode() == RESULT_OK)
                {
                    Intent data = result.getData();
                    assert data != null;
                    Uri uri = data.getData();

                    uploadPhotoToServer(uri);
                }
                else
                {
                    System.out.println(TAG);
                    System.out.println("Error - openGalleryToPickPhoto");
                }
            });

    /**
     * @since 11-12-2022
     * @param uri is uri of the photo that is being sending to the server
     * Create a HTTP request to upload photo.
     */
    private void uploadPhotoToServer(Uri uri)
    {
        /*Step 1 - set up file path*/
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(uri,
                projection, null, null, null);

        int columnIndex = cursor.getColumnIndex(projection[0]);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();


        /*Step 2 - configure new request*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        System.out.println("booking id: " + bookingId);
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
                        adapter.notifyDataSetChanged();
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