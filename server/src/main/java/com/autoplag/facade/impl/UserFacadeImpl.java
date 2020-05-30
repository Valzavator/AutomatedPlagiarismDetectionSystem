package com.autoplag.facade.impl;

import com.autoplag.facade.Facade;
import com.autoplag.facade.UserFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.User;
import com.autoplag.presentation.payload.request.SignUpDto;
import com.autoplag.presentation.payload.response.UserProfileDto;
import com.autoplag.presentation.payload.response.UserProfileVcsDto;
import com.autoplag.service.UserService;
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
        User user = userService.getUserById(userId);
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
        User user = userService.getUserById(userId);
        return userToUserProfileVcsDto.convert(user);
    }

}
