package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;

@Data
public class AddressDTO {

    private String city;

    private String street;

    private String number;

    private String postalCode;
}
