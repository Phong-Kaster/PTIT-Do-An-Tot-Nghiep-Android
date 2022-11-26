package com.example.do_an_tot_nghiep.Notificationpage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.NotificationMarkAllAsRead;
import com.example.do_an_tot_nghiep.Container.NotificationMarkAsRead;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Homepage.HomepageActivity;
import com.example.do_an_tot_nghiep.Model.Notification;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.NotificationRecyclerView;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Phong-Kaster
 * @since 18-11-2022
 */
public class NotificationFragment extends Fragment implements NotificationRecyclerView.Callback {

    private final String TAG = "NotificationFragment";

    private RecyclerView recyclerView;

    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private Context context;
    private Activity activity;

    private TextView txtMarkAllAsRead;
    private NotificationViewModel viewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private Map<String, String> header;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        setupComponent(view);
        setupViewModel();
        setupEvent();

        return view;
    }


    /**
     * @since 24-11-2022
     * @param view is the view of fragment
     */
    private void setupComponent(View view)
    {
        recyclerView = view.findViewById(R.id.recyclerView);
        GlobalVariable globalVariable = (GlobalVariable) requireActivity().getApplication();


        context = requireContext();
        activity = requireActivity();

        dialog = new Dialog(context);
        loadingScreen = new LoadingScreen(activity);

        txtMarkAllAsRead = view.findViewById(R.id.txtMarkAllAsRead);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        header = globalVariable.getHeaders();
    }

    /**
     * @since 24-11-2022
     */
    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        viewModel.instantiate();

        /*Step 2 - prepare header & parameters*/
        viewModel.readAll(header);

        /*Step 3 - listen for response*/
        viewModel.getReadAllResponse().observe((LifecycleOwner) context, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {


                    /*print notification*/
                    List<Notification> list = response.getData();
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
                dialog.announce();
                dialog.show(R.string.attention, this.getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    activity.finish();
                });
            }
        });

        /*Step 4 - get animation*/
        viewModel.getAnimation().observe((LifecycleOwner) context, aBoolean -> {
            if( aBoolean )
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });
    }

    private void setupEvent()
    {
        /*TXT MARK ALL NOTIFICATION AS READ*/
        txtMarkAllAsRead.setOnClickListener(view-> {
            loadingScreen.start();
            markAllAsRead();
        });

        /*SWIPE REFRESH LAYOUT*/
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.readAll(header);
            HomepageActivity.getInstance().setNumberOnNotificationIcon();
            swipeRefreshLayout.setRefreshing(false);
        });
    }


    /**
     * @since 24-11-2022
     * @param list is the list of notifications
     */
    private void setupRecyclerView(List<Notification> list)
    {
        NotificationRecyclerView notificationAdapter = new NotificationRecyclerView(context, list, this);
        recyclerView.setAdapter(notificationAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * @since 24-11-2022
     * @param notificationId is the is of notification which is being marked as read
     */
    @Override
    public void markAsRead(String notificationId) {

        if(TextUtils.isEmpty(notificationId) )
        {
            return;
        }

        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<NotificationMarkAsRead> container = api.notificationMarkAsRead(header, notificationId);

        /*Step 4*/
        container.enqueue(new Callback<NotificationMarkAsRead>() {
            @Override
            public void onResponse(@NonNull Call<NotificationMarkAsRead> call, @NonNull Response<NotificationMarkAsRead> response) {
                /*Step 4 - Case 1 - if successful, update the number of unread notifications*/
                if(response.isSuccessful())
                {
                    NotificationMarkAsRead content = response.body();
                    assert content != null;
                    int result = content.getResult();
                    if( result == 1)
                    {
                        HomepageActivity.getInstance().setNumberOnNotificationIcon();
                    }
                }
                /*Step 4 - Case 2 - if failed, show message*/
                if(response.errorBody() != null)
                {
                    System.out.println(response);
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println(TAG);
                        System.out.println( "Error: " + e.getMessage() );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationMarkAsRead> call, @NonNull Throwable t) {
                System.out.println("Notification Fragment markAsRead - Read All - error: " + t.getMessage());
            }
        });
    }


    /**
     * @since 24-11-2022
     * mark all notifications as read
     */
    public void markAllAsRead()
    {
        /*Step 1 - setup Retrofit*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 2 - prepare HEADER*/

        /*Step 3 - make request*/
        Call<NotificationMarkAllAsRead> container = api.notificationMarkAllAsRead(header);

        /*Step 4 - listen for response*/
        container.enqueue(new Callback<NotificationMarkAllAsRead>() {
            @Override
            public void onResponse(@NonNull Call<NotificationMarkAllAsRead> call, @NonNull Response<NotificationMarkAllAsRead> response) {
                loadingScreen.stop();
                if(response.isSuccessful())
                {
                    NotificationMarkAllAsRead content = response.body();
                    assert content != null;
                    int result = content.getResult();
                    if( result == 1)
                    {
                        /*update recycler view & the number of unread notification*/
                        viewModel.readAll(header);
                        HomepageActivity.getInstance().setNumberOnNotificationIcon();


                        /*show successful message dialog*/
                        Dialog dialog = new Dialog(context);
                        dialog.announce();
                        dialog.btnOK.setOnClickListener(view->dialog.close());
                        String title = context.getString(R.string.success);
                        String message = context.getString(R.string.successful_action);
                        dialog.show(title, message, R.drawable.ic_check);
                    }
                }
                if(response.errorBody() != null)
                {
                    System.out.println(response);
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( "Error: " + e.getMessage() );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationMarkAllAsRead> call, @NonNull Throwable t) {
                System.out.println("Notification Fragment markAllAsRead - Read All - error: " + t.getMessage());
            }
        });
    }
}