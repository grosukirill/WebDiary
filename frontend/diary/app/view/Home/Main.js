import Image from 'next/image'
import Tab from '../../components/View/Home/Tab'
import Post from '../../components/Main/Post/Post'

const Main = (props) => {
    return (
        <div className="main_container">
            <div className="main_post_container">
                <div className="post_container">
                    {props.state.loading ? (
                        <div className="loader content_loader feed_loader"></div>
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
                </div>
            </div>
            <Tab />
        </div>
    )
}

export default Main;