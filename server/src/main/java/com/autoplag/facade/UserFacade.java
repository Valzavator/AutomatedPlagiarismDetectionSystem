package com.autoplag.facade;

import com.autoplag.presentation.payload.request.SignUpDto;
import com.autoplag.presentation.payload.response.UserProfileDto;
import com.autoplag.presentation.payload.response.UserProfileVcsDto;

public interface UserFacade {

    boolean registerUser(SignUpDto user);

    UserProfileDto getUserProfile(Long userId);

    UserProfileVcsDto getUserProfileVcs(Long userId);

}
