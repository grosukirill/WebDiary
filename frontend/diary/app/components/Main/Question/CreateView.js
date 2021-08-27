import { useEffect, useRef, useState } from 'react'
import SmileSvg from '../../svg/Main/Smile'
import DropDown from '../DropDown/Dropdown'
import Static from '../../../../services/Static'
import SmallButton from '../Button/SmallButton'
import { connect } from 'react-redux'

import Questions from '../../../../services/Questions'

const CreateView = ({ avatar, ...props }) => {
    const [hidden, setHidden] = useState(true)
    const [data, setData] = useState([])
    const [category, selectCategory] = useState({});
    const [loading, setLoading] = useState(false)
    const [value, setValue] = useState("")

    const categoryRef = useRef()

    useEffect(() => {
        Static.getCategories(props.auth.token).then(res => {
            if (res.status) {
                setData(res.data)
            }
        })
    }, [])

    const onFocusAction = () => {
        setHidden(false)
    }

    const validationData = () => {
        if (value.trim() === "") {
            return false;
        }
        if (Object.keys(category).length === 0) {
            if (categoryRef && categoryRef.current) {
                categoryRef.current.classList.add("error_block_line")
            }
            return false;
        }
        return true;
    }

    const selectCategoryData = (data) => {
        if (categoryRef && categoryRef.current) {
            categoryRef.current.classList.remove("error_block_line")
        }

        selectCategory(data)
    }

    const createQuestion = () => {
        const validation = validationData();

        if (validation) {
            setLoading(true)
            const obj = {
                question: value,
                categoryId: category.id
            }
            const req = {
                body: JSON.stringify(obj)
            }
            Questions.createQuestion(props.auth.token, req).then(res => {
                if (res.status) {
                    props.setQuestion(res.data)
                    setValue("");
                    selectCategory({})
                }
                setLoading(false)
            })
        }
    }

    return (
        <div className="feed_input_post_question">
            <span className="feed_title_post_question">Напиши что-то</span>
            <div className="feed_edit_container">
                <div className="feed_edit_avatar">
                    <img
                        src={avatar}
                        alt="Picture"
                        width={32}
                        height={32}
                    />
                </div>
                <div className="feed_edit_input_block">
                    <input
                        type="text"
                        placeholder="Задай свой вопрос"
                        maxLength={140}
                        value={value}
                        onChange={e => setValue(e.target.value)}
                        onFocus={onFocusAction}
                    />
                </div>
                <div className="feed_edit_input_end_block">
                    <SmileSvg />
                </div>
            </div>

            {!hidden ? (
                <div className="end_feed_input_post_questtion">
                    <DropDown
                        title={Object.keys(category).length === 0 ? "Категория" : category.category}
                        dataId="category_end_input"
                        data={data}
                        keyItem="category"
                        select={selectCategoryData}
                        getRef={categoryRef}
                        arrow
                    />
                    <SmallButton
                        title="Создать"
                        onClick={createQuestion}
                        loading={loading}
                    />
                </div>
            ) : ""}
        </div>
    )
}

const mapStateToProps = state => {
    return {
        auth: state.authReducer
    }
}

export default connect(mapStateToProps)(CreateView);