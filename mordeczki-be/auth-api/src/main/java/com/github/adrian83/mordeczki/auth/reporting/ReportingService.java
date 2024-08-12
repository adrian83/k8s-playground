package com.github.adrian83.mordeczki.auth.reporting;

import org.springframework.stereotype.Service;

@Service
public class ReportingService {

    public void reportUserRegisteringException(Throwable t) {
    }

    public void reportStoringRegistrationDataException(Throwable t) {
    }

    public void reportResetPasswordException(Throwable t) {
    }

    public void reportStoringResetPasswordDataException(Throwable t) {
    }
}
