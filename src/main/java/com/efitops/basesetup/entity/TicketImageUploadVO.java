package com.efitops.basesetup.entity;

import org.springframework.web.multipart.MultipartFile;

public class TicketImageUploadVO {

    private Long ticketId;
    private MultipartFile file;

    public TicketImageUploadVO() {}

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
