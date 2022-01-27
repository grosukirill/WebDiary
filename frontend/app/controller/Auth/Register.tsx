import React from 'react';
import RegisterView from '../../view/Auth/Register';
import { connect } from 'react-redux'
import { bindActionCreators, Dispatch } from 'redux'
import { withRouter, NextRouter } from 'next/router'
import { setData } from '../../../store/actions/mainAction'

import { registerStart } from '../../../store/actions/authActions'

interface IActionProps {
    dispatch: Dispatch
    setData: (key: string, value: any) => void
    registerStart: (data: IRegisterUser) => void
    data: ImainReducer
    auth: IauthReducer
}

interface IProps extends IActionProps {
    router: NextRouter,
}

class Register extends React.Component<IProps, RegisterStateC> {
    constructor(props: IProps) {
        super(props);
        this.state = {
            name: "",
            surname: "",
            email: "",
            password: "",
            hidePassword: true,
            anim: false,
            checkbox: false,
            inputs: [],
            error: {
                status: false,
                message: null
            }
        }
    }

    changeState = (key: string, value: any) => {
        this.setState({
            [key]: value
        } as Pick<RegisterStateC, keyof RegisterStateC>)
    }

    changeAuthPage = () => {
        this.changeState("anim", true)
        this.props.setData("loginAnimation", true)
        setTimeout(() => {
            this.props.router.push("/login");
        }, 500)
    }

    setRef = (value: HTMLElement) => {
        if (value && !this.state.inputs.includes(value)) {
            const arr = [...this.state.inputs];
            arr.push(value)
            this.setState({
                inputs: arr
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

    validation = () => {
        if (this.state.name.trim() === "") {
            this.validate("name")
            return false;
        }
        if (this.state.surname.trim() === "") {
            this.validate("surname")
            return false;
        }
        if (this.state.email.trim() === "") {
            this.validate("email")
            return false;
        }
        if (this.state.password.trim() === "") {
            this.validate("password")
            return false;
        }
        const checkPass = this.validatePasswordEmail();
        return checkPass;
    }

    validatePasswordEmail = () => {
        const password = this.state.password
        var checkLetters = password.replace(/[^a-zA-Z]+/g, '');
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        let error: IError = {
            status: false,
            message: ""
        }
        if (!re.test(this.state.email)) {
            error = {
                status: true,
                message: "Email введен не правильно!"
            }
        }
        else if (checkLetters === "") {
            error = {
                status: true,
                message: "Пароль должен состоять только из латинских символов"
            }
        }
        else if (!/[a-z]/.test(password)) {
            error = {
                status: true,
                message: "Пароль должен содержать нижний регистр"
            }
        }
        else if (!/[A-Z]/.test(password)) {
            error = {
                status: true,
                message: "Пароль должен содержать верхний регистр"
            }
        }
        else if (!/[!#\*\?]/.test(password)) {
            error = {
                status: true,
                message: "Пароль должен содержать символы"
            }
        }
        if (error.status) {
            this.setState({
                error: error
            })
            return false;
        }
        else {
            if (this.state.error.status) {
                this.setState({
                    error: {
                        status: false,
                        message: null
                    }
                })
            }
            return true;
        }
    }

    register = () => {
        const validation = this.validation();

        if (validation) {
            const obj: IRegisterUser = {
                firstName: this.state.name,
                lastName: this.state.surname,
                email: this.state.email,
                password: this.state.password
            }
            this.props.registerStart(obj);
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
        const obj: RegisterPropsC = {
            state: this.state,
            changeState: this.changeState,
            getAuthErrors: this.getAuthErrors,
            setRef: this.setRef,
            register: this.register,
            changeAuthPage: this.changeAuthPage,
            props: {
                data: this.props.data,
                auth: this.props.auth
            }
        }
        
        return React.createElement(RegisterView, obj)
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
        ...bindActionCreators({ setData, registerStart }, dispatch),
    }
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Register));