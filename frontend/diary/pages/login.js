import LoginController from "../app/controller/Auth/Login"
import withLogin from "../utils/withLogin";
import HeadLayout from "../app/layout/Head";

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