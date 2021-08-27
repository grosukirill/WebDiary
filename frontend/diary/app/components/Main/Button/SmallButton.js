const SmallButton = ({ title, loading, ...props }) => {
    return (
        <button
            className={(loading ? "loading_small_button " : "") + "send_question_button"}
            {...props}
        >
            {loading ? (
                <>
                    <div className="loader"></div>
                    <div className="overlay_loading"></div>
                </>
            ) : ""}
            <span>{title}</span>
        </button>
    )
}

export default SmallButton;