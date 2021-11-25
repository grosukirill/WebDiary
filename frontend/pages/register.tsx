import RegisterController from "../app/controller/Auth/Register"
import HeadLayout from "../app/layout/Head";

const Register = () => {
    return (
        <div>
            <HeadLayout
                title="Регистрация в Дневник Вопросов"
            />
            <RegisterController />
        </div>
    )
}

export default Register;