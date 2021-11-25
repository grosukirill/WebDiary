import HeadLayout from "../app/layout/Head";
import LoginController from "../app/controller/Auth/Login"
import withLogin from "../utils/withLogin";

const Login = () => {
    return (
        <div>
            <HeadLayout
                title="Вход в Дневник Вопросов"
            />
            <LoginController />
        </div>
    )
}

export default withLogin(Login);