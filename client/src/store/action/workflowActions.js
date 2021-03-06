import * as types from './actionTypes';
import {getAllCourses, getCourse} from "../../api/course";
import {getAllGroupsForCourse, getGroup} from "../../api/group";

export function loadAllCourses(page = 0, size = 5) {
    return async function (dispatch) {
        try {
            const res = await getAllCourses(page, size);
            dispatch({type: types.LOAD_COURSES, courses: res.data});
        } catch (error) {
            dispatch({type: types.THROW_ERROR, error});
        }
    };
}

export function loadSpecificCourse(courseId) {
    return async function (dispatch) {
        try {
            let res = await getCourse(courseId);
            dispatch({type: types.LOAD_SPECIFIC_COURSE, activeCourse: res.data});
        } catch (error) {
            dispatch({type: types.THROW_ERROR, error});
        }
    };
}

export function loadAllGroups(courseId, page = 0, size = 6) {
    return async function (dispatch) {
        try {
            const res = await getAllGroupsForCourse(courseId, page, size);
            dispatch({type: types.LOAD_GROUPS, groups: res.data});
        } catch (error) {
            dispatch({type: types.THROW_ERROR, error});
        }
    };
}

export function loadSpecificGroup(groupId, courseId) {
    return async function (dispatch) {
        try {
            let res = await getGroup(groupId, courseId);
            dispatch({type: types.LOAD_SPECIFIC_GROUPS, activeGroup: res.data});
        } catch (error) {
            dispatch({type: types.THROW_ERROR, error});
        }
    };
}

export function addTaskGroupToActiveGroup(newTaskGroup) {
    return function (dispatch) {
        dispatch({type: types.ADD_TASK_GROUP_TO_ACTIVE_GROUP, newTaskGroup: newTaskGroup});
    }
}

export function deleteTaskGroupFromActiveGroup(taskId) {
    return function (dispatch) {
        dispatch({type: types.DELETE_TASK_GROUP_FROM_ACTIVE_GROUP, taskId: parseInt(taskId)});
    }
}

export function addStudentToActiveGroup(newStudent) {
    return function (dispatch) {
        dispatch({type: types.ADD_STUDENT_TO_ACTIVE_GROUP, newStudent: newStudent});
    }
}

export function deleteStudentFromActiveGroup(studentId) {
    return function (dispatch) {
        dispatch({type: types.DELETE_STUDENT_FROM_ACTIVE_GROUP, studentId: parseInt(studentId)});
    }
}


