import withAuth from '../utils/withAuth'
import HeadLayout from "../app/layout/Head";
import FeedController from '../app/controller/Home/Feed';

const Feed = () => {
  return (
    <>
      <HeadLayout
        title="Лента"
        // content={
        //   <link href="/css/feed.css" rel="stylesheet" />
        // }
      />
      <FeedController />
    </>
  )
}


export default withAuth(Feed);