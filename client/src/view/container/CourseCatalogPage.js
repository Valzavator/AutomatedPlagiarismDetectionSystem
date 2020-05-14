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

class CourseCatalogPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true
        }

        this.handlePaginationClick = this.handlePaginationClick.bind(this);
    }

    async componentDidMount() {
        await this.props.sidebar.changeSidebarState("courseCatalog")
        await this.props.workflow.loadAllCourses();
        await this.setState({
            isLoading: false
        });
    }

    async handlePaginationClick(page) {
        await this.setState({
            isLoading: true
        });
        await this.props.workflow.loadAllCourses(page);
        await this.setState({
            isLoading: false
        });
    }

    render() {

        const renderCourses = (courses) => {
            return courses.map(
                course =>
                    <div className="card mb-5" key={course.name}>
                        <div className="row no-gutters">
                            <div className="col-md-2 text-center">
                                <div className="row align-content-center h-100 no-gutters">
                                    <img src={require('../../images/logo.png')} className="card-img"
                                         style={{maxWidth: '200px'}} alt="logo"/>
                                </div>
                            </div>
                            <div className="col-md-10">
                                <div className="card-body" style={{height: 'calc(100% - 36px)'}}>
                                    <h4 className="card-title text-center">
                                        {course.name}
                                    </h4>

                                    <p className="card-text">
                                        {course.description ? course.description : 'Опис курсу відсутній...'}
                                    </p>
                                    <p className="card-text">
                                        <small className="text-muted">
                                            Дата створення: {moment(course.creationDate).format('DD.MM.YYYY HH:mm')}
                                        </small>
                                    </p>
                                </div>
                                <div className="text-md-right text-sm-center text-center" style={{height: '36px'}}>
                                    <LinkContainer to={"/courses/" + course.id}>
                                        <button className="stretched-link btn btn-primary">
                                            Перейти до курсу&nbsp;&nbsp;
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
                        <div className="container my-5 w-75">

                            {this.props.courses.content.length > 0
                                ? renderCourses(this.props.courses.content)
                                : (
                                    <div className="row align-items-center h-100 justify-content-center">
                                        <div className="alert alert-primary" role="alert">
                                            <h2>
                                                Ви поки що не створили жодного курсу
                                            </h2>
                                        </div>
                                    </div>
                                )
                            }

                            <PagePagination
                                page={this.props.courses.page}
                                totalPages={this.props.courses.totalPages}
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
        courses: state.workflow.courses
    };
}

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
        sidebar: bindActionCreators(sidebarActions, dispatch),
        workflow: bindActionCreators(workflowActions, dispatch),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(CourseCatalogPage);
