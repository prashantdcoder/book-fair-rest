package com.shanky.bookfairrest.enums;

public enum InvitationStatus {

    PENDING("Pending"),
    ACCEPTED("Accepted"),
    DECLINED("Declined");

    String value;

    InvitationStatus(String value) {
        this.value = value;
    }
}
