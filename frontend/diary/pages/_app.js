import React, { useEffect, useState } from 'react';
import { useRouter } from "next/router";

import { Provider } from 'react-redux';
import store from '../store/index';

import Layout from '../app/layout/Layout';
import { setUserLogged } from '../store/actions/authAction'

import '../assets/css/global_auth.css'
import '../assets/css/global.css'
import '../public/css/feed.css'
import '../assets/css/global_components.css'

const App = ({ Component, pageProps }) => {
  const [isLogged, setLogged] = useState(false)
  const [loading, setLoading] = useState(true)
  const Router = useRouter();

  useEffect(() => {
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null

    if (token) {
      setUserLogged(store, token)
      setLogged(true)
    }
    if (pageProps.isProtected) {
      if (token === null || token === undefined) {
        goLogin();
        setLoading(false)
      }
    }

    setLoading(false)
  })

  const goLogin = () => {
    Router.replace("/login");
  }

  return (
    <Provider store={store}>
      {loading ? (
        <span>Loading...</span>
      ) : (
        <div className={(isLogged ? "main_background " : "") + "main"}>
          <Layout>
            <Component {...pageProps} />
          </Layout>
        </div>
      )}
    </Provider>
  )
}

export default App;