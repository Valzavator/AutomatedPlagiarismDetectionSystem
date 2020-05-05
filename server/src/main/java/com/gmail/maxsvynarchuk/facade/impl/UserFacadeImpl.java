package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.UserFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.presentation.payload.request.SignUpDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.UserProfileDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.UserProfileVcsDto;
import com.gmail.maxsvynarchuk.service.UserService;
import lombok.AllArgsConstructor;

@Facade
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;

    private final Converter<SignUpDto, User> signUpDtoToUser;
    private final Converter<User, UserProfileDto> userToUserProfileDto;
    private final Converter<User, UserProfileVcsDto> userToUserProfileVcsDto;

    @Override
    public boolean registerUser(SignUpDto signUpDto) {
        User user = signUpDtoToUser.convert(signUpDto);
        return userService.registerUser(user);
    }

    @Override
    public UserProfileDto getUserProfile(Long userId) {
        User user = userService.getRequiredUserById(userId);
        UserProfileDto userProfileDto = userToUserProfileDto.convert(user);

        //TODO - calculate real statistic
        userProfileDto.setCoursesCount(1);
        userProfileDto.setGroupsCount(2);
        userProfileDto.setTasksCount(2);
        userProfileDto.setStudentsCount(5);
        userProfileDto.setStudentsRepositoriesCount(5);

        return userProfileDto;
    }

    @Override
    public UserProfileVcsDto getUserProfileVcs(Long userId) {
        User user = userService.getRequiredUserById(userId);
        return userToUserProfileVcsDto.convert(user);
    }

}
