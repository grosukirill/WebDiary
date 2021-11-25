import HeadLayout from "../app/layout/Head";
import withAuth from '../utils/withAuth'
import Main from '../app/controller/Home/Main';
import { useEffect } from 'react';

const Home = () => {
  return (
    <>
      <HeadLayout
        title="Главная страница"
        // content={
        //   <link href="/css/feed.css" rel="stylesheet" />
        // }
      />
      <Main />
    </>
  )
}


export default withAuth(Home);