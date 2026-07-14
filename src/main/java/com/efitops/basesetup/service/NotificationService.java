package com.efitops.basesetup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.entity.NotificationVO;

@Service

public interface NotificationService {

	void createNotification(Long userId, Long auctionId, String message, String type);

	void markAsRead(Long notificationId);

	List<NotificationVO> getNotifications(Long userId);

	void deleteNotification(Long notificationId);

	void clearAll(Long userId);

}
