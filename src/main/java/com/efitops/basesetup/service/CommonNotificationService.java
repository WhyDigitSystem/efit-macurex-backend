package com.efitops.basesetup.service;

import org.springframework.stereotype.Service;

@Service
public interface CommonNotificationService {
    void generateNotification(String screenCode, Long id, Object oldObj, Object newObj);

}