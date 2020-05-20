package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentDto;

public interface StudentFacade {

    PagedDto<StudentDto> getStudentsByCreatorId(Long creatorId, int page, int size);

}
