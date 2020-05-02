import React from 'react';
import Header from '../component/Header';
import Footer from '../component/Footer'

class Layout extends React.Component {
    render() {
        return (
            <div>
                <Header/>
                <div>
                    {this.props.children}
                </div>
                <Footer/>
            </div>
        )
    }
}

export default Layout;