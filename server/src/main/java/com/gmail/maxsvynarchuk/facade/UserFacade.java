package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.SignUpDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.UserProfileDto;

public interface UserFacade {

    boolean registerUser(SignUpDto user);

    UserProfileDto getUserProfile(Long userId);

}
