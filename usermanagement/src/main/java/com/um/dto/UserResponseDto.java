package com.um.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class UserResponseDto {
    
    private long userId;
    private String userName;
    private String userPhone;
    
}
