import Image from 'next/image'
import SmileSvg from '../../components/svg/Main/Smile'
import Tab from '../../components/View/Home/Tab'
import Post from '../../components/Main/Post/Post'
import NoContent from '../../components/Main/Post/NoContent'
import CreateView from '../../components/Main/Question/CreateView'

const Feed = (props) => {
    return (
        <div className="main_container">
            <div className="main_post_container">
                <CreateView
                    avatar={props.props.auth.user?.avatar ?? ""}
                    setQuestion={props.setQuestion}
                />
                <div className="post_container">
                    {props.state.loading ? (
                        <div className="loader content_loader feed_loader"></div>
                    ) : (
                        <>
                            {props.state.questions.length === 0 ? (
                                <NoContent />
                            ) : (
                                <>
                                    {props.state.questions.map((item, index) => {
                                        return (
                                            <Post
                                                author={item.creator.firstName + " " + item.creator.lastName}
                                                date={item.creationDate}
                                                title={item.question}
                                                key={index}
                                                id={item.id}
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
            <Tab />
        </div>
    )
}

export default Feed;