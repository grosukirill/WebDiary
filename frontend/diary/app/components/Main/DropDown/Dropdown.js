import { useState } from "react"
import ArrowDown from '../../svg/ArrowDown'

const DropDown = ({ children, dataId, data, select, containsIcons, title, arrow, keyItem, getRef, ...props }) => {
    const [opened, setOpened] = useState(false)

    const onFocusAction = () => {
        if (!props.disabled) {
            if (!opened) {
                openDropDown()
            }
            else {
                closeDD();
            }
        }
    }

    const closeDD = () => {
        setOpened(false)
        document.removeEventListener('click', handleOutsideClick, false);
    }

    const openDropDown = () => {
        setOpened(true)
        document.addEventListener('click', handleOutsideClick, false);
    }

    const handleOutsideClick = e => {
        if (!e.target.closest("." + dataId + "_drop_down")) {
            closeDD()
        }
    }

    const selectData = (item) => {
        select(item)
        closeDD()
    }

    return (
        <div
            className={dataId + "_drop_down " + " drop_down_main_element"}
            ref={getRef}
        >
            <div className="drop_down_element" onClick={onFocusAction}>
                {children ? children : (
                    <div className="own_drop_down_element">
                        <span>{title}</span>
                        {arrow ? (
                            <ArrowDown />
                        ) : ""}
                    </div>
                )}
            </div>
            {opened && data && data.length !== 0 ? (
                <div className={(!children ? "left_position " : "") + "drop_down_block"}>
                    <ul>
                        {data.map((item, index) => {
                            return (
                                <li
                                    key={index}
                                    onClick={() => selectData(item)}
                                >
                                    {item.icon ? item.icon : ""}
                                    <span className={containsIcons ? "icon_padding" : ""}>{keyItem ? item[keyItem] : item.name}</span>
                                </li>
                            )
                        })}
                    </ul>
                </div>
            ) : ""}
        </div>
    )
}

export default DropDown;