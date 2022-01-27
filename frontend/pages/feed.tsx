import HeadLayout from "../app/layout/Head";
import withAuth from '../utils/withAuth'
import Feed from '../app/controller/Home/Feed';
import Questions from '../services/Questions';

const Home = () => {
  return (
    <>
      <HeadLayout
        title="Главная страница"
        // content={
        //   <link href="/css/feed.css" rel="stylesheet" />
        // }
      />
      <Feed />
    </>
  )
}


export default withAuth(Home);