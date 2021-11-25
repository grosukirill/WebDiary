const Button: React.FC<ButtonProps> = ({ title, loading, ...props }) => {
    return (
        <button
            className={(loading ? "loading_auth_button " : "") + "auth_button"}
            {...props}
        >
            {loading ? (
                <>
                    <div className="loader"></div>
                    <div className="overlay_loading"></div>
                </>
            ) : ""}

            <span className="button_text">{title}</span>
        </button>
    )
}

export default Button;