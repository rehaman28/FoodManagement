package com.dm.model;

public enum DeliveryStatus {

    ASSIGNED,
    ACCEPTED,
    COLLECTED,
    IN_TRANSIT,
    DELIVERED,
    REFUSED;

    public static boolean isValidTransition(
            DeliveryStatus currentStatus,
            DeliveryStatus requestedStatus) {
        return switch (currentStatus) {

            case ASSIGNED ->
                requestedStatus == DeliveryStatus.ACCEPTED ||
                        requestedStatus == DeliveryStatus.REFUSED;

            case ACCEPTED ->
                requestedStatus == DeliveryStatus.COLLECTED ||
                        requestedStatus == DeliveryStatus.REFUSED;

            case COLLECTED ->
                requestedStatus == DeliveryStatus.IN_TRANSIT;

            case IN_TRANSIT ->
                requestedStatus == DeliveryStatus.DELIVERED;

            case DELIVERED ->
                false;

            case REFUSED ->
                false;
        };

    }
}
