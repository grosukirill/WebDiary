import Eye from '../../svg/Auth/Eye'
import EyeOff from '../../svg/Auth/EyeOff'
import style from './css/input.module.css'

const Input = ({ icon, password, showEye, getRef, dataId, ...props }) => {
    return (
        <div
            className={style.auth_input_block}
            ref={getRef}
            data-id={dataId}
        >
            {
                icon ?
                    (
                        <div className={style.input_icon}>
                            {icon}
                        </div>
                    ) : ""
            }
            <input
                className={(password ? style.auth_input_password : "") + " " + style.auth_input}
                {...props}
            />
            {password ? (
                <>
                    {showEye ? (
                        <Eye
                            className={style.eye_icon}
                            onClick={password}
                        />
                    ) : (
                        <EyeOff
                            className={style.eye_icon}
                            onClick={password}
                        />
                    )}
                </>
            ) : ""}
        </div>
    )
}

export default Input;