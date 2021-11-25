interface ISmallButton {
    title: string
    loading: boolean
    onClick?: () => void
}

const SmallButton: React.FC<ISmallButton> = ({ title, loading, ...props }) => {
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