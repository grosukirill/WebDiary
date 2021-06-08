import './App.css';
import React from "react";
import Login from "./conponents/login/Login";
import {Route, Switch, withRouter} from "react-router-dom";
import {createMuiTheme} from "@material-ui/core";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import Register from "./conponents/login/Register";
import CssBaseline from "@material-ui/core/CssBaseline";
import Home from "./conponents/home/Home";
import connect from "react-redux/lib/connect/connect";
import {authCheckState} from "./store/actions/authActions";
import Navbar from "./conponents/navigation/Navbar";

const theme = createMuiTheme({
    palette: {
        primary: {
            main: '#228b22',
        },
        secondary: {
            main: '#19857b',
        },
        error: {
            main: "#FF0000",
        },
        background: {
            default: '#fff',
        },
    }
});

const App = (props) => {

    const {token} = props.auth;
    const isAuthenticated = token !== null;

    if (!isAuthenticated) {
        props.authCheckState();
    }

    let routes = [
        <Route path='/login' component={Login} key={1}/>,
        <Route path='/register' component={Register} key={2}/>,
        <Route exact path='/' component={Home} key={3}/>,
    ];

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <Navbar/>
            <Switch>{routes}</Switch>
        </ThemeProvider>
    );
}

const mapStateToProps = state => ({
    auth: state.authReducer,
});

export default withRouter(connect(mapStateToProps, {authCheckState})(App));
