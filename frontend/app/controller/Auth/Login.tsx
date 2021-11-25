import React from 'react';
import { createElement } from 'react';
import { connect } from 'react-redux'
import { bindActionCreators, Dispatch } from 'redux'
import { setData } from '../../../store/actions/mainAction'
import { withRouter, NextRouter } from 'next/router'
import { authenticationStart, setAuthData } from '../../../store/actions/authActions'

import LoginView from '../../view/Auth/Login';

interface IActionProps {
    dispatch: Dispatch
    setAuthData: (key: string, data: any) => void
    authenticationStart: (data: ILoginUser) => void
    setData: (key: string, value: any) => void
    data: ImainReducer
    auth: IauthReducer
}

interface IProps extends IActionProps {
    router: NextRouter,
}

class Login extends React.Component<IProps, LoginStateC> {
    constructor(props: IProps) {
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

    changeState = (key: string, value: any) => {
        this.setState({
            [key]: value
        } as Pick<LoginStateC, keyof LoginStateC>)
    }

    setRef = (value: HTMLElement) => {
        if (value && !this.state.inputs.includes(value)) {
            this.setState({
                inputs: [...this.state.inputs, value]
            })
        }
    }

    validate = (error: string) => {
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
            const obj: ILoginUser = {
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
        const obj: LoginPropsC = {
            state: this.state,
            changeState: this.changeState,
            getAuthErrors: this.getAuthErrors,
            setRef: this.setRef,
            signIn: this.signIn,
            changeAuthPage: this.changeAuthPage,
            props: {
                data: this.props.data,
                auth: this.props.auth
            }
        }

        return React.createElement(LoginView, obj)
    }
}

const mapStateToProps = (state: RootState) => {
    return {
        data: state.mainReducer,
        auth: state.authReducer
    }
}

const mapDispatchToProps = (dispatch: Dispatch) => {
    return {
        dispatch,
        ...bindActionCreators({ setData, authenticationStart, setAuthData }, dispatch),
    }
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Login));