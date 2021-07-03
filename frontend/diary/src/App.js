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
import {ToastContainer} from "material-react-toastify";
import 'material-react-toastify/dist/ReactToastify.min.css';
import Question from "./conponents/question/Question";


const theme = createMuiTheme({
    palette: {
        primary: {
            main: '#32cd32',
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
        <Route path='/question/:id?' component={Question} key={4}/>
    ];

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <Navbar/>
            <Switch>{routes}</Switch>
            <ToastContainer
                position="bottom-right"
                autoClose={5000}
                hideProgressBar={true}
                newestOnTop={true}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
            />
        </ThemeProvider>
    );
}

const mapStateToProps = state => ({
    auth: state.authReducer,
});

export default withRouter(connect(mapStateToProps, {authCheckState})(App));
