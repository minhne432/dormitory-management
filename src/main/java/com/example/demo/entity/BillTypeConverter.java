package com.example.demo.entity;

import jakarta.persistence.*;
@Converter(autoApply = true)
public class BillTypeConverter implements AttributeConverter<Bill.BillType, String> {

    @Override
    public String convertToDatabaseColumn(Bill.BillType attribute) {
        if (attribute == null) {
            return null;
        }
        switch(attribute) {
            case PHONG:
                return "phòng";
            case DIEN_NUOC:
                return "điện-nước";
            case DICH_VU:
                return "dịch-vụ";
            default:
                throw new IllegalArgumentException("Unknown BillType: " + attribute);
        }
    }

    @Override
    public Bill.BillType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch(dbData) {
            case "phòng":
                return Bill.BillType.PHONG;
            case "điện-nước":
                return Bill.BillType.DIEN_NUOC;
            case "dịch-vụ":
                return Bill.BillType.DICH_VU;
            default:
                throw new IllegalArgumentException("Unknown BillType from database: " + dbData);
        }
    }
}
