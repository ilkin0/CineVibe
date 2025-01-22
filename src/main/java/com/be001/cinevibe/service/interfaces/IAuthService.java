package com.be001.cinevibe.service.interfaces;

import com.be001.cinevibe.dto.RegisterRequestDTO;
import com.be001.cinevibe.dto.UserResponseDTO;

public interface IAuthService {
    UserResponseDTO register(RegisterRequestDTO request);
}
