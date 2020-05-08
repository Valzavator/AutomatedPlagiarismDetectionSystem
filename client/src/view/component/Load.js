import React from 'react';

const Load = (props) => (
    <div className={props.page ?  'load-page' : 'load-content'}>
        <div className='anim-loading'>
            <i className="fa fa-cog fa-spin fa-5x fa-fw"/>
        </div>
    </div>
);

export default Load;