import Banner from '../../components/View/Auth/Banner'
import Input from '../../components/View/Auth/Input'
import Person from '../../components/svg/Auth/Person'
import Password from '../../components/svg/Auth/Password'
import Checkbox from '../../components/View/Auth/Checkbox'
import Button from '../../components/View/Auth/Button'

const Login: React.FC<LoginPropsC> = (props) => {
    return (
        <div
            className={(props.props.data.loginAnimation ? "login_appear " : "")
                + (props.state.anim ? "auth_animation_login " : "")
                + "auth"}>
            <div className="auth_container login_auth">
                <span className="auth_title_container">Вход в Дневник Вопросов</span>
                <div className="auth_input_blocks">
                    <Input
                        icon={<Person />}
                        type="email"
                        placeholder="Email"
                        value={props.state.user}
                        getRef={(ref: HTMLElement) => { props.setRef(ref) }}
                        onChange={e => props.changeState("user", e.target.value)}
                        dataId="user"
                    />
                    <Input
                        icon={<Password />}
                        value={props.state.password}
                        onChange={e => props.changeState("password", e.target.value)}
                        type={props.state.hidePassword ? "password" : "text"}
                        placeholder="Password"
                        showEye={props.state.hidePassword}
                        getRef={(ref: HTMLElement) => { props.setRef(ref) }}
                        password={() => props.changeState("hidePassword", !props.state.hidePassword)}
                        dataId="password"
                    />

                    {Object.keys(props.props.auth.errors).length !== 0 && props.props.auth.errors.message ? (
                        <div className="erorr_text_block">
                            <span>{props.props.auth.errors.message}</span>
                        </div>
                    ) : ""}

                    <Button
                        onClick={props.signIn}
                        loading={props.props.auth.loading}
                        title="Войти"
                    />
                </div>

                <div className="login_bottom_container">
                    <div className="no_account_block">
                        <span className="title_account">У вас еще нет аккаунта?</span>
                        <span className="link_account" onClick={props.changeAuthPage}>
                            Зарегистрируйтесь
                        </span>
                    </div>
                </div>
            </div>
            <Banner />
        </div>
    )
}

export default Login;