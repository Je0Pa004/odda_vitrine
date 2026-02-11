package net.essossolam.oddatech.service;

import net.essossolam.oddatech.dto.LoginRequestDTO;
import net.essossolam.oddatech.dto.LoginResponseDTO;

public interface UserService {
    LoginResponseDTO authenticate(LoginRequestDTO request);
}
