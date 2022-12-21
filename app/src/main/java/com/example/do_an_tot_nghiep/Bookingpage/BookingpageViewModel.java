package com.example.do_an_tot_nghiep.Bookingpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.BookingPhotoReadAll;
import com.example.do_an_tot_nghiep.Container.BookingReadByID;
import com.example.do_an_tot_nghiep.Container.DoctorReadByID;
import com.example.do_an_tot_nghiep.Container.ServiceReadByID;
import com.example.do_an_tot_nghiep.Repository.BookingPhotoRepository;
import com.example.do_an_tot_nghiep.Repository.BookingRepository;
import com.example.do_an_tot_nghiep.Repository.DoctorRepository;
import com.example.do_an_tot_nghiep.Repository.ServiceRepository;

import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 23-11-2022
 * Booking-page ViewModel
 */
public class BookingpageViewModel extends ViewModel {

    private MutableLiveData<ServiceReadByID> serviceReadByIdResponse;
    private MutableLiveData<BookingReadByID> bookingReadByIdResponse;

    private MutableLiveData<Boolean> animation;
    private ServiceRepository serviceRepository;
    private BookingRepository bookingRepository;
    private BookingPhotoRepository bookingPhotoRepository;
    private DoctorRepository doctorRepository;

    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }


    /**
     * @since 23-11-2022
     * instantiate repository
     */
    public void instantiate()
    {
        if( serviceRepository == null)
        {
            serviceRepository = new ServiceRepository();
        }
        if( bookingRepository == null)
        {
            bookingRepository = new BookingRepository();
        }
        if( bookingPhotoRepository == null)
        {
            bookingPhotoRepository = new BookingPhotoRepository();
        }
        if( doctorRepository == null)
        {
            doctorRepository = new DoctorRepository();
        }
    }

    /************************SERVICE READ BY ID***************************/
    public MutableLiveData<ServiceReadByID> getServiceReadByIdResponse() {
        if( serviceReadByIdResponse == null)
        {
            serviceReadByIdResponse = new MutableLiveData<>();
        }
        return serviceReadByIdResponse;
    }

    public void serviceReadById(Map<String, String> header, String serviceId)
    {
        serviceReadByIdResponse = serviceRepository.readByID(header, serviceId);
        animation = serviceRepository.getAnimation();
    }

    /* ***********************SERVICE READ BY ID**************************
     * we do not use View model to observe send POST request to server
     * */
//    private BookingRepository bookingRepository;
//    private MutableLiveData<BookingCreate> bookingCreateResponse;
//    public MutableLiveData<BookingCreate> getBookingCreateResponse() {
//        if(bookingCreateResponse == null)
//        {
//            bookingCreateResponse = new MutableLiveData<>();
//        }
//        return bookingCreateResponse;
//    }
//    public void bookingCreate(Map<String, String> header, Map<String, String> body)
//    {
//        bookingRepository.create(header, body);
//        bookingCreateResponse = bookingRepository.create(header, body);
//
//        animation = bookingRepository.getAnimation();
//    }

    /************************BOOKING READ BY ID***************************/
    public MutableLiveData<BookingReadByID> getBookingReadByIdResponse() {
        if( bookingReadByIdResponse == null )
        {
            bookingReadByIdResponse = new MutableLiveData<>();
        }
        return bookingReadByIdResponse;
    }
    public void bookingReadByID(Map<String, String> header, String bookingId)
    {
        bookingReadByIdResponse = bookingRepository.readByID(header, bookingId);
        animation = bookingRepository.getAnimation();
    }


    /************************BOOKING PHOTO - READ ALL***************************/
    private MutableLiveData<BookingPhotoReadAll> bookingPhotoReadAllResponse;
    public MutableLiveData<BookingPhotoReadAll> getBookingPhotoReadAllResponse(){
        if( bookingPhotoReadAllResponse == null )
        {
            bookingPhotoReadAllResponse = new MutableLiveData<>();
        }
        return bookingPhotoReadAllResponse;
    }
    public void bookingPhotoReadAll(Map<String, String> header, String bookingId)
    {
        bookingPhotoReadAllResponse = bookingPhotoRepository.readAll(header, bookingId);
        animation = bookingPhotoRepository.getAnimation();
    }

    /************************DOCTOR - READ BY ID***************************/
    private MutableLiveData<DoctorReadByID> doctorReadById = new MutableLiveData<>();
    public MutableLiveData<DoctorReadByID> getDoctorReadByIdResponse() {
        return doctorReadById;
    }
    public void doctorReadByID(Map<String, String> header, String doctorId)
    {
        animation = doctorRepository.getAnimation();
        doctorReadById = doctorRepository.readById(header, doctorId);
    }
}
