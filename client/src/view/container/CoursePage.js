import React from "react";
import Load from "../component/Load";
import {getCourse} from "../../api/course";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import * as sidebarActions from "../../store/action/sidebarActions";
import {connect} from "react-redux";
import moment from "moment";
import PagePagination from "../component/PagePagination";

class CourseCatalogPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pageDto: {
                content: [],
                page: 0,
                totalPages: 0
            },
            isLoading: true
        }

        this.handlePaginationClick = this.handlePaginationClick.bind(this);
    }

    componentDidMount() {
        this.props.sidebar.changeSidebarState("course")
        console.log(this.props.match.params.courseId)
        this.loadCourse();
    }

    componentWillUnmount() {
        this.props.sidebar.changeSidebarState("courseCatalog")
    }

    async loadCourse(page = 0, size = 5) {
        try {
            // const response = await getCourse(page, size);
            await this.setState({
                // course: {
                //         ...this.state.course,
                //         ...response.data
                //     },
                isLoading: false,
            });
            // console.log(this.state.course)
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handlePaginationClick(page) {
        this.loadCourses(page);
    }

    render() {

        const renderGroups = (groups) => {
            return groups.map(
                course =>
                    <div className="card mb-5" key={course.name}>
                        <div className="row no-gutters">
                            <div className="col-md-2 text-center">
                                <div className="row align-content-center h-100 no-gutters">
                                    <img src={require('../../images/logo.png')} className="card-img"
                                         style={{maxWidth: '200px'}}/>
                                </div>
                            </div>
                            <div className="col-md-10">
                                <div className="card-body" style={{height: 'calc(100% - 36px)'}}>
                                    <h4 className="card-title text-center">
                                        {course.name}
                                    </h4>

                                    <p className="card-text">
                                        {course.description}
                                    </p>
                                    <p className="card-text">
                                        <small className="text-muted">
                                            Дата створення: {moment(course.creationDate).format('DD.MM.YYYY HH:mm')}
                                        </small>
                                    </p>
                                </div>
                                <div className="text-md-right text-sm-center text-center" style={{height: '36px'}}>
                                    <button className="stretched-link btn btn-primary">
                                        Перейти до курсу&nbsp;&nbsp;
                                        <i className="fa fa-chevron-circle-right fa-lg" aria-hidden="true"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
            );
        }

        const group = (
            <div className="col mb-4">
                <div className="card h-100">
                    <div className="row justify-content-center no-gutters">
                        <img src={require('../../images/logo.png')} className="card-img"
                             style={{maxWidth: '200px'}} alt="logo"/>
                    </div>
                    <div className="card-body">
                        <h5 className="card-title">Название карточки</h5>
                        <p className="card-text">This is a longer card with supporting text
                            below as a natural lead-in to additional content. This content is a
                            little bit longer.</p>
                    </div>
                </div>
            </div>
        )

        return (
            <div className="row h-100 justify-content-center">
                {this.state.isLoading
                    ? (<Load/>)
                    : (
                        <div className="container my-3">

                            <div className="row justify-content-center">
                                <div className="container-fluid">
                                    <h2 className="text-center"> ASDASD</h2>
                                    <div className="progress mb-4">
                                        <div className="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                                             aria-valuemax="100"/>
                                    </div>
                                </div>
                            </div>

                            <div className="row row-cols-1 row-cols-md-3">
                                {group}
                                {group}
                                {group}
                                {group}
                            </div>

                            <PagePagination
                                page={this.state.pageDto.page}
                                totalPages={12}
                                onClick={this.handlePaginationClick}
                            />
                        </div>
                    )
                }
            </div>
        )
    }
}

function mapDispatchToProps(dispatch) {
    return {
        error: bindActionCreators(errorActions, dispatch),
        sidebar: bindActionCreators(sidebarActions, dispatch)
    };
}

export default connect(null, mapDispatchToProps)(CourseCatalogPage);
