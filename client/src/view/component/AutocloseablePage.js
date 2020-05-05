import React from "react";

class AutocloseablePage extends React.Component {
    componentDidMount() {
        window.close();
    }
    render() {
        return (<div/>)
    }
}

export default AutocloseablePage;