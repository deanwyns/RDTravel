package com.realdolmen.rdtravel.views;

import com.realdolmen.rdtravel.domain.Booking;
import com.realdolmen.rdtravel.persistence.BookingDAO;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;


@Named
@ViewScoped
public class BookingOverviewView implements Serializable {
    @Inject
    private BookingDAO bookingDAO;

    private List<Booking> bookings;
    private List<Booking> filteredBookings;
    private List<Booking> selectedBookings;

    @PostConstruct
    public void init() {
        bookings = bookingDAO.findAll();
    }

    public boolean filterByTimeBefore(Object value, Object filter, Locale locale) {
        if (filter == null) {
            return true;
        }

        LocalDate valueTime = LocalDate.parse(value.toString());
        LocalDate filterTime = LocalDate.parse(filter.toString());

        return filterTime.isBefore(valueTime);
    }

    public boolean filterByTimeAfter(Object value, Object filter, Locale locale) {
        if (filter == null) {
            return true;
        }

        LocalDate valueTime = LocalDate.parse(value.toString());
        LocalDate filterTime = LocalDate.parse(filter.toString());

        return filterTime.isAfter(valueTime);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Booking> getFilteredBookings() {
        return filteredBookings;
    }

    public void setFilteredBookings(List<Booking> filteredBookings) {
        this.filteredBookings = filteredBookings;
    }

    public List<Booking> getSelectedBookings() {
        return selectedBookings;
    }

    public void setSelectedBookings(List<Booking> selectedBookings) {
        this.selectedBookings = selectedBookings;
    }
}
