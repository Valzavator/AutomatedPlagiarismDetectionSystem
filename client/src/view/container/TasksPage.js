import React from "react";
import Load from "../component/Load";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import * as sidebarActions from "../../store/action/sidebarActions";
import {connect} from "react-redux";
import PagePagination from "../component/PagePagination";
import {getAllCourseTasks} from "../../api/task";

class TasksPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            content: [],
            page: 0,
            totalPages: 0
        }

        this.handlePaginationClick = this.handlePaginationClick.bind(this);
    }

    componentDidMount() {
        this.props.sidebar.changeSidebarState("courseTasks", "Меню керування:");
        this.loadCourseTasks();
    }

    componentWillUnmount() {
        this.props.sidebar.changeSidebarState("courseCatalog")
    }

    async loadCourseTasks(page = 0) {
        try {
            await this.setState({
                isLoading: true
            });
            const courseId = this.props.match.params.courseId;
            let res = await getAllCourseTasks(courseId, page);
            console.log(res.data)
            await this.setState({
                isLoading: false,
                ...res.data
            });
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handlePaginationClick(page) {
        this.loadCourseTasks(page);
    }

    render() {

        const renderTasks = (tasks) => {
            return tasks.map(
                task =>
                    <div className="col mb-4" key={task.id}>
                        <div className="card">
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item">
                                    Назва: <strong>{task.name}</strong>
                                </li>
                                <li className="list-group-item">
                                    Директорія репозиторія: <strong>"{task.repositoryPrefixPath}"</strong>
                                </li>
                            </ul>
                            <div className="card-body overflow-auto" style={{maxHeight: '200px'}}>
                                <h5 className="card-title text-center">Опис завдання</h5>
                                {task.description ? task.description : 'Відсутній...'}
                            </div>
                            <div className="card-body text-center">
                                <button className="btn btn-danger align-self-end m-3">
                                    Видалити&nbsp;&nbsp;
                                    <i className="fa fa-trash fa-lg" aria-hidden="true"/>
                                </button>
                                <button className="btn btn-warning align-self-end m-3">
                                    Редагувати&nbsp;&nbsp;
                                    <i className="fa fa-pencil-square fa-lg" aria-hidden="true"/>
                                </button>
                            </div>
                        </div>
                    </div>
            );
        }

        return (
            <div className="row h-100 justify-content-center">
                {this.state.isLoading
                    ? (<Load/>)
                    : (
                        <div className="container my-3">

                            <div className="row justify-content-center">
                                <div className="container-fluid">
                                    <h2 className="text-center">
                                        Завдання курсу
                                        {this.props.activeCourse.name !== 'NOT_LOADED'
                                            ? `"${this.props.activeCourse.name}"`
                                            : null
                                        }
                                    </h2>
                                    <div className="progress mb-4">
                                        <div className="progress-bar" role="progressbar" aria-valuenow="25"
                                             aria-valuemin="0"
                                             aria-valuemax="100"/>
                                    </div>
                                </div>
                            </div>

                            {
                                this.state.content.length > 0
                                    ? (
                                        <div className="row row-cols-1 row-cols-md-3">
                                            {renderTasks(this.state.content)}
                                        </div>
                                    )
                                    : (
                                        <div className="row align-items-center h-50 justify-content-center">
                                            <div className="container-fluid">
                                                <div className="alert alert-primary" role="alert">
                                                    <h2 className="text-center">
                                                        Ви не створили жодного завдання для даного курсу
                                                    </h2>
                                                </div>
                                            </div>
                                        </div>
                                    )
                            }

                            <PagePagination
                                page={this.state.page}
                                totalPages={this.state.totalPages}
                                onClick={this.handlePaginationClick}
                            />
                        </div>
                    )
                }
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        activeCourse: state.workflow.activeCourse,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
        sidebar: bindActionCreators(sidebarActions, dispatch)
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(TasksPage);
