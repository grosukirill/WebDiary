import React from 'react';
import App from 'next/app';

import { Provider } from 'react-redux';
import store from '../store/index';

import Layout from '../app/layout/Layout';

import '../assets/css/global_auth.css'
import '../assets/css/global.css'

class MyApp extends App {

  static async getStaticProps({ Component, ctx }) {
    const pageProps = Component.getStaticProps ? await Component.getStaticProps(ctx) : {};

    return { pageProps: pageProps };
  }

  render() {
    const { Component, pageProps } = this.props;

    return (
      <Provider store={store}>
        <Layout>
          <Component {...pageProps} />
        </Layout>
      </Provider>
    );
  }

}

const makeStore = () => store;

//withRedux wrapper that passes the store to the App Component
export default MyApp;