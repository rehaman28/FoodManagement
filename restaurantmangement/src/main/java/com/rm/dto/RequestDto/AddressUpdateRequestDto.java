package com.rm.dto.RequestDto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateRequestDto {

    @Size(min = 1, max = 100)
    private String landmark;

    @Size(min = 2, max = 50)
    private String city;

    @Size(min = 2, max = 50)
    private String state;

    @Pattern(
        regexp = "^\\d{6}$",
        message = "Pincode must contain 6 digits"
    )
    private String pincode;
}
