import Banner from '../../components/View/Auth/Banner'
import Input from '../../components/View/Auth/Input'
import Person from '../../components/svg/Auth/Person'
import Email from '../../components/svg/Auth/Email'
import Phone from '../../components/svg/Auth/Phone'
import Password from '../../components/svg/Auth/Password'
import Button from '../../components/View/Auth/Button'

const Register = (props) => {
    const errors = props.getAuthErrors()
    return (
        <div className={(props.state.anim ? "auth_animation " : "")
            + (props.props.data.loginAnimation ? "register_appear " : "")
            + "auth"}>
            <Banner />
            <div className="auth_container register_auth">
                <span className="auth_title_container">Вход в Дневник Вопросов</span>

                <div className="auth_input_blocks">
                    <Input
                        icon={<Person />}
                        value={props.state.name}
                        onChange={e => props.changeState("name", e.target.value)}
                        type="text"
                        placeholder="Имя"
                        dataId="name"
                        getRef={ref => { props.setRef(ref) }}
                    />
                    <Input
                        icon={<Person />}
                        value={props.state.surname}
                        onChange={e => props.changeState("surname", e.target.value)}
                        type="text"
                        placeholder="Фамилия"
                        dataId="surname"
                        getRef={ref => { props.setRef(ref) }}
                    />
                    <Input
                        icon={<Email />}
                        value={props.state.email}
                        onChange={e => props.changeState("email", e.target.value)}
                        type="email"
                        placeholder="Email"
                        dataId="email"
                        getRef={ref => { props.setRef(ref) }}
                    />
                    <Input
                        icon={<Password />}
                        value={props.state.password}
                        onChange={e => props.changeState("password", e.target.value)}
                        type={props.state.hidePassword ? "password" : "text"}
                        placeholder="Пароль"
                        showEye={props.state.hidePassword}
                        password={() => props.changeState("hidePassword", !props.state.hidePassword)}
                        dataId="password"
                        getRef={ref => { props.setRef(ref) }}
                    />

                    {props.state.error.status || errors.status ? (
                        <div className="erorr_text_block">
                            <span>{props.state.error.message || errors.message}</span>
                        </div>
                    ) : ""}

                    <Button
                        onClick={props.register}
                        loading={props.props.auth.loading}
                        title="отправить"
                    />

                    <div className="login_bottom_container">
                        <div className="no_account_block">
                            <span className="title_account">У вас уже есть аккаунт?</span>
                            <span className="link_account" onClick={props.changeAuthPage}>
                                Войдите
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default (Register);