package com.github.adrian83.mordeczki.auth.reporting;

import org.springframework.stereotype.Service;

@Service
public class ReportingService {

    public void reportUserRegisteringException(Throwable ex) {
    }

    public void reportStoringRegistrationDataException(Exception ex) {
    }
}
