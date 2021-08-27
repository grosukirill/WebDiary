import React from 'react';
import { createElement } from 'react';
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { setData } from '../../../store/actions/mainAction'
import { withRouter } from 'next/router'
import { authenticationStart, setAuthData } from '../../../store/actions/authAction'

import LoginView from '../../view/Auth/Login';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: "",
            password: "",
            hidePassword: true,
            anim: false,
            checkbox: false,
            inputs: []
        }
    }

    componentDidMount = () => {
        this.props.setAuthData("errors", {})
    }

    changeState = (key, value) => {
        this.setState({
            [key]: value
        })
    }

    setRef = (value) => {
        if (value && !this.state.inputs.includes(value)) {
            this.setState({
                inputs: [...this.state.inputs, value]
            })
        }
    }

    validate = (error) => {
        const inputs = this.state.inputs
        const block = inputs.find(item => item.getAttribute("data-id") === error)
        if (block) {
            block.classList.add("error_block")
        }
    }

    changeAuthPage = () => {
        this.changeState("anim", true)
        this.props.setData("loginAnimation", true)
        setTimeout(() => {
            this.props.router.push("/register");
        }, 500)
    }

    validation = () => {
        if (this.state.user.trim() === "") {
            this.validate("user")
            return false;
        }
        if (this.state.password.trim() === "") {
            this.validate("password")
            return false;
        }
        return true;
    }

    signIn = () => {
        const validation = this.validation();
        if (validation) {
            const obj = {
                email: this.state.user,
                password: this.state.password
            }
            this.props.authenticationStart(obj)
        }
    }

    getAuthErrors = () => {
        return {
            status: false
        }
    }

    componentDidUpdate = () => {
        const inputs = this.state.inputs
        if (inputs) {
            for (let i = 0; i < inputs.length; i++) {
                if (inputs[i]) {
                    inputs[i].addEventListener("click", () => {
                        if (inputs[i].classList.contains("error_block")) {
                            inputs[i].classList.remove("error_block")
                        }
                    })
                }
            }
        }
    }

    render() {
        return createElement(LoginView, {
            ...this
        })
    }
}

const mapStateToProps = state => {
    return {
        data: state.mainReducer,
        auth: state.authReducer
    }
}

const mapDispatchToProps = dispatch => {
    return {
        dispatch,
        ...bindActionCreators({ setData, authenticationStart, setAuthData }, dispatch),
    }
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Login));