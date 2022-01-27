import { useEffect, useRef, useState } from 'react'
import SmileSvg from '../../svg/Main/Smile'
import DropDown from '../DropDown/Dropdown'
import Static from '../../../../services/Static'
import SmallButton from '../Button/SmallButton'
import { connect } from 'react-redux'
import dynamic from 'next/dynamic';
const Picker = dynamic(() => import('emoji-picker-react'), { ssr: false });

import Questions from '../../../../services/Questions'

import style from '../../../../assets/css/modules/feed.module.css'

import { BiSmile } from 'react-icons/bi';

interface IEmojiData {
    unified: string,
    originalUnified: string,
    names: Array<string>,
    emoji: string,
    activeSkinTone: string
}

const CreateView: React.FC<ICreateViewC> = ({ avatar, ...props }) => {
    const [hidden, setHidden] = useState(true)
    const [iconHidden, setIconHidden] = useState(true)
    const [data, setData] = useState([])
    const [category, selectCategory] = useState<IDropDownMainData>({});
    const [loading, setLoading] = useState(false)
    const [value, setValue] = useState("")

    const categoryRef = useRef<HTMLElement>()

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

    const selectCategoryData = (data: IDropDownMainData) => {
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

    const onEmojiClick = (event: React.MouseEvent<Element, MouseEvent>, emojiObject: IEmojiData) => {
        setValue(prev => prev + emojiObject.emoji)
    };

    const selectEmoji = () => {
        setIconHidden(!iconHidden)

        if (iconHidden) {
            document.addEventListener('click', handleOutsideClick, false);
        }
        else {
            document.removeEventListener('click', handleOutsideClick, false);
        }
    }

    const handleOutsideClick = (e: Event) => {
        const target = e.target as HTMLElement

        if (!target.closest('.feed_edit_input_block')) {
            setIconHidden(true)
        }
    }

    return (
        <div className={style.feed_input_post_question}>
            <span className={style.feed_title_post_question}>Напиши что-то</span>
            <div className={style.feed_edit_container}>
                <div className={style.feed_edit_avatar}>
                    <img
                        src={avatar}
                        alt="Picture"
                        width={32}
                        height={32}
                    />
                </div>
                <div className={style.feed_edit_input_block}>
                    <input
                        type="text"
                        placeholder="Задай свой вопрос"
                        maxLength={140}
                        value={value}
                        onChange={e => setValue(e.target.value)}
                        onFocus={onFocusAction}
                    />
                </div>
                <div className={`${style.feed_edit_input_end_block} feed_edit_input_block`}>
                    <BiSmile onClick={selectEmoji} />
                    <div className={`${style.icon_picker_container} ${iconHidden ? style.hidden_picker : ""}`}>
                        <Picker
                            groupNames={{
                                smileys_people: 'Смайлики и люди',
                                animals_nature: 'Животные и природа',
                                food_drink: 'Еда и напитоки',
                                travel_places: 'Путешествия и места',
                                activities: 'Деятельность',
                                objects: 'Объекты',
                                symbols: 'Символы',
                                flags: 'Флаги',
                                recently_used: 'Недавно использованные',
                            }}
                            onEmojiClick={onEmojiClick}
                        />
                    </div>
                </div>
            </div>

            {!hidden ? (
                <div className={style.end_feed_input_post_questtion}>
                    <DropDown
                        title={Object.keys(category).length === 0 ? "Категория" : category.category}
                        dataId="category_end_input"
                        data={data}
                        keyItem="category"
                        select={selectCategoryData}
                        getRef={ref => categoryRef}
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

const mapStateToProps = (state: RootState) => {
    return {
        auth: state.authReducer
    }
}

export default connect(mapStateToProps)(CreateView);