import React from 'react';
import { createElement } from 'react';
import { connect } from 'react-redux'

import MainView from '../../view/Home/Main';
import Questions from '../../../services/Questions';

interface IProps {
    data: ImainReducer
    auth: IauthReducer
}

class Main extends React.Component<IProps, MainStateC> {
    constructor(props: IProps) {
        super(props);
        this.state = {
        }
    }

    render() {
        const obj: MainPropsC = {
            props: {
                data: this.props.data,
                auth: this.props.auth
            }
        }
        return createElement(MainView, obj)
    }
}

const mapStateToProps = (state: RootState) => {
    return {
        data: state.mainReducer,
        auth: state.authReducer
    }
}

export default connect(mapStateToProps)(Main);