import React from "react";
import Load from "../component/Load";
import {getAllCourses} from "../../api/course";
import {bindActionCreators} from "redux";
import * as errorActions from "../../store/action/errorActions";
import * as sidebarActions from "../../store/action/sidebarActions";
import {connect} from "react-redux";
import moment from "moment";
import PagePagination from "../component/PagePagination";
import {LinkContainer} from "react-router-bootstrap";

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
        this.props.sidebar.changeSidebarState("courseCatalog")
        this.loadAllCourses();
    }

    async loadAllCourses(page = 0, size = 5) {
        try {
            const response = await getAllCourses(page, size);
            await this.setState({
                pageDto: {
                    ...this.state.pageDto,
                    ...response.data
                },
                isLoading: false,
            });
            console.log(this.state.pageDto)
        } catch (err) {
            this.props.error.throwError(err);
        }
    }

    handlePaginationClick(page) {
        this.loadCourses(page);
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
                                        {course.description}
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
                        <div className="container my-5">

                            {renderCourses(this.state.pageDto.content)}


                                <PagePagination
                                    page={this.state.pageDto.page}
                                    totalPages={this.state.pageDto.totalPages}
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
        sidebar: bindActionCreators(sidebarActions, dispatch),
    };
}

export default connect(null, mapDispatchToProps)(CourseCatalogPage);
