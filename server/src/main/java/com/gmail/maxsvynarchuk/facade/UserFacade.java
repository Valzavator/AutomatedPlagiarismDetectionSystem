package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.SignUpDto;

public interface UserFacade {

    boolean registerUser(SignUpDto user);

}
