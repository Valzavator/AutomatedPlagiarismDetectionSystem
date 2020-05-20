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
        case types.DELETE_TASK_GROUP_FROM_ACTIVE_GROUP:
            let updatedActiveGroup = Object.assign({}, state.activeGroup);
            updatedActiveGroup.taskGroups = updatedActiveGroup.taskGroups.filter(tg => tg.taskId !== action.taskId);
            return Object.assign({}, state, {
                activeGroup: updatedActiveGroup
            });
        default:
            return state;
    }
}
