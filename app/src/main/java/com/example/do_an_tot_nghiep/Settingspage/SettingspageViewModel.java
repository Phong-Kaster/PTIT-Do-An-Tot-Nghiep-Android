package com.example.do_an_tot_nghiep.Settingspage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.AppointmentReadAll;
import com.example.do_an_tot_nghiep.Container.BookingReadAll;
import com.example.do_an_tot_nghiep.Repository.AppointmentQueueRepository;
import com.example.do_an_tot_nghiep.Repository.AppointmentRepository;
import com.example.do_an_tot_nghiep.Repository.BookingRepository;

import java.util.Map;

/**
 * @since 01-12-2022
 * settings-page view model
 */
public class SettingspageViewModel extends ViewModel {

    private MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    private AppointmentRepository appointmentRepository;
    private BookingRepository bookingRepository;

    public void instantiate()
    {
        if( appointmentRepository == null)
        {
            appointmentRepository = new AppointmentRepository();
        }
        if( bookingRepository == null);
        {
            bookingRepository = new BookingRepository();
        }
    }

    /************************ APPOINTMENTS - READ ALL ***************************/
    private MutableLiveData<AppointmentReadAll> readAllResponse = new MutableLiveData<>();
    public MutableLiveData<AppointmentReadAll> getReadAllResponse() {
        return readAllResponse;
    }
    public void readAll(Map<String, String> header, Map<String, String> parameters)
    {
        animation = appointmentRepository.getAnimation();
        readAllResponse = appointmentRepository.readAll(header, parameters);
    }

    /************************ BOOKING - READ ALL ***************************/
    private MutableLiveData<BookingReadAll> bookingReadAll = new MutableLiveData<>();
    public MutableLiveData<BookingReadAll> getBookingReadAll() {
        return bookingReadAll;
    }
    public void bookingReadAll(Map<String, String> header, Map<String, String> parameters)
    {
        animation = bookingRepository.getAnimation();
        bookingReadAll = bookingRepository.readAll(header, parameters);
    }
}
