import Image from 'next/image'
import Horiz from '../../svg/Post/Horiz'
import Vote from '../../svg/Vote'
import DownVote from '../../svg/DownVote'
import MessageSvg from '../../svg/Post/Message'
import Bookmark from '../../svg/Post/Bookmark'
import DropDown from '../DropDown/Dropdown'
import { connect } from 'react-redux'

import Questions from '../../../../services/Questions'
import { useEffect, useState } from 'react'

import { BiDownArrowAlt, BiUpArrowAlt, BiBookmark, BiComment } from 'react-icons/bi';

const Post: React.FC<IPostC> = ({ title, author, date, count, key, idEl, ...props }) => {
    const [countVotes, setCount] = useState(0)
    const [voted, setVotes] = useState(props.voted)

    useEffect(() => {
        setCount(props.votes)
    }, [])

    const votePost = (action: number) => {
        const data = {
            vote: action,
            questionId: idEl
        }
        const req = {
            body: JSON.stringify(data)
        }
        Questions.voteQuestion(props.auth.token, req).then(res => {
            if (res.status) {
                setCount(res?.data?.votes)
                setVotes(res?.data?.voted)
            }
        })
    }

    const select = (item: IDropDownMainData) => {}

    return (
        <div className="post_component">
            <div className="vote_post_block">
                <BiUpArrowAlt
                    onClick={() => votePost(1)}
                    className={voted === true ? "voted_icon" : ""}
                />
                <div className="vote_count_block">
                    <span>{countVotes}</span>
                </div>
                <BiDownArrowAlt
                    onClick={() => votePost(-1)}
                    className={voted === false ? "downvote" : ""}
                />
            </div>
            <div className="post_block">
                <div className="top_post_author_block">
                    <div className="main_top_author_block">
                        <Image
                            src="/avatar.png"
                            alt="Picture"
                            width={38}
                            height={38}
                        />
                        <div className="post_author_info">
                            <span className="post_author_title">{author}</span>
                            <span className="post_author_date">{date}</span>
                        </div>
                    </div>
                    <DropDown
                        children={
                            <div className="post_horiz_line">
                                <Horiz />
                            </div>
                        }
                        select={select}
                        dataId={"post_" + key}
                        data={
                            [
                                {
                                    name: "Пожаловаться",
                                }
                            ]
                        }
                    />
                </div>

                <div className="main_post_block">
                    <span className="question_title">{title}</span>
                </div>

                <div className="end_post_block">
                    <div className="right_post_icons_block">
                        <BiComment size={24} />
                        <div className="bookmark_post_icon">
                            <BiBookmark size={24} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

const mapStateToProps = (state: RootState) => {
    return {
        data: state.mainReducer,
        auth: state.authReducer
    }
}

export default connect(mapStateToProps)(Post);