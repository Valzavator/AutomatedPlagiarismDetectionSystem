import React from "react";
import Load from "./Load";
import {Pagination} from "react-bootstrap";

class CoursesPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            isLoading: false
        }

    }

    render() {

        const card = (
            <div className="card mb-5">
                <div className="row no-gutters">
                    <div className="col-md-2 text-center">
                        <img src={require('../../images/logo.png')} className="card-img" style={{maxWidth: '200px'}}/>
                    </div>
                    <div className="col-md-10">
                        <div className="card-body" style={{height: 'calc(100% - 36px)'}}>
                            <h4 className="card-title text-center">
                                Название карточки
                            </h4>

                            <p className="card-text">
                                This is a wider card with supporting text below as a
                            </p>
                            <p className="card-text">
                                <small className="text-muted">
                                    updated 3 mins ago
                                </small>
                            </p>
                        </div>
                        <div className="text-md-right text-sm-center text-center" style={{height: '36px'}}>
                            <a href="#" className="stretched-link btn btn-primary">
                                Перейти до курсу&nbsp;&nbsp;
                                <i className="fa fa-chevron-circle-right fa-lg" aria-hidden="true"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        )

        return (
            <div className="row h-100 justify-content-center">
                {this.state.isLoading
                    ? (<Load/>)
                    : (
                        <div className="container my-5">
                            {card}
                            {card}
                            {card}
                            {card}
                            {card}

                            <div className="row justify-content-center">
                                <Pagination>
                                    <Pagination.Item>{1}</Pagination.Item>
                                    <Pagination.Ellipsis/>

                                    <Pagination.Item>{11}</Pagination.Item>
                                    <Pagination.Item active>{12}</Pagination.Item>
                                    <Pagination.Item>{13}</Pagination.Item>

                                    <Pagination.Ellipsis/>
                                    <Pagination.Item>{20}</Pagination.Item>
                                </Pagination>
                            </div>
                        </div>
                    )
                }
            </div>
        )
    }
}

export default CoursesPage;