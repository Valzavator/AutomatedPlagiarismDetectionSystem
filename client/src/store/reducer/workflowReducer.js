import * as types from '../action/actionTypes';

const INITIAL_STATE = {
    courses: {
        content: [],
        page: 0,
        totalPages: 0
    },
    groups: {
        content: [],
        page: 0,
        totalPages: 0
    },
    activeCourse: {
        id: -1,
        name: 'NOT_LOADED',
        description: 'NOT_LOADED',
        creationDate: 'NOT_LOADED'
    },
    activeGroup: {
        id: -1,
        name: 'NOT_LOADED',
        creationDate: 'NOT_LOADED',
        courseId: -1,
        courseName: 'NOT_LOADED',
        studentGroups: [],
        taskGroups: []
    }
};

export default function authReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case types.LOAD_COURSES:
            return Object.assign({}, state, {
                courses: action.courses
            });
        case types.LOAD_SPECIFIC_COURSE:
            return Object.assign({}, state, {
                activeCourse: action.activeCourse
            });
        case types.LOAD_GROUPS:
            return Object.assign({}, state, {
                groups: action.groups
            });
        case types.LOAD_SPECIFIC_GROUPS:
            return Object.assign({}, state, {
                activeGroup: action.activeGroup
            });
        case types.ADD_TASK_GROUP_TO_ACTIVE_GROUP:
            let activeGroupToTaskGroup = Object.assign({}, state.activeGroup);
            activeGroupToTaskGroup.taskGroups = [...activeGroupToTaskGroup.taskGroups, action.newTaskGroup];
            return Object.assign({}, state, {
                activeGroup: activeGroupToTaskGroup
            });
        case types.DELETE_TASK_GROUP_FROM_ACTIVE_GROUP:
            let activeGroupToDeleteTaskGroup = Object.assign({}, state.activeGroup);
            activeGroupToDeleteTaskGroup.taskGroups = activeGroupToDeleteTaskGroup.taskGroups
                .filter(tg => tg.taskId !== action.taskId);
            return Object.assign({}, state, {
                activeGroup: activeGroupToDeleteTaskGroup
            });
        case types.ADD_STUDENT_TO_ACTIVE_GROUP:
            let activeGroupToAddStudent = Object.assign({}, state.activeGroup);
            activeGroupToAddStudent.studentGroups = [...activeGroupToAddStudent.studentGroups, action.newStudent];
            return Object.assign({}, state, {
                activeGroup: activeGroupToAddStudent
            });
        case types.DELETE_STUDENT_FROM_ACTIVE_GROUP:
        let activeGroupToDeleteStudent = Object.assign({}, state.activeGroup);
            activeGroupToDeleteStudent.studentGroups = activeGroupToDeleteStudent.studentGroups
                .filter(s => s.studentId !== action.studentId);
        return Object.assign({}, state, {
            activeGroup: activeGroupToDeleteStudent
        });
        default:
            return state;
    }
}
