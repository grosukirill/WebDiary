import Post from '../../components/Main/Post/Post'
import NoContent from '../../components/Main/Post/NoContent'
import CreateView from '../../components/Main/Question/CreateView'

import style from '../../../assets/css/modules/feed.module.css'

const Feed: React.FC<FeedPropsC> = (props) => {
    return (
        <div
            className={style.main + " main_container"}
            ref={(ref: HTMLDivElement) => {
                props.getRef({
                    current: ref
                })
            }}
        >
            <div className="main_post_container">
                <CreateView
                    avatar={props.props.auth.user?.avatar ?? ""}
                    setQuestion={props.setQuestion}
                />
                <div className={style.post_container}>
                    {props.state.loading ? (
                        <div className="loader content_loader feed_loader"></div>
                    ) : (
                        <>
                            {props.state.questions?.length === 0 ? (
                                <NoContent />
                            ) : (
                                <>
                                    {props.state.questions?.map((item, index) => {
                                        return (
                                            <Post
                                                author={item.creator.firstName + " " + item.creator.lastName}
                                                date={item.creationDate}
                                                title={item.question}
                                                key={index}
                                                idEl={item.id}
                                                {...item}
                                            />
                                        )
                                    })}
                                </>
                            )}
                        </>
                    )}
                </div>
            </div>
            {/* <Tab /> */}
        </div>
    )
}

export default Feed;