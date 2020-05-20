import React from "react";
import Load from "../component/Load";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import * as sidebarActions from "../../store/action/sidebarActions";
import * as workflowActions from "../../store/action/workflowActions";
import {connect} from "react-redux";
import moment from "moment";
import PagePagination from "../component/PagePagination";
import {LinkContainer} from "react-router-bootstrap";

class TasksPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false
        }

        this.handlePaginationClick = this.handlePaginationClick.bind(this);
    }

    async componentDidMount() {
        await this.props.workflow.loadSpecificCourse(this.props.match.params.courseId);
        await this.props.sidebar.changeSidebarState("courseTasks", "Меню керування:");
        await this.props.workflow.loadAllGroups(this.props.match.params.courseId);
        await this.setState({
            isLoading: false
        });
    }

    componentWillUnmount() {
        this.props.sidebar.changeSidebarState("courseCatalog")
    }

    async handlePaginationClick(page) {
        await this.setState({
            isLoading: true
        });
        await this.props.workflow.loadAllGroups(this.props.match.params.courseId, page);
        await this.setState({
            isLoading: false
        });
    }

    render() {

        const renderGroups = (groups) => {
            return groups.map(
                group =>
                    <div className="col mb-4" key={group.id}>
                        <div className="card h-100">
                            <div className="card-body">
                                <h5 className="card-title text-center">{group.name}</h5>
                                <div className="row justify-content-center no-gutters">
                                    <img src={require('../../images/logo.png')} className="card-img"
                                         style={{maxWidth: '200px'}} alt="logo"/>
                                </div>
                                <p className="card-text text-center">
                                    <small className="text-muted">
                                        Дата створення: {moment(group.creationDate).format('DD.MM.YYYY HH:mm')}
                                    </small>
                                </p>
                                <div className="text-sm-center text-center">
                                    <LinkContainer
                                        to={"/courses/" + this.props.match.params.courseId + "/groups/" + group.id}>
                                        <button className="stretched-link btn btn-primary">
                                            Перейти до групи&nbsp;&nbsp;
                                            <i className="fa fa-chevron-circle-right fa-lg" aria-hidden="true"/>
                                        </button>
                                    </LinkContainer>
                                </div>
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
                                        Завдання курсу "{this.props.activeCourse.name}"
                                    </h2>
                                    <div className="progress mb-4">
                                        <div className="progress-bar" role="progressbar" aria-valuenow="25"
                                             aria-valuemin="0"
                                             aria-valuemax="100"/>
                                    </div>
                                </div>
                            </div>

                            {
                                this.props.groups.content.length > 0
                                    ? (
                                        <div className="row row-cols-1 row-cols-md-3">
                                            {renderGroups(this.props.groups.content)}
                                        </div>
                                    )
                                    : (
                                        <div className="row align-items-center h-50 justify-content-center">
                                            <div className="container-fluid">
                                                <div className="alert alert-primary" role="alert">
                                                    <h2>
                                                        Ви поки що не створили жодної групи для даного курсу
                                                    </h2>
                                                </div>
                                            </div>
                                        </div>
                                    )
                            }

                            <PagePagination
                                page={this.props.groups.page}
                                totalPages={this.props.groups.totalPages}
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
        groups: state.workflow.groups
    };
}


function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
        sidebar: bindActionCreators(sidebarActions, dispatch),
        workflow: bindActionCreators(workflowActions, dispatch),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(TasksPage);
