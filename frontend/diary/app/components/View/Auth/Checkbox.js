import Done from '../../svg/Done'

const Checkbox = ({ getRef, dataId, title, ...props }) => {
    return (
        <div className="auth_checkbox_container">
            <label
                className="checkbox_container"
                ref={getRef}
                data-id={dataId}
            >
                <input type="checkbox"
                    {...props}
                />
                <span className="checkmark">
                    <Done
                        fill="white"
                        width="11"
                        height="11"
                    />
                </span>
                {title}
            </label>
        </div>
    )
}

export default Checkbox;